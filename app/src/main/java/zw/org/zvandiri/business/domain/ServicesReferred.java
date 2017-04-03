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
import zw.org.zvandiri.business.util.AppUtil;

import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "services_referred", id = "_id")
public class ServicesReferred extends Model {

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

    public ServicesReferred() {
        super();
    }

    public ServicesReferred(String id) {
        super();
        this.id = id;
    }

    public static ServicesReferred getItem(String id) {
        return new Select()
                .from(ServicesReferred.class).where("id = ?", id).executeSingle();
    }

    public static List<ServicesReferred> getAll() {
        return new Select()
                .from(ServicesReferred.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<ServicesReferred> findByReferral(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralServicesReferredContract.class)
                .on("referral_service.service_id = services_referred._id")
                .where("referral_service.referral_id = ?", referral.getId())
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(ServicesReferred.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(ServicesReferred.class).execute();
    }

    @Override
    public String toString() {
        return name;
    }

    public static void fetchRemote(Context context, final String userName, final String password) {
        String URL = AppUtil.getBaseUrl(context) + "/static/service-referred";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<ServicesReferred> servicesReferredList = Arrays.asList(AppUtil.createGson().fromJson(response, ServicesReferred[].class));
                        for (ServicesReferred servicesReferred: servicesReferredList) {
                            ServicesReferred checkDuplicate = ServicesReferred.getItem(servicesReferred.id);
                            if(checkDuplicate == null){
                                Log.d("Getting Service Refered", servicesReferred.name);
                                servicesReferred.save();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ServicesReferred", error.toString());
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
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }

}
