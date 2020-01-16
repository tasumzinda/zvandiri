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
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/11/2016.
 */
@Table(name = "facility", id = "_id")
public class Facility extends Model implements Serializable{

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
    @Expose
    @Column(name = "district")
    public District district;

    public Facility() {
        super();
    }

    public Facility(String id) {
        super();
        this.id = id;
    }

    public static Facility getItem(String id) {
        return new Select().from(Facility.class).where("id = ?", id).executeSingle();
    }

    public static List<Facility> getByDistrict(District district) {
        return new Select()
                .from(Facility.class)
                .where("district = ?", district.getId())
                .orderBy("name ASC")
                .execute();
    }

    public static List<Facility> getAll() {
        return new Select()
                .from(Facility.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(Facility.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Facility.class).execute();
    }

    public static void fetchRemote(final Context context, final String userName, final String password) {
        String URL = AppUtil.getBaseUrl(context) + "/static/facility";
        JsonArrayRequest stringRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try{
                                JSONObject jsonObject = response.getJSONObject(i);
                                Facility facility = new Facility();
                                facility.id = jsonObject.getString("id");
                                facility.active = jsonObject.getBoolean("active");
                                if(! jsonObject.isNull("dateModified")){
                                    facility.dateModified = DateUtil.getFromString(jsonObject.getString("dateModified"));
                                }
                                String date = jsonObject.getString("dateCreated");
                                facility.dateCreated = DateUtil.getFromString(date);
                                facility.deleted = jsonObject.getBoolean("deleted");
                                facility.description = jsonObject.getString("description");
                                facility.name = jsonObject.getString("name");
                                facility.version = jsonObject.getLong("version");
                                JSONObject district = jsonObject.getJSONObject("district");
                                facility.district = District.getItem(district.getString("id"));
                                JSONObject province = district.getJSONObject("province");
                                facility.province = Province.getItem(province.getString("id"));
                                Facility checkDuplicate = Facility.getItem(jsonObject.getString("id"));
                                if(checkDuplicate == null){
                                    facility.save();
                                }

                            }catch (JSONException ex){
                                Log.d("Facility", ex.getMessage());
                            }

                        }
                        SupportGroup.fetchRemote(context, userName, password);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", userName, password).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }
    public String toString() {
        return name;
    }
}
