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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Source;
import zw.org.zvandiri.business.domain.util.Status;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "arv_adverse_effect", id = "_id")
public class ArvAdverseEffect extends Model {
    private static final String URL = "http://192.168.20.39:8084/zvandiri-mobile/rest/mobile/static/arv-adverse-effect";
    //private static final String URL = "http://pzat.org:8089/zvandiri-mobile/rest/mobile/static/arv-adverse-effect";
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
    @Column(name = "arv_hist")
    public ArvHist arvHist;
    @Expose
    @Column(name = "event")
    public String event;
    @Expose
    @Column(name = "date_commenced")
    public Date dateCommenced;
    @Expose
    @Column(name = "status")
    public Status status;
    @Expose
    @Column(name = "source")
    public Source source;

    public ArvAdverseEffect() {
        super();
    }

    public ArvAdverseEffect(ArvHist arvHist) {
        super();
        this.arvHist = arvHist;
    }

    public static ArvAdverseEffect getItem(String id) {
        return new Select()
                .from(ArvAdverseEffect.class).where("id = ?", id).executeSingle();
    }

    public static List<ArvAdverseEffect> getAll() {
        return new Select()
                .from(ArvAdverseEffect.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(ArvAdverseEffect.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(ArvAdverseEffect.class).execute();
    }

    public String toString() {
        return "Source: " + source + "Status: " + status + "Event: " + event;
    }

    public void fetchRemote(Context context, final String userName, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<ArvAdverseEffect> arvAdverseEffectList = Arrays.asList(AppUtil.createGson().fromJson(response, ArvAdverseEffect[].class));
                        for(ArvAdverseEffect arvAdverseEffect : arvAdverseEffectList){
                            Log.d("Saving arvAdverseEffect", arvAdverseEffect.toString());
                            arvAdverseEffect.save();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Arv Adverse Effect", error.toString());
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }
}
