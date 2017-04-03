package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "medical_hist", id = "_id")
public class MedicalHist extends Model {

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
    @Column(name = "hosp_when")
    public Date hospWhen;
    @Expose
    @Column(name = "primary_clinic")
    public Facility primaryClinic;
    @Expose
    @Column(name = "hosp_cause")
    public HospCause hospCause;
    @Column(name = "pushed")
    public boolean pushed = true;

    @Column(name = "is_new")
    public boolean isNew = false;

    public MedicalHist() {
        super();
    }

    public MedicalHist(Patient patient) {
        super();
        this.patient = patient;
    }

    public static MedicalHist getItem(String id) {
        return new Select()
                .from(MedicalHist.class).where("id = ?", id).executeSingle();
    }

    public static List<MedicalHist> getAll() {
        return new Select()
                .from(MedicalHist.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<MedicalHist> findByPatient(Patient patient) {
        return new Select()
                .from(MedicalHist.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<MedicalHist> findByPatientAndPushed(Patient patient) {
        return new Select()
                .from(MedicalHist.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(MedicalHist.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(MedicalHist.class).execute();
    }

    @Override
    public String toString(){
        return "Hosp Cause: " + hospCause.name;
    }
}
