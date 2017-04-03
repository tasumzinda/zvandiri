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
@Table(name = "hiv_infection_item", id = "_id")
public class HivConInfectionItem extends Model {

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
    @Column(name = "pushed")
    public boolean pushed = true;

    @Expose
    @Column(name = "hiv_co_infection")
    public HivCoInfection hivCoInfection;
    @Expose
    @Column(name = "infection_date")
    public Date infectionDate;
    @Expose
    @Column(name = "medication")
    public String medication;
    @Expose
    @Column(name = "resolution")
    public String resolution;

    @Column(name = "is_new")
    public boolean isNew = false;

    public HivConInfectionItem() {
        super();
    }

    public HivConInfectionItem(Patient patient) {
        super();
        this.patient = patient;
    }

    public static HivConInfectionItem getItem(String id) {
        return new Select()
                .from(HivConInfectionItem.class).where("id = ?", id).executeSingle();
    }

    public static List<HivConInfectionItem> getAll() {
        return new Select()
                .from(HivConInfectionItem.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<HivConInfectionItem> findByPatient(Patient p) {
        return new Select()
                .from(HivConInfectionItem.class)
                .where("patient = ?", p.getId())
                .execute();
    }

    public static List<HivConInfectionItem> findByPatientAndPushed(Patient p) {
        return new Select()
                .from(HivConInfectionItem.class)
                .where("patient = ?", p.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(HivConInfectionItem.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(HivConInfectionItem.class).execute();
    }

    @Override
    public String toString(){
       return "Infection: " + hivCoInfection.name;
    }
}
