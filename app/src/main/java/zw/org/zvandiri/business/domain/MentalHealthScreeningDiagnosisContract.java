package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Diagnosis;

import java.util.List;

@Table(name = "mental_health_screening_diagnosis_contract", id = "_id")
public class MentalHealthScreeningDiagnosisContract extends Model {

    @Expose
    @Column(name = "mental_health_screening")
    public MentalHealthScreening mentalHealthScreening;

    @Expose
    @Column(name = "diagnosis")
    public Diagnosis diagnosis;

    @Expose
    @Column(name = "id")
    public String id;

    public MentalHealthScreeningDiagnosisContract() {
        super();
    }

    public static List<MentalHealthScreeningDiagnosisContract> findByMentalHealthScreening(MentalHealthScreening c){
        return new Select()
                .from(MentalHealthScreeningDiagnosisContract.class)
                .where("mental_health_screening = ?", c.getId())
                .execute();
    }
}
