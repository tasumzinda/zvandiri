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
@Table(name = "referral_oi_art_availed", id = "_id")
public class ReferralOIArtAvailedContract extends Model {

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred oiArtAvailed;
    @Expose
    @Column(name = "id")
    public String id;

    public ReferralOIArtAvailedContract(){
        super();
    }

    public static List<ReferralOIArtAvailedContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralOIArtAvailedContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }

    public static List<ReferralOIArtAvailedContract> getAll(){
        return new Select()
                .from(ReferralOIArtAvailedContract.class)
                .execute();
    }
}
