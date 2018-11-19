package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;
import zw.org.zvandiri.business.domain.util.MentalScreenResult;
import zw.org.zvandiri.business.domain.util.YesNo;

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
    @Expose
    public Patient patient;

    @Column
    @Expose
    public YesNo screenedForMentalHealth;

    @Column
    @Expose
    public IdentifiedRisk identifiedRisk;

    @Column
    @Expose
    public ActionTaken actionTaken;

    @Column
    @Expose
    public String actionTakenText;

    @Column
    @Expose
    public MentalScreenResult mentalScreenResult;

    @Column
    @Expose
    public ActionTaken rescreenActionTaken;

    @Column
    @Expose
    public String rescreenActionTakenText;

    @Column
    @Expose
    public IdentifiedRisk rescreenIdentifiedRisk;

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
