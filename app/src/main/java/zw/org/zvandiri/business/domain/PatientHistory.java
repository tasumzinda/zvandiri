package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.*;

import java.util.Date;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "patient_history", id = "_id")
public class PatientHistory extends Model {

    @Expose
    @Column(name = "uuid")
    public String uuid;

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
    @Column(name = "first_name")
    public String firstName;
    @Expose
    @Column(name = "middle_name")
    public String middleName;
    @Expose
    @Column(name = "last_name")
    public String lastName;
    @Expose
    @Column(name = "gender")
    public Gender gender;
    @Expose
    @Column(name = "consent_to_photo")
    public YesNo consentToPhoto;
    @Expose
    @Column(name = "consent_to_mhealth")
    public YesNo consentToMHealth;
    @Expose
    @Column(name = "period")
    public Period period;
    @Expose
    @Column(name = "address")
    public String address;
    @Expose
    @Column(name = "address1")
    public String address1;
    @Expose
    @Column(name = "mobile_number")
    public String mobileNumber;
    @Expose
    @Column(name = "email")
    public String email;
    @Expose
    @Column(name = "date_of_birth")
    public Date dateOfBirth;
    @Expose
    @Column(name = "education")
    public Education education;
    @Expose
    @Column(name = "education_level")
    public EducationLevel educationLevel;
    @Expose
    @Column(name = "date_joined")
    public Date dateJoined;
    @Expose
    @Column(name = "referer")
    public Referer referer;
    @Expose
    @Column(name = "primary_clinic")
    public Facility primaryClinic;
    @Expose
    @Column(name = "support_group")
    public SupportGroup supportGroup;
    @Expose
    @Column(name = "date_tested")
    public Date dateTested;
    @Expose
    @Column(name = "hiv_disclosure_location")
    public HIVDisclosureLocation hIVDisclosureLocation;
    @Expose
    @Column(name = "vst_student")
    public YesNo vstStudent;
    @Expose
    @Column(name = "disability")
    public YesNo disability;
    @Expose
    @Column(name = "cat")
    public YesNo cat;
    @Expose
    @Column(name = "young_mum_group")
    public YesNo youngMumGroup;
    @Expose
    @Column(name = "pfirst_name")
    public String pfirstName;
    @Expose
    @Column(name = "plast_name")
    public String plastName;
    @Expose
    @Column(name = "pmobile_number")
    public String pmobileNumber;
    @Expose
    @Column(name = "pgender")
    public Gender pgender;
    @Expose
    @Column(name = "relationship")
    public Relationship relationship;
    @Expose
    @Column(name = "secondary_mobile_number")
    public String secondaryMobileNumber;
    @Expose
    @Column(name = "mobile_owner")
    public YesNo mobileOwner;
    @Expose
    @Column(name = "owner_name")
    public String ownerName;
    @Expose
    @Column(name = "mobile_owner_relation")
    public Relationship mobileOwnerRelation;
    @Expose
    @Column(name = "own_secondary_mobile")
    public YesNo ownSecondaryMobile;
    @Expose
    @Column(name = "secondary_mobile_owner_name")
    public String secondaryMobileOwnerName;
    @Expose
    @Column(name = "secondary_mobile_owner_relation")
    public Relationship secondaryMobileownerRelation;
    @Expose
    @Column(name = "transmission_mode")
    public TransmissionMode transmissionMode;
    @Expose
    @Column(name = "hiv_status_known")
    public YesNo hivStatusKnown;
    @Expose
    @Column(name = "photo")
    public String photo;
    @Expose
    @Column(name = "consent_form")
    public String consentForm;
    @Expose
    @Column(name = "mhealth_form")
    public String mhealthForm;
    @Expose
    @Column(name = "status")
    public PatientChangeEvent status;
    @Expose
    @Column(name = "vstStatus")
    public PatientChangeEvent vstStatus;

    @Expose
    @Column(name = "patient")
    private Patient patient;

    public PatientHistory() {
        super();
    }

    /*public PatientHistory(Patient patient) {
        super();
        this.patient = patient;
        this.active = patient.active;
        this.address = patient.address;
        this.address1 = patient.address1;
        this.cat = patient.cat;
        this.consentForm = patient.consentForm;
        this.consentToMHealth = patient.consentToMHealth;
        this.consentToPhoto = patient.consentToPhoto;
        this.dateCreated = patient.dateCreated;
        this.dateJoined = patient.dateJoined;
        this.dateModified = patient.dateModified;
        this.dateOfBirth = patient.dateOfBirth;
        this.dateTested = patient.dateTested;
        this.deleted = patient.deleted;
        this.disability = patient.disability;
        this.education = patient.education;
        this.educationLevel = patient.educationLevel;
        this.email = patient.email;
        this.firstName = patient.firstName;
        this.gender = patient.gender;
        this.hivStatusKnown = patient.hivStatusKnown;
        this.lastName = patient.lastName;
        this.mhealthForm = patient.mhealthForm;
        this.middleName = patient.middleName;
        this.mobileNumber = patient.mobileNumber;
        this.mobileOwner = patient.mobileOwner;
        this.mobileOwnerRelation = patient.mobileOwnerRelation;
        this.ownSecondaryMobile = patient.ownSecondaryMobile;
        this.ownerName = patient.ownerName;
        this.pfirstName = patient.pfirstName;
        this.pgender = patient.pgender;
        this.photo = patient.photo;
        this.plastName = patient.photo;
        this.pmobileNumber = patient.pmobileNumber;
        this.primaryClinic = patient.primaryClinic;
        this.referer = patient.referer;
        this.relationship = patient.relationship;
        this.secondaryMobileNumber = patient.secondaryMobileNumber;
        this.secondaryMobileOwnerName = patient.secondaryMobileOwnerName;
        this.secondaryMobileownerRelation = patient.secondaryMobileownerRelation;
        this.supportGroup = patient.supportGroup;
        this.transmissionMode = patient.transmissionMode;
        this.vstStudent = patient.vstStudent;
        this.youngMumGroup = patient.youngMumGroup;
        this.hIVDisclosureLocation = patient.hIVDisclosureLocation;

    }*/
}
