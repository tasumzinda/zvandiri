package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by jackie muzinda on 10/1/2017.
 */
@Table(name = "referral_service", id = "_id")
public class ReferralServicesReferredContract extends Model{

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred servicesReferred;

    @Expose
    @Column(name = "id")
    public String id;

    public ReferralServicesReferredContract(){
        super();
    }

    public static List<ReferralServicesReferredContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralServicesReferredContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }
}
