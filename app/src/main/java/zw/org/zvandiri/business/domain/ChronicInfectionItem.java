package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.CurrentStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "chronic_infection_item", id = "_id")
public class ChronicInfectionItem extends Model {

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
    @Column(name = "chronic_infection")
    public ChronicInfection chronicInfection;
    @Expose
    @Column(name = "infection_date")
    public Date infectionDate;
    @Expose
    @Column(name = "medication")
    public String medication;
    @Expose
    @Column(name = "current_status")
    public CurrentStatus currentStatus;

    @Column(name = "pushed")
    public boolean pushed = true;

    @Column(name = "is_new")
    public boolean isNew = false;

    public ChronicInfectionItem() {
        super();
    }

    public ChronicInfectionItem(Patient patient) {
        super();
        this.patient = patient;
    }

    public static ChronicInfectionItem getItem(String id) {
        return new Select()
                .from(ChronicInfectionItem.class).where("id = ?", id).executeSingle();
    }

    public static List<ChronicInfectionItem> findByPatient(Patient patient){
        return new Select()
                .from(ChronicInfectionItem.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<ChronicInfectionItem> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(ChronicInfectionItem.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }
    public static List<ChronicInfectionItem> getAll() {
        return new Select()
                .from(ChronicInfectionItem.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(ChronicInfectionItem.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(ChronicInfectionItem.class).execute();
    }

    public String toString() {
        return "Chronic infection: " + chronicInfection;
    }
}
