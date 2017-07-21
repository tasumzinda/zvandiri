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
@Table(name = "referral_psych_req", id = "_id")
public class ReferralPsychReqContract extends Model {

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred psychReq;
    @Expose
    @Column(name = "id")
    public String id;

    public ReferralPsychReqContract(){
        super();
    }

    public static List<ReferralPsychReqContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralPsychReqContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }

    public static List<ReferralPsychReqContract> getAll(){
        return new Select()
                .from(ReferralPsychReqContract.class)
                .execute();
    }
}
