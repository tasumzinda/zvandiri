package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;
import zw.org.zvandiri.business.domain.util.Referral;

import java.util.List;

@Table(name = "mental_health_screening_referral_contract", id = "_id")
public class MentalHealthScreeningReferralContract extends Model {

    @Expose
    @Column(name = "mental_health_screening")
    public MentalHealthScreening mentalHealthScreening;

    @Expose
    @Column(name = "referral")
    public Referral referral;

    @Expose
    @Column(name = "id")
    public String id;

    public MentalHealthScreeningReferralContract() {
        super();
    }

    public static List<MentalHealthScreeningReferralContract> findByMentalHealthScreening(MentalHealthScreening c){
        return new Select()
                .from(MentalHealthScreeningReferralContract.class)
                .where("mental_health_screening = ?", c.getId())
                .execute();
    }
}
