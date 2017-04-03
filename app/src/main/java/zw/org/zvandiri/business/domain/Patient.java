package zw.org.zvandiri.business.domain;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
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

import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "patient", id = "_id")
public class Patient extends Model {

    @Expose
    @Column(name = "id")
    public String id;

    @Expose
    @Column(name = "name")
    public String name;

    @Column(name = "pushed")
    public boolean pushed = true;

   /* @Expose
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
    public List<Dependent> dependents;
    @Expose
    public List<MedicalHist> medicalHists;
    @Expose
    public List<ChronicInfectionItem> chronicInfectionItems;
    @Expose
    public List<HivConInfectionItem> hivConInfectionItems;
    @Expose
    public List<ArvHist> arvHists;
    @Expose
    public List<MentalHealthItem> mentalHealthItems;
    @Expose
    public List<SrhHist> srhHists;
    @Expose
    public List<ObstercHist> obstercHists;
    @Expose
    public List<SocialHist> socialHists;
    @Expose
    public List<SubstanceItem> substanceItems;
    @Expose
    public List<Family> familys;

    public List<Referral> referrals;
    @Expose
    public List<Cd4Count> cd4Counts;
    @Expose
    public List<ViralLoad> viralLoads;
    public List<DisabilityCategory> disabilityCategorys;*/

   @Expose
   public List<Contact> contacts;

    public static boolean isFinished = true;

    public Patient() {
        super();
    }

    public static Patient findById(String id){
        return new Select().from(Patient.class).where("id = ?", id).executeSingle();
    }


