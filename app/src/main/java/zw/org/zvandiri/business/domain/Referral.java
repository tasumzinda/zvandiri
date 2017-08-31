package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.ReferralActionTaken;
import zw.org.zvandiri.business.util.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by jackie muzinda on 10/1/2017.
 */
@Table(name = "referral", id = "_id")
public class Referral extends Model implements Serializable{

    @Expose
    @Column(name = "uuid")
    public String uuid;
    @Expose
    @Column(name = "created_by")
    public User createdBy;
    @Expose
    @Column(name = "modified_by")
    public User modifiedBy;
    @Expose
    @Column(name = "date_created")
    public Date dateCreated;
    @Expose
    @Column(name = "date_modified")
    public Date dateModified;
    @Expose
    @Column(name = "version")
    public Long version;
    @Expose
    @Column(name = "active")
    public Boolean active = Boolean.TRUE;
    @Expose
    @Column(name = "deleted")
    public Boolean deleted = Boolean.FALSE;
    @Expose
    @Column(name = "id")
    public String id;
    @Expose
    @Column(name = "patient")
    public Patient patient;
    @Expose
    @Column(name = "referral_date")
    public Date referralDate;
    @Expose
    @Column(name = "date_attended")
    public Date dateAttended;
    @Expose
    @Column(name = "attending_officer")
    public String attendingOfficer;
    @Expose
    @Column(name = "designation")
    public String designation;
    @Expose
    @Column(name = "organisation")
    public String organisation;
    @Expose
    @Column(name = "expectedVisitDate")
    public Date expectedVisitDate;
    public String servicesRequestedError;
    public String servicesAvailedError;
    @Expose
    @Column(name = "action_taken")
    public ReferralActionTaken actionTaken;
    @Column(name = "pushed")
    public boolean pushed = true;
    @Column(name = "is_new")
    public boolean isNew = false;

    @Expose
    public List<ServicesReferred> hivStiServicesReq;
    public List<String> hivStiServicesReqId;

    @Expose
    public List<ServicesReferred> hivStiServicesAvailed;
    public List<String> hivStiServicesAvailedId;

    @Expose
    public List<ServicesReferred> oiArtReq;
    public List<String> oiArtReqId;

    @Expose
    public List<ServicesReferred> oiArtAvailed;
    public List<String> oiArtAvailedId;

    @Expose
    public List<ServicesReferred> srhReq;
    public List<String> srhReqId;

    @Expose
    public List<ServicesReferred> srhAvailed;
    public List<String> srhAvailedId;

    @Expose
    public List<ServicesReferred> laboratoryReq;
    public List<String> laboratoryReqId;

    @Expose
    public List<ServicesReferred> laboratoryAvailed;
    public List<String> laboratoryAvailedId;

    @Expose
    public List<ServicesReferred> tbReq;
    public List<String> tbReqId;

    @Expose
    public List<ServicesReferred> tbAvailed;
    public List<String> tbAvailedId;

    @Expose
    public List<ServicesReferred> psychReq;
    public List<String> psychReqId;

    @Expose
    public List<ServicesReferred> psychAvailed;
    public List<String> psychAvailedId;

    @Expose
    public List<ServicesReferred> legalReq;
    public List<String> legalReqId;

    @Expose
    public List<ServicesReferred> legalAvailed;
    public List<String> legalAvailedId;

    public static Referral findById(String id){
        return new Select().from(Referral.class).where("id = ?", id).executeSingle();
    }

    public static List<Referral> getAll(){
        return new Select()
                .from(Referral.class)
                .execute();
    }

    public String toString(){
        return "Referral Date: " + DateUtil.formatDate(referralDate);
    }

    public static List<Referral> findByPatient(Patient patient){
        return new Select()
                .from(Referral.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<Referral> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(Referral.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }
}
