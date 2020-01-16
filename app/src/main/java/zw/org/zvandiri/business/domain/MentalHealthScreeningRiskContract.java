package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;

import java.util.List;

@Table(name = "mental_health_screening_risk_contract", id = "_id")
public class MentalHealthScreeningRiskContract extends Model {

    @Expose
    @Column(name = "mental_health_screening")
    public MentalHealthScreening mentalHealthScreening;

    @Expose
    @Column(name = "risk")
    public IdentifiedRisk  identifiedRisk;

    @Expose
    @Column(name = "id")
    public String id;

    public MentalHealthScreeningRiskContract() {
        super();
    }

    public static List<MentalHealthScreeningRiskContract> findByMentalHealthScreening(MentalHealthScreening c){
        return new Select()
                .from(MentalHealthScreeningRiskContract.class)
                .where("mental_health_screening = ?", c.getId())
                .execute();
    }
}
