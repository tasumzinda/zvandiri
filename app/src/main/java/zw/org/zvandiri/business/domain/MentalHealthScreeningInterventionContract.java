package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Intervention;

import java.util.List;

@Table(name = "mental_health_screening_intervention_contract", id = "_id")
public class MentalHealthScreeningInterventionContract extends Model {

    @Expose
    @Column(name = "mental_health_screening")
    public MentalHealthScreening mentalHealthScreening;

    @Expose
    @Column(name = "intervention")
    public Intervention intervention;

    @Expose
    @Column(name = "id")
    public String id;

    public MentalHealthScreeningInterventionContract() {
        super();
    }

    public static List<MentalHealthScreeningInterventionContract> findByMentalHealthScreening(MentalHealthScreening c){
        return new Select()
                .from(MentalHealthScreeningInterventionContract.class)
                .where("mental_health_screening = ?", c.getId())
                .execute();
    }
}