    public static Patient getById(String id){
        return new Select()
                .from(Patient.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<Patient> findByPushed(){
        return new Select()
                .from(Patient.class)
                .where("pushed = ?", 0)
                .execute();
    }

    public static List<Patient> getAll(){
        return new Select()
                .from(Patient.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void fetchRemote(Context context, final String userName, final String password){
        String URL = AppUtil.getBaseUrl(context) + "/patient/cats-patients?email=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + userName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Patient> itemList = Arrays.asList(AppUtil.createGson().fromJson(response, Patient[].class));
                        for(Patient item : itemList){
                            Patient checkDuplicate = Patient.getById(item.id);
                            if(checkDuplicate == null){
                                item.save();
                            }

                        }
                        isFinished = false;
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Location", error.toString());
                    }
                }
        ){
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

   /*public static void fetchRemote(Context context, final String userName, final String password){
       String URL = AppUtil.getBaseUrl(context) + "/patient/cats-patients?email=";
       //String URL = "http://192.168.20.127:8084/zvandiri-mobile/rest/mobile/patient/cats-patients?email=";
        JsonArrayRequest request = new JsonArrayRequest(URL+userName,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        for(int i = 0; i < response.length(); i++){
                            try{
                                JSONObject jsonObject = response.getJSONObject(i);
                                Patient item = new Patient();
                                item.id = jsonObject.getString("id");
                                item.plastName = jsonObject.getString("plastName");
                                item.pfirstName = jsonObject.getString("pfirstName");
                                item.active = jsonObject.getBoolean("active");
                                item.address = jsonObject.getString("address");
                                item.address1 = jsonObject.getString("address1");
                                item.consentForm = jsonObject.getString("consentForm");
                                item.dateCreated = DateUtil.getFromString(jsonObject.getString("dateCreated"));
                                item.dateJoined = DateUtil.getFromString(jsonObject.getString("dateJoined"));
                                if( ! jsonObject.isNull("dateModified")){
                                    item.dateModified = DateUtil.getFromString(jsonObject.getString("dateModified"));
                                }
                                item.dateOfBirth = DateUtil.getFromString(jsonObject.getString("dateOfBirth"));
                                if( ! jsonObject.isNull("dateTested")){
                                    item.dateTested = DateUtil.getFromString(jsonObject.getString("dateTested"));
                                }
                                if( ! jsonObject.isNull("supportGroup")){
                                    JSONObject supportGroup = jsonObject.getJSONObject("supportGroup");
                                    item.supportGroup = SupportGroup.getItem(supportGroup.getString("id"));
                                }
                                if( ! jsonObject.isNull("education")){
                                    JSONObject education = jsonObject.getJSONObject("education");
                                    item.education = Education.getItem(education.getString("id"));
                                }
                                if( ! jsonObject.isNull("educationLevel")){
                                    JSONObject educationLevel = jsonObject.getJSONObject("educationLevel");
                                    item.educationLevel = EducationLevel.getItem(educationLevel.getString("id"));
                                }
                                if( ! jsonObject.isNull("")){
                                    JSONObject mobileOwnerRelation = jsonObject.getJSONObject("mobileOwnerRelation");
                                    item.mobileOwnerRelation = Relationship.getItem(mobileOwnerRelation.getString("id"));
                                }
                                if( ! jsonObject.isNull("primaryClinic")){
                                    JSONObject primaryClinic = jsonObject.getJSONObject("primaryClinic");
                                    item.primaryClinic = Facility.getItem(primaryClinic.getString("id"));
                                }
                                if( ! jsonObject.isNull("referer")){
                                    JSONObject refer = jsonObject.getJSONObject("referer");
                                    item.referer = Referer.getItem(refer.getString("id"));
                                }
                                if(! jsonObject.isNull("secondaryMobileownerRelation")){
                                    JSONObject secondaryMobileownerRelation = jsonObject.getJSONObject("secondaryMobileownerRelation");
                                    item.secondaryMobileownerRelation = Relationship.getItem(secondaryMobileownerRelation.getString("id"));
                                }
                                if( ! jsonObject.isNull("relationship")){
                                    JSONObject relationship = jsonObject.getJSONObject("relationship");
                                    item.relationship = Relationship.getItem(relationship.getString("id"));
                                }

                                if( ! jsonObject.isNull("cat")){
                                    item.cat = YesNo.valueOf(jsonObject.getString("cat"));
                                }
                                if( ! jsonObject.isNull("consentToMHealth")){
                                    item.consentToMHealth = YesNo.valueOf(jsonObject.getString("consentToMHealth"));
                                }
                                if( ! jsonObject.isNull("consentToPhoto")){
                                    item.consentToPhoto = YesNo.valueOf(jsonObject.getString("consentToPhoto"));
                                }
                                item.deleted = jsonObject.getBoolean("deleted");
                                if( ! jsonObject.isNull("disability")){
                                    item.disability = YesNo.valueOf(jsonObject.getString("disability"));
                                }
                                item.gender = Gender.valueOf(jsonObject.getString("gender"));
                                if( ! jsonObject.isNull("hivDisclosureLocation")){
                                    item.hIVDisclosureLocation = HIVDisclosureLocation.valueOf(jsonObject.getString("hivDisclosureLocation"));
                                }
                                if( ! jsonObject.isNull("hivStatusKnown")){
                                    item.hivStatusKnown = YesNo.valueOf(jsonObject.getString("hivStatusKnown"));
                                }
                                if( ! jsonObject.isNull("mobileOwner")){
                                    item.mobileOwner = YesNo.valueOf(jsonObject.getString("mobileOwner"));
                                }
                                item.ownerName = jsonObject.getString("ownerName");
                                if( ! jsonObject.isNull("ownSecondaryMobile")){
                                    item.ownSecondaryMobile = YesNo.valueOf(jsonObject.getString("ownSecondaryMobile"));
                                }
                                if( ! jsonObject.isNull("period")){
                                    JSONObject period = jsonObject.getJSONObject("period");
                                    item.period = Period.getItem(period.getString("id"));
                                }
                                if( ! jsonObject.isNull("pgender")){
                                    item.pgender = Gender.valueOf(jsonObject.getString("pgender"));
                                }
                                if( ! jsonObject.isNull("transmissionMode")){
                                    item.transmissionMode = TransmissionMode.valueOf(jsonObject.getString("transmissionMode"));
                                }
                                item.uuid = jsonObject.getString("uuid");
                                if( ! jsonObject.isNull("vstStatus")){
                                    item.vstStatus = PatientChangeEvent.valueOf(jsonObject.getString("vstStatus"));
                                }
                                item.version = jsonObject.getLong("version");
                                if( ! jsonObject.isNull("vstStudent")){
                                    item.vstStudent = YesNo.valueOf(jsonObject.getString("vstStudent"));
                                }
                                if( ! jsonObject.isNull("youngMumGroup")){
                                    item.youngMumGroup = YesNo.valueOf(jsonObject.getString("youngMumGroup"));
                                }
                                item.email = jsonObject.getString("email");
                                item.firstName = jsonObject.getString("firstName");
                                item.lastName = jsonObject.getString("lastName");
                                item.mhealthForm = jsonObject.getString("mhealthForm");
                                item.middleName = jsonObject.getString("middleName");
                                item.mobileNumber = jsonObject.getString("mobileNumber");
                                item.photo = jsonObject.getString("photo");
                                item.pmobileNumber = jsonObject.getString("pmobileNumber");
                                item.secondaryMobileNumber = jsonObject.getString("secondaryMobileNumber");
                                item.secondaryMobileOwnerName = jsonObject.getString("secondaryMobileOwnerName");
                                item.save();
                                Log.d("Saved patient", item.firstName);
                                saveMentalHealthItems(jsonObject);
                                saveArvHists(jsonObject);
                                saveChronicInfectionItems(jsonObject);
                                saveHivConInfectionItems(jsonObject);
                                saveSrhHists(jsonObject);
                                saveObstetricHists(jsonObject);
                                saveSocialHists(jsonObject);
                                saveSubstanceItems(jsonObject);
                                saveFamilys(jsonObject);
                                saveDisabilityCategorys(jsonObject);
                                saveMedicalHists(jsonObject);
                                saveContacts(jsonObject);
                            }catch (JSONException exc){
                                Log.d("Patient", exc.getMessage());
                            }
                        }
                        isFinished = false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Patient Error", error.toString());
                    }
                }
        ){
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
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(request);
    }*/

    public String toString(){
        return name;
    }

    private static void saveMentalHealthItems(JSONObject jsonObject){
        try{
            JSONArray mentalHealthItems = jsonObject.getJSONArray("mentalHealthItems");
            for(int j = 0; j < mentalHealthItems.length(); j++){
                JSONObject mentalHealthItem = mentalHealthItems.getJSONObject(j);
                MentalHealthItem m = new MentalHealthItem();
                m.beenHospitalized = YesNo.valueOf(mentalHealthItem.getString("beenHospitalized"));
                if( ! jsonObject.isNull("profHelpStart")){
                    m.profHelpStart = DateUtil.getFromString(mentalHealthItem.getString("profHelpStart"));
                }
                if(! jsonObject.isNull("profHelpEnd")){
                    m.profHelpEnd = DateUtil.getFromString(mentalHealthItem.getString("profHelpEnd"));
                }
                m.startDate = DateUtil.getDateFromString(mentalHealthItem.getString("startDate"));
                m.receivedProfessionalHelp = YesNo.valueOf(mentalHealthItem.getString("receivedProfessionalHelp"));
                if(! jsonObject.isNull("endDate")){
                    m.endDate = DateUtil.getFromString(mentalHealthItem.getString("endDate"));
                }
                m.current = mentalHealthItem.getString("current");
                m.id = mentalHealthItem.getString("id");
                m.medication = mentalHealthItem.getString("medication");
                m.mentalHistText = mentalHealthItem.getString("mentalHistText");
                JSONObject mentalHealth = mentalHealthItem.getJSONObject("mentalHealth");
                m.mentalHealth = MentalHealth.getItem(mentalHealth.getString("id"));
                m.patient = Patient.findById(jsonObject.getString("id"));
                if(! jsonObject.isNull("startDate")){
                    m.startDate = DateUtil.getFromString(mentalHealthItem.getString("startDate"));
                }
                m.save();
                Log.d("MentalHealthItem", m.mentalHealth.name);
            }
        }catch (JSONException ex){
            Log.d("Mental Health", ex.getMessage());
        }
    }

    private static void saveArvHists(JSONObject jsonObject){
        try{
            JSONArray arvHists = jsonObject.getJSONArray("arvHists");
            for(int k = 0; k < arvHists.length(); k++){
                ArvHist a = new ArvHist();
                JSONObject arvHist = arvHists.getJSONObject(k);
                a.id = arvHist.getString("id");
                if( ! arvHist.isNull("endDate")){
                    a.endDate = DateUtil.getFromString(arvHist.getString("endDate"));
                }
                a.patient = Patient.findById(jsonObject.getString("id"));
                if( ! arvHist.isNull("startDate")){
                    a.startDate = DateUtil.getFromString(arvHist.getString("startDate"));
                }
                if( ! arvHist.isNull("arvMedicine")){
                    JSONObject arvMedicine = arvHist.getJSONObject("arvMedicine");
                    a.arvMedicine = ArvMedicine.getItem(arvMedicine.getString("id"));
                }
                a.save();
            }
        }catch(JSONException ex){
            Log.d("Arv Hist", ex.getMessage());
        }
    }

    private static void saveChronicInfectionItems(JSONObject jsonObject){
        try{
            JSONArray chronicInfectionItems = jsonObject.getJSONArray("chronicInfectionItems");
            for(int k = 0; k < chronicInfectionItems.length(); k++){
                ChronicInfectionItem item = new ChronicInfectionItem();
                JSONObject object = chronicInfectionItems.getJSONObject(k);
                item.id = object.getString("id");
                item.patient = Patient.findById(jsonObject.getString("id"));
                if( ! object.isNull("currentStatus")){
                    item.currentStatus = CurrentStatus.valueOf(object.getString("currentStatus"));
                }
                item.medication = object.getString("medication");
                if( ! object.isNull("infectionDate")){
                    item.infectionDate = DateUtil.getFromString(object.getString("infectionDate"));
                }
                if( ! object.isNull("chronicInfection")){
                    JSONObject chronicInfection = object.getJSONObject("chronicInfection");
                    item.chronicInfection = ChronicInfection.getItem(chronicInfection.getString("id"));
                }
                item.dateCreated = new Date();
                item.save();
                Log.d("Saved Chronic Infection", item.chronicInfection.name);
            }
        }catch (JSONException ex){
            Log.d("Chronic Infection", ex.getMessage());
        }
    }

    private static void saveHivConInfectionItems(JSONObject jsonObject){
        try{
                JSONArray items = jsonObject.getJSONArray("hivConInfectionItems");
            for(int i = 0; i < items.length(); i++){
                HivConInfectionItem h = new HivConInfectionItem();
                JSONObject object = items.getJSONObject(i);
                h.id = object.getString("id");
                if( ! object.isNull("hivCoInfection")){
                    JSONObject hivCoInfection = object.getJSONObject("hivCoInfection");
                    h.hivCoInfection = HivCoInfection.getItem(hivCoInfection.getString("id"));
                }
                h.resolution = object.getString("resolution");
                h.medication = object.getString("medication");
                if( ! object.isNull("infectionDate")){
                    h.infectionDate = DateUtil.getFromString(object.getString("infectionDate"));
                }
                h.patient = Patient.findById(jsonObject.getString("id"));
                h.save();
                Log.d("Saved Con-Infection", h.hivCoInfection.name);
            }
        }catch (JSONException ex){
            Log.d("Con-Infection", ex.getMessage());
        }
    }

    private static void saveSrhHists(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.getJSONArray("srhHists");
            for(int i = 0; i < items.length(); i++){
                SrhHist s = new SrhHist();
                JSONObject object = items.getJSONObject(i);
                s.bleeddays = Integer.parseInt(object.getString("bleeddays"));
                s.id = object.getString("id");
                if( ! object.isNull("sexuallyActive")){
                    s.sexuallyActive = YesNo.valueOf(object.getString("sexuallyActive"));
                }
                if( ! object.isNull("sexualIntercourse")){
                    s.sexualIntercourse = YesNo.valueOf(object.getString("sexualIntercourse"));
                }
                s.ageStartMen = Integer.parseInt(object.getString("ageStartMen"));
                if( ! object.isNull("birthControl")){
                    s.birthControl = YesNo.valueOf(object.getString("birthControl"));
                }
                s.bleedHowOften = Integer.parseInt(object.getString("bleedHowOften"));
                if( ! object.isNull("condomUse")){
                    s.condomUse = CondomUse.valueOf(object.getString("condomUse"));
                }
                s.patient = Patient.findById(jsonObject.getString("id"));
                s.save();
                Log.d("Saved Srh Hist", s.ageStartMen.toString());
            }
        }catch (JSONException ex){
            Log.d("Srh Hist", ex.getMessage());
        }
    }

    private static void saveObstetricHists(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.getJSONArray("obstercHists");
            for(int i = 0; i < items.length(); i++){
                ObstercHist o = new ObstercHist();
                JSONObject object = items.getJSONObject(i);
                if( ! object.isNull("pregCurrent")){
                    o.pregCurrent = YesNo.valueOf(object.getString("pregCurrent"));
                }
                o.id = object.getString("id");
                if( ! object.isNull("pregType")){
                    o.pregType = PregType.valueOf(object.getString("pregType"));
                }
                if( ! object.isNull("pregnant")){
                    o.pregnant = YesNo.valueOf(object.getString("pregnant"));
                }
                o.children = Integer.parseInt(object.getString("children"));
                o.childrenHivStatus = object.getString("childrenHivStatus");
                if( ! object.isNull("givenBirth")){
                    o.givenBirth = YesNo.valueOf(object.getString("givenBirth"));
                }
                o.numberOfPreg = Integer.parseInt(object.getString("numberOfPreg"));
                o.patient = Patient.findById(jsonObject.getString("id"));
                o.save();
                Log.d("Save Obstetric Hist", o.givenBirth.getName());
            }
        }catch (JSONException ex){
            Log.d("Obstetric Hist", ex.getMessage());
        }
    }

    private static void saveSocialHists(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.getJSONArray("socialHists");
            for(int i = 0; i < items.length(); i++){
                SocialHist s = new SocialHist();
                JSONObject object = items.getJSONObject(i);
                s.id = object.getString("id");
                if( ! object.isNull("abuseType")){
                    s.abuseType = AbuseType.valueOf(object.getString("abuseType"));
                }
                if( ! object.isNull("feelSafe")){
                    s.feelSafe = YesNo.valueOf(object.getString("feelSafe"));
                }
                if( ! object.isNull("abuse")){
                    s.abuse = YesNo.valueOf(object.getString("abuse"));
                }
                if( ! object.isNull("abuseOutcome")){
                    s.abuseOutcome = AbuseOutcome.valueOf(object.getString("abuseOutcome"));
                }
                if( ! object.isNull("dosclosure")){
                    s.dosclosure = YesNo.valueOf(object.getString("dosclosure"));
                }
                s.liveWith = object.getString("liveWith");
                s.patient = Patient.findById(jsonObject.getString("id"));
                if( ! object.isNull("relationship")){
                    JSONObject relationship = object.getJSONObject("relationship");
                    s.relationship = Relationship.getItem(relationship.getString("id"));
                }
                s.save();
                Log.d("Saved Social Hist", s.abuseType.getName());
            }
        }catch (JSONException ex){
            Log.d("Social Hist", ex.getMessage());
        }
    }

