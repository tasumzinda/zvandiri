package zw.org.zvandiri.business.domain;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "patient", id = "_id")
public class Patient extends Model implements Serializable {


    @Expose
    @Column(name = "name")
    public String name;

    @Column(name = "pushed")
    public int pushed = 0;

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
    public String educationId;
    @Expose
    @Column(name = "education_level")
    public EducationLevel educationLevel;
    public String educationLevelId;
    @Expose
    @Column(name = "date_joined")
    public Date dateJoined;
    @Expose
    @Column(name = "referer")
    public Referer referer;
    public String referrerId;
    @Expose
    @Column(name = "primary_clinic")
    public Facility primaryClinic;
    public String primaryClinicId;
    @Expose
    @Column
    public SupportGroup supportGroup;
    public String supportGroupId;
    @Expose
    @Column(name = "date_tested")
    public Date dateTested;
    @Expose
    @Column(name = "hiv_disclosure_location")
    public HIVDisclosureLocation hIVDisclosureLocation;
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
    public String relationshipId;
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
    public String mobileOwnerRelationId;
    @Expose
    @Column(name = "own_secondary_mobile")
    public YesNo ownSecondaryMobile;
    @Expose
    @Column(name = "secondary_mobile_owner_name")
    public String secondaryMobileOwnerName;
    @Expose
    @Column(name = "secondary_mobile_owner_relation")
    public Relationship secondaryMobileownerRelation;
    public String secondaryMobileownerRelationId;
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
    @Column(name = "selfConsent")
    public YesNo selfConsent;
    @Expose
    public List<DisabilityCategory> disabilityCategorys;
    public ArrayList<String> disabilityCategorysId;
    @Expose
    @Column(name = "refererName")
    public String refererName;
    @Expose
    @Column(name = "oINumber")
    public String oINumber;
    @Expose
    @Column(name = "reasonForNotReachingOLevel")
    public ReasonForNotReachingOLevel reasonForNotReachingOLevel;
    public String reasonForNotReachingOLevelId;
    @Expose
    public Long version = 0L;

    @Expose
    public List<Contact> contacts;

    public static boolean isFinished = true;

    @Column
    @Expose
    public YesNo hei;

    @Expose
    @Column
    public Patient motherOfHei;

    @Column
    @Expose
    public YesNo selfPrimaryCareGiver;

    /*
    Dummy column to save gender as an integer in order to ease database queries where gender is required as a parameter
     */
    @Column
    public Integer sex;


    public Patient() {
        super();
    }

    public String toString(){
        return name != null ? name : firstName + " " + lastName;
    }

    public static Patient findById(String id) {
        return new Select()
                .from(Patient.class).where("id = ?", id).executeSingle();
    }

    public static Patient get(Long id) {
        return new Select()
                .from(Patient.class).where("_id = ?", id).executeSingle();
    }

    public static Patient findByEmail(String email) {
        return new Select()
                .from(Patient.class)
                .where("email = ?", email)
                .executeSingle();
    }

    public static Patient getById(String id) {
        return new Select()
                .from(Patient.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<Patient> findByPushed() {
        return new Select()
                .from(Patient.class)
                .where("pushed = ?", 1)
                .execute();
    }

    public static List<Patient> getAll() {
        return new Select()
                .from(Patient.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<Patient> checkDuplicate(String firstName, String lastName, Date dateOfBirth, Facility facility) {
        return new Select()
                .from(Patient.class)
                .where("first_name LIKE ?", new String[]{'%' + firstName + '%'})
                .and("last_name LIKE ?", new String[]{'%' + lastName + '%'})
                .and("date_of_birth > ? and date_of_birth < ?", DateUtil.getDateDiffMonth(dateOfBirth, -6).getTime(), DateUtil.getDateDiffMonth(dateOfBirth, 6).getTime())
                .and("primary_clinic = ?", facility.getId())
                .execute();

    }

    public static List<Patient> findYoungMothersByName(String name){
        return new Select()
                .from(Patient.class)
                .where("date_of_birth < ?", DateUtil.getDateFromAge(7).getTime())
                .and("date_of_birth > ?", DateUtil.getDateFromAge(41).getTime())
                .and("name LIKE ?", new String[]{'%' + name + '%'})
                .and("sex = ?", 3)
                .execute();
    }

    public static List<Patient> findYoungMothersByName(String firstName, String lastName){
        return new Select()
                .from(Patient.class)
                .where("date_of_birth > ? and date_of_birth < ?", DateUtil.getDateFromAge(7).getTime(), DateUtil.getDateFromAge(41))
                .and("first_name LIKE ?", new String[]{'%' + firstName + '%'})
                .and("last_name LIKE ?", new String[]{'%' + lastName + '%'})
                .and("sex = ?", 3)
                .execute();
    }

    public static List<Patient> getYoungMothers(String... exp){
        if(exp == null){
            throw new IllegalArgumentException("Provide parameters for search");
        }else if(exp.length == 1){
            return findYoungMothersByName(exp[0]);
        }
        return findYoungMothersByName(exp[0], exp[1]);
    }

    public static void fetchRemote(Context context, final String userName, final String password) {
        String URL = AppUtil.getBaseUrl(context) + "/patient/cats-patients?email=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + userName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        List<Patient> itemList = Arrays.asList(AppUtil.createGson().fromJson(response, Patient[].class));
                        for (Patient item : itemList) {
                            Patient checkDuplicate = Patient.getById(item.id);
                            if (checkDuplicate == null) {
                                //item.pushed = true;
                                switch (item.gender){
                                    case MALE:
                                        item.sex = 1;
                                        break;
                                    case OTHER:
                                        item.sex = 2;
                                        break;
                                    case FEMALE:
                                        item.sex = 3;
                                }
                                item.save();
                                //Log.d("Patient", AppUtil.createGson().toJson(item));
                            }

                        }
                        isFinished = false;
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Patient", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", userName, password).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }

}
