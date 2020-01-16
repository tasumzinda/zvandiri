package zw.org.zvandiri.business.domain;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.UserLevel;
import zw.org.zvandiri.business.domain.util.UserType;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/10/2016.
 */
@Table(name = "user", id = "_id")
public class User extends Model {

    @Expose
    @Column(name = "version")
    public Long version;

    @Expose
    @Column(name = "id")
    public String id;

    @Expose
    @Column(name = "first_name")
    public String firstName;

    @Expose
    @Column(name = "last_name")
    public String lastName;

    @Expose
    @Column(name = "user_name")
    public String userName;

    @Expose
    @Column
    public UserLevel userLevel;

    @Expose
    @Column(name = "user_level")
    public Integer userLevelId;
    @Expose
    @Column
    public Province province;
    @Expose
    @Column
    public District district;

    public User() {
        super();
    }

    public static boolean checkDuplicate(String userName) {
        boolean userExists = true;
        User obj = new Select().from(User.class).where("user_name = ?", userName).executeSingle();
        if (obj != null) {
            userExists = false;
        }
        return userExists;
    }

    public static User userExists(String userName) {
        return new Select().from(User.class).where("user_name = ?", userName).executeSingle();
    }

    public static User getItem(String id) {
        return new Select().from(User.class).where("id = ?", id).executeSingle();
    }

    public static List<User> getAll() {
        return new Select()
                .from(User.class)
                .orderBy("last_name ASC")
                .execute();
    }

    public static List<User> getUsers(Province province, District district, UserLevel userLevel) {
        List<User> users = new ArrayList<>();
        if(district != null) {
            users.addAll(getUserByUserLevelAndDistrict(userLevel, district));
        }else if(province != null) {
            users.addAll(getUserByUserLevelAndProvince(userLevel, province));
        }else if(userLevel != null) {
            users.addAll(getUserByUserLevel(userLevel));
        }else {
            users.addAll(getAll());
        }
        return users;
    }

    private static List<User> getUserByUserLevel(UserLevel userLevel) {
        return new Select()
                .from(User.class)
                .where("user_level = ?", userLevel.getCode())
                .orderBy("last_name ASC")
                .execute();
    }

    private static List<User> getUserByUserLevelAndProvince(UserLevel userLevel, Province province) {
        return new Select()
                .from(User.class)
                .where("user_level = ?", userLevel.getCode())
                .and("province = ?", province.getId())
                .orderBy("last_name ASC")
                .execute();
    }

    private static List<User> getUserByUserLevelAndDistrict(UserLevel userLevel, District district) {
        return new Select()
                .from(User.class)
                .where("user_level = ?", userLevel.getCode())
                .and("district = ?", district.getId())
                .orderBy("last_name ASC")
                .execute();
    }

    public static void deleteAll() {
        new Delete().from(User.class).execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(User.class).where("id = ?", id).executeSingle();
    }

    public static void fetchRemote(final Context context, final String userName, final String password) {
        String URL = AppUtil.getBaseUrl(context) + "/static/user";
        JsonArrayRequest stringRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try{
                                JSONObject jsonObject = response.getJSONObject(i);
                                User user = new User();
                                user.id = jsonObject.getString("id");
                                user.firstName = jsonObject.getString("firstName");
                                user.lastName = jsonObject.getString("lastName");
                                user.userName = jsonObject.getString("userName");
                                user.version = jsonObject.getLong("version");
                                if(!jsonObject.isNull("province")) {
                                    JSONObject province = jsonObject.optJSONObject("province");
                                    user.province = Province.getItem(province.getString("id"));
                                }
                                if(!jsonObject.isNull("district")) {
                                    JSONObject district = jsonObject.optJSONObject("district");
                                    user.district = District.getItem(district.getString("id"));
                                }
                                if(!jsonObject.isNull("userLevel")) {
                                    user.userLevel = UserLevel.valueOf(jsonObject.getString("userLevel"));
                                    user.userLevelId = user.userLevel.getCode();
                                }
                                User checkDuplicate = user.getItem(jsonObject.getString("id"));
                                if(checkDuplicate == null){
                                    user.save();
                                }

                            }catch (JSONException ex){
                                Log.d("user", ex.getMessage());
                            }

                        }
                        Patient.fetchRemote(context, userName, password);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("user", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", userName, password).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }

            public Priority getPriority(){
                return Priority.NORMAL;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}
