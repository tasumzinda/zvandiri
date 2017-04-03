package zw.org.zvandiri.business.domain;

import android.content.Context;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "settings", id = "_id")
public class Settings extends Model {

    private static final String GET_URL = "http://pzat.org:8089/zvandiri";
    private static final Response.Listener<String> onSettingsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<Settings> settingsList = Arrays.asList(AppUtil.createGson().fromJson(response, Settings[].class));
            for (Settings settings : settingsList) {
                Log.d("Saving settings", settings.id);
                settings.save();
            }
        }
    };
    private static final Response.ErrorListener onSettingsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("Settings error", error.toString());
        }
    };
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
    @Column(name = "patient_min_age")
    public Integer patientMinAge;
    @Expose
    @Column(name = "patient_max_age")
    public Integer patientMaxAge;
    @Expose
    @Column(name = "patient_auto_expire_aftre_max_age")
    public Integer patientAutoExpireAfterMaxAge;
    @Expose
    @Column(name = "cat_min_age")
    public Integer catMinAge;
    @Expose
    @Column(name = "cat_max_age")
    public Integer catMaxAge;
    @Expose
    @Column(name = "cd4_min_count")
    public Integer cd4MinCount;
    @Expose
    @Column(name = "viral_load_max_count")
    public Integer viralLoadMaxCount;
    @Expose
    @Column(name = "max_days_after_contact")
    public Integer maxDaysAfterContact;
    @Expose
    @Column(name = "min_age_to_give_birth")
    public Integer minAgeToGiveBirth;

    public Settings() {
        super();
    }

    public Settings(String id) {
        super();
        this.id = id;
    }

    public static Settings getItem(String id) {
        return new Select()
                .from(Settings.class).where("id = ?", id).executeSingle();
    }

    public static List<Settings> getAll() {
        return new Select()
                .from(Settings.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(Settings.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Settings.class).execute();
    }

    public static void fetchSettings(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_URL, onSettingsLoaded, onSettingsError);
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }
}
