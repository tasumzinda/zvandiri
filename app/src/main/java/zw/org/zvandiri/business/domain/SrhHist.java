package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.CondomUse;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "srh_hist", id = "_id")
public class SrhHist extends Model {

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
    @Column(name = "age_start_men")
    public Integer ageStartMen;
    @Expose
    @Column(name = "bleed_how_often")
    public Integer bleedHowOften;
    @Expose
    @Column(name = "bleed_days")
    public Integer bleeddays;
    @Expose
    @Column(name = "sexual_intercourse")
    public YesNo sexualIntercourse;
    @Expose
    @Column(name = "sexually_active")
    public YesNo sexuallyActive;
    @Expose
    @Column(name = "condom_use")
    public CondomUse condomUse;
    @Expose
    @Column(name = "birth_control")
    public YesNo birthControl;
    @Column(name = "pushed")
    public boolean pushed = true;
    @Column(name = "is_new")
    public boolean isNew = false;

    public SrhHist() {
        super();
    }

    public SrhHist(Patient patient) {
        super();
        this.patient = patient;
    }

    public static SrhHist getItem(String id) {
        return new Select()
                .from(SrhHist.class).where("id = ?", id).executeSingle();
    }

    public static List<SrhHist> getAll() {
        return new Select()
                .from(SrhHist.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<SrhHist> findByPatient(Patient patient){
        return new Select()
                .from(SrhHist.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<SrhHist> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(SrhHist.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(SrhHist.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(SrhHist.class).execute();
    }

    @Override
    public String toString(){
        return "Bleed Days: " + bleeddays;
    }
}
