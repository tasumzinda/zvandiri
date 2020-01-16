package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.domain.util.Referral;

import java.util.Date;
import java.util.List;

/**
 * @uthor Tasu Muzinda
 */
@Table(name = "mental_health_screening", id = "_id")
public class MentalHealthScreening extends Model {

    @Column
    @Expose
    public String id;

    @Column
    public Patient patient;

    @Column
    @Expose
    public YesNo screenedForMentalHealth;
    @Column
    @Expose
    public Screening screening;
    @Column
    @Expose
    public YesNo risk;
    @Expose
    public List<IdentifiedRisk> identifiedRisks;
    @Expose
    @Column
    public YesNo support;
    @Expose
    public List<Support> supports;
    @Column
    @Expose
    public YesNo referral;
    @Expose
    public List<Referral> referrals;
    @Expose
    @Column
    public YesNo diagnosis;
    @Expose
    public List<Diagnosis> diagnoses;
    @Expose
    @Column
    public String otherDiagnosis;
    @Expose
    @Column
    public YesNo intervention;
    @Expose
    public List<Intervention> interventions;
    @Column
    @Expose
    public String otherIntervention;
    @Column
    public int pushed = 0;
    @Column(name = "is_new")
    public int isNew = 0;
    @Expose
    public String patientId;
    @Column
    @Expose
    public Date dateScreened;
    @Column
    @Expose
    public YesNo referralComplete;

    public static MentalHealthScreening getItem(Long id) {
        return new Select()
                .from(MentalHealthScreening.class).where("_id = ?", id).executeSingle();
    }

    public static List<MentalHealthScreening> findByPatient(Patient patient) {
        return new Select()
                .from(MentalHealthScreening.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<MentalHealthScreening> getAll(){
        return new Select()
                .from(MentalHealthScreening.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(MentalHealthScreening.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(MentalHealthScreening.class).execute();
    }

    @Override
    public String toString() {
        return patient.name != null ? patient.name : patient.firstName + " " + patient.lastName;
    }
}
