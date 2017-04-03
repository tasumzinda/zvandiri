package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "mental_health_item", id = "_id")
public class MentalHealthItem extends Model {


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
    @Column(name = "mental_health")
    public MentalHealth mentalHealth;
    @Expose
    @Column(name = "past")
    public String past;
    @Expose
    @Column(name = "current")
    public String current;
    @Expose
    @Column(name = "received_professional_help")
    public YesNo receivedProfessionalHelp;
    @Expose
    @Column(name = "prof_help_start")
    public Date profHelpStart;
    @Expose
    @Column(name = "prof_help_end")
    public Date profHelpEnd;
    @Expose
    @Column(name = "medication")
    public String medication;
    @Expose
    @Column(name = "start_date")
    public Date startDate;
    @Expose
    @Column(name = "end_date")
    public Date endDate;
    @Expose
    @Column(name = "been_hospitalized")
    public YesNo beenHospitalized;
    @Expose
    @Column(name = "mental_hist_text")
    public String mentalHistText;
    @Column(name = "pushed")
    public boolean pushed = true;

    @Column(name = "is_new")
    public boolean isNew = false;

    public MentalHealthItem() {
        super();
    }

    public MentalHealthItem(Patient patient) {
        super();
        this.patient = patient;
    }

    public static MentalHealthItem getItem(String id) {
        return new Select()
                .from(MentalHealthItem.class).where("id = ?", id).executeSingle();
    }

    public static List<MentalHealthItem> getAll() {
        return new Select()
                .from(MentalHealthItem.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(MentalHealthItem.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(MentalHealthItem.class).execute();
    }

    public static List<MentalHealthItem> findByPatient(Patient patient) {
        return new Select()
                .from(MentalHealthItem.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<MentalHealthItem> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(MentalHealthItem.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public String toString(){
        return "Current Status: " + current;
    }
}
