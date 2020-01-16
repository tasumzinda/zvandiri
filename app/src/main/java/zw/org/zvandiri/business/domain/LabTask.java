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

@Table(name = "lab_task", id = "_id")
public class LabTask extends Model implements Serializable {

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

    public LabTask() {
        super();
    }

    public LabTask(String id) {
        super();
        this.id = id;
    }

    public static LabTask getItem(String id) {
        return new Select()
                .from(LabTask.class).where("id = ?", id).executeSingle();
    }

    public static List<LabTask> getAll() {
        return new Select()
                .from(Intensive.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<LabTask> findByContact(Contact contact){
        return new Select()
                .from(LabTask.class)
                .innerJoin(ContactLabTaskContact.class)
                .on("contact_lab_task.lab_task_id = lab_task._id ")
                .where("contact_lab_task.contact_id = ?", contact.getId())
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(LabTask.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(LabTask.class).execute();
    }

    public static void fetchRemote(Context context, final String userName, final String password){
        String URL = AppUtil.getBaseUrl(context) + "/static/lab-task-service";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<LabTask> intensiveList = Arrays.asList(AppUtil.createGson().fromJson(response, LabTask[].class));
                        for(LabTask intensive : intensiveList){
                            intensive.save();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LabTask", error.toString());
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

    @Override
    public String toString() {
        return name;
    }
}
