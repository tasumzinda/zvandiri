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
@Table(name = "referral_laboratory_req", id = "_id")
public class ReferralLaboratoryReqContract extends Model {

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred laboratoryReq;
    @Expose
    @Column(name = "id")
    public String id;

    public ReferralLaboratoryReqContract(){
        super();
    }

    public static List<ReferralLaboratoryReqContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralLaboratoryReqContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }
}
