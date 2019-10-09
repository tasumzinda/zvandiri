package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;
import zw.org.zvandiri.business.domain.util.TbSymptom;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "tb_ipt", id = "_id")
public class TbIpt extends Model implements Serializable {

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
    public YesNo screenedForTb;
    @Expose
    @Column
    public Date dateScreened;
    @Expose
    public List<TbSymptom> tbSymptoms;
    @Expose
    @Column
    public YesNo identifiedWithTb;
    @Expose
    @Column
    public TbIdentificationOutcome tbIdentificationOutcome;
    @Expose
    @Column
    public Date dateStartedTreatment;
    //Need further info on this
    @Expose
    @Column
    public String referralForSputum;
    @Expose
    @Column
    public TbTreatmentOutcome tbTreatmentOutcome;
    @Expose
    @Column
    public YesNo referredForIpt;
    @Expose
    @Column
    public YesNo onIpt;
    @Expose
    @Column
    public Date dateStartedIpt;

    public static TbIpt getItem(Long id) {
        return new Select()
                .from(TbIpt.class).where("_id = ?", id).executeSingle();
    }

    public static List<TbIpt> findByPatient(Patient patient) {
        return new Select()
                .from(TbIpt.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<TbIpt> getAll(){
        return new Select()
                .from(TbIpt.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(TbIpt.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(TbIpt.class).execute();
    }

    @Override
    public String toString() {
        return patient.name != null ? patient.name : patient.firstName + " " + patient.lastName;
    }
}
