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

import java.io.Serializable;
import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "enhanced", id = "_id")
public class Enhanced extends Model implements Serializable{

    @Expose
    @Column(name = "uuid")
    public String uuid;

    //@Expose
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

    public Enhanced() {
        super();
    }

    public Enhanced(String id) {
        super();
        this.id = id;
    }

    public static Enhanced getItem(String id) {
        return new Select()
                .from(Enhanced.class).where("id = ?", id).executeSingle();
    }

    public static List<Enhanced> getAll() {
        return new Select()
                .from(Enhanced.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(Enhanced.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Enhanced.class).execute();
    }

    @Override
    public String toString() {
        return name;
    }

    public static void fetchRemote(Context context, final String userName, final String password) {
        String URL = AppUtil.getBaseUrl(context) + "/static/enhanced-follow-up";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Enhanced> enhancedList = Arrays.asList(AppUtil.createGson().fromJson(response, Enhanced[].class));
                        for (Enhanced enhanced : enhancedList) {
                            Enhanced checkDuplicate = Enhanced.getItem(enhanced.id);
                            if(checkDuplicate == null){
                                enhanced.save();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Enhanced", error.toString());
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

    public static List<Enhanced> findByContact(Contact contact){
        return new Select()
                .from(Enhanced.class)
                .innerJoin(ContactEnhancedContract.class)
                .on("contact_enhanced.enhanced_id = enhanced._id ")
                .where("contact_enhanced.contact_id = ?", contact.getId())
                .execute();
    }
}
