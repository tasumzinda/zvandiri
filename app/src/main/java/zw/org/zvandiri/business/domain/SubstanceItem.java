package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.DrugIntervention;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "substance_item", id = "_id")
public class SubstanceItem extends Model {

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
    @Column(name = "substance")
    public Substance substance;
    @Expose
    @Column(name = "current")
    public YesNo current;
    @Expose
    @Column(name = "past")
    public YesNo past;
    @Expose
    @Column(name = "type_amount")
    public String typeAmount;
    @Expose
    @Column(name = "start_date")
    public Date startDate;
    @Expose
    @Column(name = "end_date")
    public Date endDate;
    @Expose
    @Column(name = "drug_intervention")
    public DrugIntervention drugIntervention;
    @Column(name = "pushed")
    public boolean pushed = true;
    @Column(name = "is_new")
    public boolean isNew = false;

    public SubstanceItem() {
        super();
    }

    public SubstanceItem(Patient patient) {
        super();
        this.patient = patient;
    }

    public static SubstanceItem getItem(String id) {
        return new Select()
                .from(SubstanceItem.class).where("id = ?", id).executeSingle();
    }

    public static List<SubstanceItem> getAll() {
        return new Select()
                .from(SubstanceItem.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<SubstanceItem> findByPatient(Patient patient){
        return new Select()
                .from(SubstanceItem.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<SubstanceItem> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(SubstanceItem.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(SubstanceItem.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(SubstanceItem.class).execute();
    }

    @Override
    public String toString() {
        return "Substance: " + substance.name;
    }
}
