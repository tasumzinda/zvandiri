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
@Table(name = "referral_oi_art_req")
public class ReferralOIArtReqContract extends Model{

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred oiArtReq;
    @Expose
    @Column(name = "id")
    public String id;

    public ReferralOIArtReqContract(){
        super();
    }

    public static List<ReferralOIArtReqContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralOIArtReqContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }
}
