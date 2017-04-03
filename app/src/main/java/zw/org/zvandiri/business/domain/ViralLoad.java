package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Cd4CountResultSource;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "viral_load", id = "_id")
public class ViralLoad extends Model {

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
    @Column(name = "date_taken")
    public Date dateTaken;

    @Expose
    @Column(name = "result")
    public Integer result;

    @Expose
    @Column(name = "source")
    public Cd4CountResultSource source;

    @Column(name = "pushed")
    public boolean pushed = true;

    @Column(name = "is_new")
    public boolean isNew = false;

    public ViralLoad() {
        super();
    }

    public ViralLoad(Patient patient) {
        super();
        this.patient = patient;
    }

    public static ViralLoad getItem(String id) {
        return new Select()
                .from(ViralLoad.class).where("id = ?", id).executeSingle();
    }

    public static List<ViralLoad> getAll() {
        return new Select()
                .from(ViralLoad.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<ViralLoad> findByPatient(Patient patient){
        return new Select()
                .from(ViralLoad.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<ViralLoad> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(ViralLoad.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(ViralLoad.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(ViralLoad.class).execute();
    }

    public String toString(){
        return "Source: " + source.getName();
    }
}
