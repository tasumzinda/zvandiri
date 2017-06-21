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
import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/11/2016.
 */
@Table(name = "district", id = "_id")
public class District extends Model implements Serializable{

    @Expose
    @Column(name = "uuid")
    public String uuid;

    @Expose
    @Column(name = "date_created")
    public Date dateCreated;

    @Expose
    @Column(name = "date_modified")
    public Date dateModified;

    @Expose
    @Column(name = "version")
    public Long version;

    @Expose
    @Column(name = "active")
    public Boolean active = Boolean.TRUE;

    @Expose
    @Column(name = "deleted")
    public Boolean deleted = Boolean.FALSE;

    @Expose
    @Column(name = "id")
    public String id;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "description")
    public String description;

    @Expose
    @Column(name = "province")
    public Province province;

    public District() {
        super();
    }

    public static District getItem(String id) {
        return new Select()
                .from(District.class).where("id = ?", id).executeSingle();
    }

    public static List<District> getByProvince(Province province) {
        return new Select()
                .from(District.class)
                .where("province = ?", province.getId())
                .orderBy("name ASC")
                .execute();
    }

    public static List<District> getAll() {
        return new Select()
                .from(District.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(District.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(District.class).execute();
    }

    public List<Facility> getFacilities() {
        return getMany(Facility.class, "district");
    }

    @Override
    public String toString() {
        return name;
    }

    public static void fetchRemote(final Context context, final String userName, final String password) {
        String URL = AppUtil.getBaseUrl(context) + "/static/district";
        JsonArrayRequest stringRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try{
                                JSONObject jsonObject = response.getJSONObject(i);
                                District district = new District();
                                district.id = jsonObject.getString("id");
                                district.active = jsonObject.getBoolean("active");
                                if(! jsonObject.isNull("dateModified")){
                                    district.dateModified = DateUtil.getFromString(jsonObject.getString("dateModified"));
                                }
                                String date = jsonObject.getString("dateCreated");
                                district.dateCreated = DateUtil.getFromString(date);
                                district.deleted = jsonObject.getBoolean("deleted");
                                district.description = jsonObject.getString("description");
                                district.name = jsonObject.getString("name");
                                district.version = jsonObject.getLong("version");
                                JSONObject province = jsonObject.getJSONObject("province");
                                district.province = Province.getItem(province.getString("id"));
                                District checkDuplicate = District.getItem(jsonObject.getString("id"));
                                if(checkDuplicate == null){
                                    district.save();
                                    Log.d("Created district", district.name);
                                }

                            }catch (JSONException ex){
                                Log.d("District", ex.getMessage());
                            }

                        }
                        Facility.fetchRemote(context, userName, password);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("District", error.toString());
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

}
