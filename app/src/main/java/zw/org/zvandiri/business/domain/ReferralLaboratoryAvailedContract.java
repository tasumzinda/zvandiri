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
@Table(name = "referral_laboratory_availed", id = "_id")
public class ReferralLaboratoryAvailedContract extends Model {

    @Expose
    @Column(name = "referral_id")
    public Referral referral;
    @Expose
    @Column(name = "service_id")
    public ServicesReferred laboratoryAvailed;
    @Expose
    @Column(name = "id")
    public String id;

    public ReferralLaboratoryAvailedContract(){
        super();
    }

    public static List<ReferralLaboratoryAvailedContract> findByReferral(Referral r){
        return new Select()
                .from(ReferralLaboratoryAvailedContract.class)
                .where("referral_id = ?", r.getId())
                .execute();
    }

    public static List<ReferralLaboratoryAvailedContract> getAll(){
        return new Select()
                .from(ReferralLaboratoryAvailedContract.class)
                .execute();
    }
}
