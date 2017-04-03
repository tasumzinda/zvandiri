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
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "cat_detail", id = "_id")
public class CatDetail extends Model {

    private static final String URL = "http://192.168.20.39:8084/zvandiri-mobile/rest/mobile/static/cat-detail";
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
    @Column(name = "primary_clinic")
    public Facility primaryClinic;
    @Expose
    @Column(name = "date_as_cat")
    public Date dateAsCat;
    public String userName;
    public String password;
    public String confirmPassword;
    public Province province;
    public District district;
    public String currentElement;

    public CatDetail() {
    }

    public CatDetail(Patient patient) {
        this.patient = patient;
    }

    public static CatDetail getItem(String id) {
        return new Select()
                .from(CatDetail.class).where("id = ?", id).executeSingle();
    }

    public static List<CatDetail> getAll() {
        return new Select()
                .from(CatDetail.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(CatDetail.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(CatDetail.class).execute();
    }
}
