package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.CauseOfDeath;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "mortality", id = "_id")
public class Mortality extends Model implements Serializable {

    @Expose
    @Column(name = "uuid")
    public String uuid;
    @Expose
    @Column(name = "version")
    public Long version;

    @Expose
    @Column(name = "active")
    public Boolean active;

    @Expose
    @Column(name = "deleted")
    public Boolean deleted;

    @Expose
    @Column(name = "id")
    public String id;

    @Expose
    @Column(name = "patient")
    public Patient patient;

    @Expose
    @Column
    public Date dateOfDeath;

    @Expose
    @Column
    public CauseOfDeath causeOfDeath;

    @Expose
    @Column
    public YesNo receivingEnhancedCare;
    @Expose
    @Column
    public Date datePutOnEnhancedCare;
    @Expose
    @Column
    public String caseBackground;
    @Expose
    @Column
    public String careProvided;
    @Expose
    @Column
    public String home;
    @Expose
    @Column
    public String beneficiary;
    @Expose
    @Column
    public String facility;
    @Expose
    @Column
    public String cats;
    @Expose
    @Column
    public String zm;
    @Expose
    @Column
    public String others;
    @Expose
    @Column
    public String learningPoints;
    @Expose
    @Column
    public String actionPlan;

    public static Mortality getItem(Long id) {
        return new Select()
                .from(Mortality.class).where("_id = ?", id).executeSingle();
    }

    public static List<Mortality> findByPatient(Patient patient) {
        return new Select()
                .from(Mortality.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<Mortality> getAll(){
        return new Select()
                .from(Mortality.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(Mortality.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Mortality.class).execute();
    }

    @Override
    public String toString() {
        return patient.name != null ? patient.name : patient.firstName + " " + patient.lastName;
    }
}
