package zw.org.zvandiri.business.domain;

import android.support.v7.widget.RecyclerView;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.domain.util.ActionTaken;
import zw.org.zvandiri.business.util.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "contact", id = "_id")
public class Contact extends Model implements Serializable{

    @Expose
    @Column(name = "uuid")
    public String uuid;

    @Expose
    @Column(name = "created_by")
    public User createdBy;

    @Expose
    @Column(name = "modified_by")
    public User modifiedBy;

    //@Expose
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
    @Column(name = "contact_date")
    public Date contactDate;
    @Expose
    @Column(name = "care_level")
    public CareLevel careLevel;
    @Expose
    @Column(name = "location")
    public Location location;
    public String locationId;
    @Expose
    @Column(name = "position")
    public Position position;
    public String positionId;
    @Expose
    @Column(name = "reason")
    public Reason reason;
    @Expose
    @Column(name = "period")
    public Period period;
    @Expose
    @Column(name = "internal_referral")
    public InternalReferral internalReferral;
    public String internalReferralId;
    @Expose
    @Column(name = "external_referral")
    public ExternalReferral externalReferral;
    public String externalReferralId;
    @Expose
    @Column(name = "follow_up")
    public FollowUp followUp;
    @Expose
    @Column(name = "subjective")
    public String subjective;
    @Expose
    @Column(name = "objective")
    public String objective;
    @Expose
    @Column(name = "plan")
    public String plan;
    @Expose
    @Column(name = "parent")
    public Contact parent;
    @Expose
    @Column(name = "referred_person")
    public User referredPerson;
    @Column(name = "pushed")
    public int pushed = 0;
    @Column(name = "is_new")
    public int isNew = 0;
    @Expose
    @Column(name = "lastClinicAppointmentDate")
    public Date lastClinicAppointmentDate;
    @Expose
    @Column(name = "attendedClinicAppointment")
    public YesNo attendedClinicAppointment;


    @Expose
    public List<Assessment> assessments;
    public List<String> assessmentId;
    @Expose
    public List<Stable> stables;
    public List<String> stableId;
    @Expose
    public List<Enhanced> enhanceds;
    public List<String> enhancedId;
    @Expose
    @Column
    public zw.org.zvandiri.business.domain.ActionTaken actionTaken;
    public String actionTakenId;

    public Contact() {
        super();
    }

    public Contact(Patient patient) {
        super();
        this.patient = patient;
    }

    public static List<Contact> getAll() {
        return new Select()
                .from(Contact.class)
                .where("is_new = ?", 1)
                .execute();
    }


    public static void deleteItem(String id) {
        new Delete().from(Contact.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Contact.class).execute();
    }

    public static Contact findById(String id){
        return new Select()
                .from(Contact.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<Contact> findByPatient(Patient patient){
        return new Select()
                .from(Contact.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<Contact> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(Contact.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 1)
                .execute();
    }

    @Override
    public String toString(){
        return "Contact Date: " + DateUtil.formatDate(contactDate);
    }
}
