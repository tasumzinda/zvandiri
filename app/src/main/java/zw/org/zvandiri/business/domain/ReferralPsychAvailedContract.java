package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by User on 4/6/2017.
 */
@Table(name = "referral_psychb_availed", id = "_id")
public class ReferralPsychAvailedContract extends Model {

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred psychAvailed;
    @Expose
    @Column(name = "id")
    public String id;

    public ReferralPsychAvailedContract(){
        super();
    }

    public static List<ReferralPsychAvailedContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralPsychAvailedContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }
}