    private static void saveSubstanceItems(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.getJSONArray("substanceItems");
            for(int i = 0; i < items.length(); i++){
                SubstanceItem s = new SubstanceItem();
                JSONObject object = items.getJSONObject(i);
                s.id = object.getString("id");
                s.typeAmount = object.getString("typeAmount");
                if( ! object.isNull("substance")){
                    JSONObject substance = object.getJSONObject("substance");
                    s.substance = Substance.getItem(substance.getString("id"));
                }
                if( ! object.isNull("startDate")){
                    s.startDate = DateUtil.getFromString(object.getString("startDate"));
                }
                if( ! object.isNull("drugIntervention")){
                    s.drugIntervention = DrugIntervention.valueOf(object.getString("drugIntervention"));
                }
                if( ! object.isNull("endDate")){
                    s.endDate = DateUtil.getFromString(object.getString("endDate"));
                }
                if( ! object.isNull("current")){
                    s.current = YesNo.valueOf(object.getString("current"));
                }
                if( ! object.isNull("past")){
                    s.past = YesNo.valueOf(object.getString("past"));
                }
                s.patient = Patient.findById(jsonObject.getString("id"));
                s.save();
                Log.d("Saved substance item", s.substance.name);
            }
        }catch (JSONException ex){
            Log.d("Substance Item", ex.getMessage());
        }
    }

    private static void saveFamilys(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.getJSONArray("familys");
            for(int i = 0; i < items.length(); i++){
                Family f = new Family();
                JSONObject object = items.getJSONObject(i);
                f.patient = Patient.findById(jsonObject.getString("id"));
                f.id = object.getString("id");
                f.numberOfSiblings = Integer.parseInt(object.getString("numberOfSiblings"));
                if( ! object.isNull("orphanStatus")){
                    JSONObject orphanStatus = object.getJSONObject("orphanStatus");
                    f.orphanStatus = OrphanStatus.getItem(orphanStatus.getString("id"));
                }
                f.save();
                Log.d("Saved family", f.orphanStatus.name);
            }
        }catch (JSONException ex){
            Log.d("Familys", ex.getMessage());
        }
    }

    private static void saveDisabilityCategorys(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.getJSONArray("disabilityCategorys");
            for(int i = 0; i < items.length(); i++){
                DisabilityCategory d = new DisabilityCategory();
                JSONObject object = items.getJSONObject(i);
                d.id = object.getString("id");
                d.name = object.getString("name");
                d.description = object.getString("description");
                d.save();
                PatientDisabilityCategoryContract item = new PatientDisabilityCategoryContract();
                item.disabilityCategory = DisabilityCategory.getItem(object.getString("id"));
                item.patient = Patient.findById(jsonObject.getString("id"));
                item.save();
                Log.d("Save disabilit category", d.name);
            }
        }catch (JSONException ex){
            Log.d("Disability category", ex.getMessage());
        }
    }

    private static void saveMedicalHists(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.getJSONArray("medicalHists");
            for(int i = 0; i < items.length(); i++){
                JSONObject object = items.getJSONObject(i);
                MedicalHist m = new MedicalHist();
                if( ! object.isNull("hospCause")){
                    JSONObject hospCause = object.getJSONObject("hospCause");
                    m.hospCause = HospCause.getItem(hospCause.getString("hospCause"));
                }
                if( ! object.isNull("hospWhen")){
                    m.hospWhen = DateUtil.getFromString(object.getString("hospWhen"));
                }
                m.id = object.getString("id");
                m.patient = Patient.findById(jsonObject.getString("id"));
                if( ! object.isNull("primaryClinic")){
                    JSONObject hospCause = object.getJSONObject("hospCause");
                    m.primaryClinic = Facility.getItem(hospCause.getString("primaryClinic"));
                }
                m.save();
                Log.d("Saved medical hist", m.hospCause.name);
            }
        }catch (JSONException ex){
            Log.d("Medical Hist", ex.getMessage());
        }
    }

    private static void saveContacts(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.getJSONArray("contacts");
            for(int i = 0; i < items.length(); i++){
                JSONObject object = items.getJSONObject(i);
                Contact c = new Contact();
                String contactId = object.getString("id");
                c.id = contactId;
                c.plan = object.getString("plan");
                c.subjective = object.getString("subjective");
                c.objective = object.getString("objective");
                c.patient = Patient.findById(jsonObject.getString("id"));
                if( ! object.isNull("contactDate")){
                    c.contactDate = DateUtil.getFromString(object.getString("contactDate"));
                }
                if( ! object.isNull("careLevel")){
                    c.careLevel = CareLevel.valueOf(object.getString("careLevel"));
                }
                if( ! object.isNull("reason")){
                    c.reason = Reason.valueOf(object.getString("reason"));
                }
                if( ! object.isNull("externalReferral")){
                    JSONObject externalReferral = object.getJSONObject("externalReferral");
                    c.externalReferral = ExternalReferral.getItem(externalReferral.getString("id"));
                }
                if( ! object.isNull("followUp")){
                    c.followUp = FollowUp.valueOf(object.getString("followUp"));
                }
                if( ! object.isNull("internalReferral")){
                    JSONObject internalReferral = object.getJSONObject("internalReferral");
                    c.internalReferral = InternalReferral.getItem(internalReferral.getString("id"));
                }
                if( ! object.isNull("location")){
                    JSONObject location = object.getJSONObject("location");
                    c.location = Location.getItem(location.getString("id"));
                }
                if( ! object.isNull("position")){
                    JSONObject position = object.getJSONObject("position");
                    c.position = Position.getItem(position.getString("id"));
                }
                c.save();
                JSONArray assessments = object.getJSONArray("assessments");
                for(int j = 0; j < assessments.length(); j++){
                    JSONObject ass = assessments.getJSONObject(j);
                    ContactAssessmentContract contract = new ContactAssessmentContract();
                    contract.contact = Contact.findById(contactId);
                    contract.assessment = Assessment.getItem(ass.getString("id"));
                    contract.save();
                }
                JSONArray enhanceds = object.getJSONArray("enhanceds");
                for(int j = 0; j < enhanceds.length(); j++){
                    JSONObject enh = enhanceds.getJSONObject(j);
                    ContactEnhancedContract contract = new ContactEnhancedContract();
                    contract.contact = Contact.findById(contactId);
                    contract.enhanced = Enhanced.getItem(enh.getString("id"));
                    contract.save();
                }
                JSONArray intensives = object.getJSONArray("intensives");
                for(int j = 0; j < intensives.length(); j++){
                    JSONObject enh = intensives.getJSONObject(j);
                    ContactIntensiveContract contract = new ContactIntensiveContract();
                    contract.contact = Contact.findById(contactId);
                    contract.intensive = Intensive.getItem(enh.getString("id"));
                    contract.save();
                }
                JSONArray stables = object.getJSONArray("stables");
                for(int j = 0; j < stables.length(); j++){
                    JSONObject enh = stables.getJSONObject(j);
                    ContactStableContract contract = new ContactStableContract();
                    contract.contact = Contact.findById(contactId);
                    contract.stable = Stable.getItem(enh.getString("id"));
                    contract.save();
                }
                Log.d("Saved contact", c.contactDate.toString());
            }
        }catch(JSONException ex){
            Log.d("Contact", ex.getMessage());
        }
    }

}
