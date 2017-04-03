package zw.org.zvandiri.business.domain;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/13/2016.
 */
@Table(name = "level_of_care", id = "_id")
public class LevelOfCare extends Model {

    //private static final String URL = "http://pzat.org:8089/zvandiri-mobile/rest/mobile/static/level-of-care";
    private static final String URL = "http://192.168.20.39:8084/zvandiri-mobile/rest/mobile/static/level-of-care";
    @Expose
    @Column(name = "uuid")
    public String uuid;
    @Expose
    @Column(name = "created_by")
    public User createdBy;
    @Expose
    @Column(name = "modified_by")
    public User modifiedBy;
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

    public LevelOfCare() {
        super();
    }

    public LevelOfCare(String id) {
        super();
        this.id = id;
    }

    public static LevelOfCare getItem(String id) {
        return new Select()
                .from(LevelOfCare.class).where("id = ?", id).executeSingle();
    }

    public static List<LevelOfCare> getAll() {
        return new Select()
                .from(LevelOfCare.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(LevelOfCare.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(LevelOfCare.class).execute();
    }

    @Override
    public String toString() {
        return name;
    }

    public static void fetchRemote(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<LevelOfCare> levelOfCareList = Arrays.asList(AppUtil.createGson().fromJson(response, LevelOfCare[].class));
                        for(LevelOfCare levelOfCare : levelOfCareList){
                            Log.d("Saving LevelOfCare", levelOfCare.name);
                            levelOfCare.save();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LevelOfCare", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", AppUtil.getUsername(context), AppUtil.getPassword(context)).getBytes(), Base64.DEFAULT)));*/
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "jmuzinda@gmail.com", "16-JudgE@84").getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }
        };
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }
}
