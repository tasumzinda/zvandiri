package zw.org.zvandiri.business.domain;

import android.content.Context;
import android.util.Base64;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table(name = "services_offered", id = "_id")
public class ServiceOffered extends Model implements Serializable {

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
    public Boolean active;

    @Expose
    @Column(name = "deleted")
    public Boolean deleted;

    @Expose
    @Column(name = "id")
    public String id;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "description")
    public String description;

    public ServiceOffered() {
        super();
    }

    public ServiceOffered(String id) {
        super();
        this.id = id;
    }

    public static ServiceOffered getItem(String id) {
        return new Select()
                .from(ServiceOffered.class).where("id = ?", id).executeSingle();
    }

    public static List<ServiceOffered> getAll() {
        return new Select()
                .from(ServiceOffered.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<ServiceOffered> findByContact(Contact contact){
        return new Select()
                .from(ServiceOffered.class)
                .innerJoin(ContactServiceOfferedContract.class)
                .on("contact_service_offered.service_id = services_offered._id ")
                .where("contact_service_offered.contact_id = ?", contact.getId())
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(ServiceOffered.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(ServiceOffered.class).execute();
    }

    @Override
    public String toString() {
        return name;
    }

    public static void fetchRemote(Context context, final String userName, final String password) {
        String URL = AppUtil.getBaseUrl(context) + "/static/service-offered";
        JsonArrayRequest stringRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try{
                                JSONObject jsonObject = response.getJSONObject(i);
                                ServiceOffered item = new ServiceOffered();
                                item.id = jsonObject.getString("id");
                                item.active = jsonObject.getBoolean("active");
                                if(! jsonObject.isNull("dateModified")){
                                    item.dateModified = DateUtil.getFromString(jsonObject.getString("dateModified"));
                                }
                                String date = jsonObject.getString("dateCreated");
                                item.dateCreated = DateUtil.getFromString(date);
                                item.deleted = jsonObject.getBoolean("deleted");
                                item.description = jsonObject.getString("description");
                                item.name = jsonObject.getString("name");
                                item.version = jsonObject.getLong("version");
                                ServiceOffered checkDuplicate = ServiceOffered.getItem(jsonObject.getString("id"));
                                if(checkDuplicate == null){
                                    item.save();
                                }

                            }catch (JSONException ex){
                                ex.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
