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
@Table(name = "referral_legalb_availed", id = "_id")
public class ReferralLegalAvailedContract extends Model {

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred legalAvailed;
    @Expose
    @Column(name = "id")
    public String id;

    public ReferralLegalAvailedContract(){
        super();
    }

    public static List<ReferralLegalAvailedContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralLegalAvailedContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }
}
