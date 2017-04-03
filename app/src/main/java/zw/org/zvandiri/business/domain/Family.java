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
@Table(name = "family", id = "_id")
public class Family extends Model {

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
    @Column(name = "orphan_status")
    public OrphanStatus orphanStatus;
    @Expose
    @Column(name = "number_of_siblings")
    public Integer numberOfSiblings;
    public boolean pushed = true;

    @Column(name = "is_new")
    public boolean isNew = false;

    public Family() {
        super();
    }

    public Family(Patient patient) {
        super();
        this.patient = patient;
    }

    public static Family getItem(String id) {
        return new Select()
                .from(Family.class).where("id = ?", id).executeSingle();
    }

    public static List<Family> getAll() {
        return new Select()
                .from(Family.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<Family> findByPatient(Patient patient){
        return new Select()
                .from(Family.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<Family> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(Family.class)
                .where("patient = ?", patient.getId())
                //.and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(Family.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Family.class).execute();
    }

    @Override
    public String toString() {

        return orphanStatus.name;
    }
}
