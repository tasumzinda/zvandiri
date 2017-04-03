package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.AbuseOutcome;
import zw.org.zvandiri.business.domain.util.AbuseType;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "social_hist", id = "_id")
public class SocialHist extends Model {

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
    @Column(name = "live_with")
    public String liveWith;
    @Expose
    @Column(name = "relationship")
    public Relationship relationship;
    @Expose
    @Column(name = "abuse")
    public YesNo abuse;
    @Expose
    @Column(name = "disclosure")
    public YesNo dosclosure;
    @Expose
    @Column(name = "feel_safe")
    public YesNo feelSafe;
    @Expose
    @Column(name = "abuse_outcome")
    public AbuseOutcome abuseOutcome;
    @Expose
    @Column(name = "abuse_type")
    public AbuseType abuseType;
    @Column(name = "pushed")
    public boolean pushed = true;
    @Column(name = "is_new")
    public boolean isNew = false;

    public SocialHist() {
        super();
    }

    public SocialHist(Patient patient) {
        super();
        this.patient = patient;
    }

    public static SocialHist getItem(String id) {
        return new Select()
                .from(SocialHist.class).where("id = ?", id).executeSingle();
    }

    public static List<SocialHist> findByPatient(Patient patient){
        return new Select()
                .from(SocialHist.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<SocialHist> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(SocialHist.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static List<SocialHist> getAll() {
        return new Select()
                .from(SocialHist.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(SocialHist.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(SocialHist.class).execute();
    }

    @Override
    public String toString(){
        return "Abuse Type: " + abuseType.getName();
    }
}
