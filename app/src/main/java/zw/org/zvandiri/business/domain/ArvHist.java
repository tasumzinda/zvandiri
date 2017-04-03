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
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "arv_hist", id = "_id")
public class ArvHist extends Model {

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
    @Column(name = "arv_medicine")
    public ArvMedicine arvMedicine;

    @Expose
    @Column(name = "start_date")
    public Date startDate;

    @Expose
    @Column(name = "end_date")
    public Date endDate;

    @Column(name = "pushed")
    public boolean pushed = true;

    @Column(name = "is_new")
    public boolean isNew = false;


    public ArvHist() {
        super();
    }

    public ArvHist(Patient patient) {
        super();
        this.patient = patient;
    }

    public static ArvHist getItem(String id) {
        return new Select()
                .from(ArvHist.class).where("id = ?", id).executeSingle();
    }

    public static List<ArvHist> getAll() {
        return new Select()
                .from(ArvHist.class)
                .execute();
    }

    public static List<ArvHist> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(ArvHist.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static List<ArvHist> findByPatient(Patient patient){
        return new Select()
                .from(ArvHist.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(ArvHist.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(ArvHist.class).execute();
    }

    public String toString() {
        return "Arv Medicine: " + arvMedicine;
    }
}
