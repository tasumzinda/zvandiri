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
import zw.org.zvandiri.business.domain.util.Cd4CountResultSource;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "cd4_count", id = "_id")
public class Cd4Count extends Model {

    //private static final String URL = "http://pzat.org:8089/zvandiri-mobile/rest/mobile/static/cd4-count";
    private static final String URL = "http://192.168.20.39:8084/zvandiri-mobile/rest/mobile/static/cd4-count";
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
    @Column(name = "patient")
    public Patient patient;
    @Expose
    @Column(name = "date_taken")
    public Date dateTaken;
    @Expose
    @Column(name = "result")
    public Integer result;
    @Expose
    @Column(name = "source")
    public Cd4CountResultSource source;

    @Column(name = "pushed")
    public boolean pushed = false;

    @Column(name = "is_new")
    public boolean isNew = false;

    public Cd4Count() {
        super();
    }

    public Cd4Count(Patient patient) {
        super();
        this.patient = patient;
    }

    public static Cd4Count getItem(String id) {
        return new Select()
                .from(Cd4Count.class).where("id = ?", id).executeSingle();
    }

    public static List<Cd4Count> getAll() {
        return new Select()
                .from(Cd4Count.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<Cd4Count> findByPatient(Patient patient){
        return new Select()
                .from(Cd4Count.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<Cd4Count> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(Cd4Count.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(Cd4Count.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Cd4Count.class).execute();
    }

    public String toString() {
        return "Source: " + source.getName();
    }

    public void fetchRemote(Context context, final String userName, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Cd4Count> cd4CountList = Arrays.asList(AppUtil.createGson().fromJson(response, Cd4Count[].class));
                        for(Cd4Count cd4Count : cd4CountList){
                            Log.d("Saving cd4Count", cd4Count.toString());
                            cd4Count.save();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Cd4 Count", error.toString());
                    }
                }
        ){
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
        };
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }

}
