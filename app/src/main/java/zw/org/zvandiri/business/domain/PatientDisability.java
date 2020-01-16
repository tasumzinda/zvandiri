package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import zw.org.zvandiri.business.domain.util.DisabilitySeverity;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "patient_disability", id = "_id")
public class PatientDisability extends Model implements Serializable {

    @Expose
    @Column(name = "id")
    public String id;

    //@Expose
    @Column
    public Patient patient;
    @Column
    @Expose
    public DisabilityCategory disabilityCategory;
    @Column
    @Expose
    @SerializedName("severity")
    public DisabilitySeverity disabilitySeverity;
    @Column
    @Expose
    public YesNo screened;
    @Column
    @Expose
    public Date dateScreened;
    @Column
    public int pushed = 0;
    @Column(name = "is_new")
    public int isNew = 0;

    public PatientDisability() {
        super();
    }

    public static PatientDisability getItem(Long id) {
        return new Select()
                .from(PatientDisability.class).where("_id = ?", id).executeSingle();
    }

    public static List<PatientDisability> findByPatient(Patient patient) {
        return new Select()
                .from(PatientDisability.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<PatientDisability> getAll(){
        return new Select()
                .from(PatientDisability.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(PatientDisability.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(PatientDisability.class).execute();
    }

    @Override
    public String toString(){
        return "Disability:: " + disabilityCategory.name;
    }
}
