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
@Table(name = "referral_hiv_sti_req")
public class ReferralHivStiServicesReqContract extends Model {

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred hivStiServicesReq;
    @Expose
    @Column(name = "id")
    public String id;

    public ReferralHivStiServicesReqContract(){
        super();
    }

    public static List<ReferralHivStiServicesReqContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralHivStiServicesReqContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }
}
