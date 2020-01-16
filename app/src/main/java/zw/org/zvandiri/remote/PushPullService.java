package zw.org.zvandiri.remote;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;
import zw.org.zvandiri.toolbox.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 4/2/2017.
 */
public class PushPullService extends IntentService {

    public static final String NOTIFICATION = "zw.org.zvandiri";
    private Context context = this;
    public static final String RESULT = "result";
    private int result = Activity.RESULT_CANCELED;

    public PushPullService() {
        super("PushPullService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int result = Activity.RESULT_OK;
        try{
            for(Person item : Person.findNew()){
                String res = run(AppUtil.getPushPersonUrl(context), item);
                String code = "";
                try{
                    JSONObject object = new JSONObject(res);
                    code = object.getString("statusCode");
                    if(code.equals("OK")){
                        JSONObject body = object.getJSONObject("body");
                        String id = body.getString("message");
                        item.id = id;
                        item.pushed = 1;
                        item.save();
                    }
                }catch (JSONException ex){
                    ex.printStackTrace();
                    result = Activity.RESULT_CANCELED;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
        try{
            for(HivSelfTesting testing : HivSelfTesting.getAll()){
                String testingOutcome = run(AppUtil.getPushHivSelfTestingUrl(context), testing);
                String code = "";
                JSONObject jsonObject = new JSONObject(testingOutcome);
                code = jsonObject.getString("statusCode");
                if(code.equals("OK")){
                    testing.delete();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
        try{
            for(Contact item : getAllContacts()){
                String res = run(AppUtil.getPushContactUrl(context), item);
                String code = "";
                try{
                    JSONObject object = new JSONObject(res);
                    code = object.getString("statusCode");
                }catch (JSONException ex){
                    ex.printStackTrace();
                    result = Activity.RESULT_CANCELED;
                }
                if(code.equals("OK")){
                    for(ContactStableContract c : ContactStableContract.findByContact(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ContactEnhancedContract c: ContactEnhancedContract.findByContact(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ContactClinicalAssessmentContract c: ContactClinicalAssessmentContract.findByContact(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ContactNonClinicalAssessmentContract c: ContactNonClinicalAssessmentContract.findByContact(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ContactServiceOfferedContract c: ContactServiceOfferedContract.findByContact(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ContactLabTaskContact c: ContactLabTaskContact.findByContact(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(Contact c : Contact.findByPatientAndPushed(item.patient)){
                        c.delete();
                    }
                    item.isNew = 0;
                    item.pushed = 1;
                    item.save();
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
        try{
            for(Patient item : getAllPatients()){
                String res = run(AppUtil.getPushPatientUrl(context), item);
                String code = "";
                try{
                    JSONObject object = new JSONObject(res);
                    code = object.getString("statusCode");
                }catch (JSONException ex){
                    ex.printStackTrace();
                    result = Activity.RESULT_CANCELED;
                }
                if(code.equals("OK")){
                    for(PatientDisability c : PatientDisability.findByPatient(item)){
                        if(c != null)
                            c.delete();
                    }
                    item.delete();
                }
            }
            try{
                for(Mortality testing : Mortality.getAll()){
                    String testingOutcome = run(AppUtil.getPushMortalityUrl(context), testing);
                    String code = "";
                    JSONObject jsonObject = new JSONObject(testingOutcome);
                    code = jsonObject.getString("statusCode");
                    if(code.equals("OK")){
                        testing.delete();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                for(InvestigationTest testing : InvestigationTest.getAll()){
                    String testingOutcome = run(AppUtil.getPushInvestigationTestUrl(context), testing);
                    String code = "";
                    JSONObject jsonObject = new JSONObject(testingOutcome);
                    code = jsonObject.getString("statusCode");
                    if(code.equals("OK")){
                        testing.delete();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                for(TbIpt testing : getTbs()){
                    String testingOutcome = run(AppUtil.getPushTbIptUrl(context), testing);
                    String code = "";
                    JSONObject jsonObject = new JSONObject(testingOutcome);
                    code = jsonObject.getString("statusCode");
                    if(code.equals("OK")){
                        for(TbIptTbSymptomContract contract : TbIptTbSymptomContract.findByTbIpt(testing)) {
                            if(contract != null)
                                contract.delete();
                        }
                        testing.delete();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
            try{
                for(MentalHealthScreening testing : getMentalHealthScreenings()){
                    String testingOutcome = run(AppUtil.getPushMentalHealthScreeningUrl(context), testing);
                    String code = "";
                    JSONObject jsonObject = new JSONObject(testingOutcome);
                    code = jsonObject.getString("statusCode");
                    if(code.equals("OK")){
                        for(MentalHealthScreeningInterventionContract contract : MentalHealthScreeningInterventionContract.findByMentalHealthScreening(testing)) {
                            if(contract != null) {
                                contract.delete();
                            }
                        }
                        for(MentalHealthScreeningReferralContract contract : MentalHealthScreeningReferralContract.findByMentalHealthScreening(testing)) {
                            if(contract != null) {
                                contract.delete();
                            }
                        }
                        for(MentalHealthScreeningRiskContract contract : MentalHealthScreeningRiskContract.findByMentalHealthScreening(testing)) {
                            if(contract != null) {
                                contract.delete();
                            }
                        }
                        for(MentalHealthScreeningSupportContract contract : MentalHealthScreeningSupportContract.findByMentalHealthScreening(testing)) {
                            if(contract != null) {
                                contract.delete();
                            }
                        }
                        for(MentalHealthScreeningDiagnosisContract contract : MentalHealthScreeningDiagnosisContract.findByMentalHealthScreening(testing)) {
                            if(contract != null) {
                                contract.delete();
                            }
                        }
                        testing.delete();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
        try{
            for(Referral item : getAllReferrals()){
                String res = run(AppUtil.getPushReferralUrl(context), item);
                String code = "";
                try{
                    JSONObject object = new JSONObject(res);
                    code = object.getString("statusCode");
                }catch (JSONException ex){
                    ex.printStackTrace();
                    result = Activity.RESULT_CANCELED;
                }
                if(code.equals("OK")){
                    for(ReferralHivStiServicesReqContract c : ReferralHivStiServicesReqContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralHivStiServicesAvailedContract c : ReferralHivStiServicesAvailedContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralLaboratoryAvailedContract c : ReferralLaboratoryAvailedContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralLaboratoryReqContract c : ReferralLaboratoryReqContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralLegalAvailedContract c : ReferralLegalAvailedContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralLegalReqContract c : ReferralLegalReqContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralOIArtAvailedContract c : ReferralOIArtAvailedContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralOIArtReqContract c : ReferralOIArtReqContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralPsychAvailedContract c : ReferralPsychAvailedContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralPsychReqContract c : ReferralPsychReqContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralSrhAvailedContract c : ReferralSrhAvailedContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralSrhReqContract c : ReferralSrhReqContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralTbAvailedContract c : ReferralTbAvailedContract.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(ReferralTbReqContact c : ReferralTbReqContact.findByReferral(item)){
                        if(c != null)
                            c.delete();
                    }
                    for(Referral r : Referral.findByPatientAndPushed(item.patient)){
                        r.delete();
                    }
                    item.pushed = 1;
                    item.isNew = 0;
                    item.save();
                }
            }
            for(Patient patient : Patient.getAll()) {
                List<PatientDisability> disabilities = PatientDisability.findByPatient(patient);
                if( ! disabilities.isEmpty()) {
                    List<PatientDisability> patientDisabilities = new ArrayList<>();
                    for(PatientDisability disability : disabilities) {
                        disability.id = UUIDGen.generateUUID();
                        patientDisabilities.add(disability);
                    }
                    patient.disabilityCategorys = patientDisabilities;
                    String res = run(AppUtil.getPushDisabilityUrl(context), patient);
                    String code = "";
                    try{
                        JSONObject object = new JSONObject(res);
                        code = object.getString("statusCode");
                    }catch (JSONException ex){
                        ex.printStackTrace();
                        result = Activity.RESULT_CANCELED;
                    }
                    if(code.equals("OK")){
                        for(PatientDisability c : PatientDisability.findByPatient(patient)){
                            if(c != null)
                                c.delete();
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
        Patient.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        publishResults(result);
    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    private String run(HttpUrl httpUrl, Contact form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, Person form){
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        form.dob = DateUtil.getStringFromDate(form.dateOfBirth);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, HivSelfTesting form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, Mortality form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, TbIpt form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, InvestigationTest form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, MentalHealthScreening form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        form.patientId = form.patient.id;
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, Patient form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String run(HttpUrl httpUrl, Referral form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    public List<Contact> getAllContacts(){
        final List<Contact> contacts = new ArrayList<>();
        for(Contact c : Contact.getAll()){
            c.clinicalAssessments = Assessment.findClinicalByContact(c);
            c.nonClinicalAssessments = Assessment.findNonClinicalByContact(c);
            c.stables = Stable.findByContact(c);
            c.enhanceds = Enhanced.findByContact(c);
            c.labTasks = LabTask.findByContact(c);
            c.id = "";
            if(c.referredPerson != null) {
                c.referredPersonId = c.referredPerson.id;
            }
            contacts.add(c);
        }
        return contacts;
    }

    private List<MentalHealthScreening> getMentalHealthScreenings() {
        final List<MentalHealthScreening> list = new ArrayList<>();
        for(MentalHealthScreening screening : MentalHealthScreening.getAll()) {
            List<IdentifiedRisk> identifiedRisks = new ArrayList<>();
            for(MentalHealthScreeningRiskContract contract : MentalHealthScreeningRiskContract.findByMentalHealthScreening(screening)) {
                identifiedRisks.add(contract.identifiedRisk);
            }
            screening.identifiedRisks = identifiedRisks;
            List<Support> supports = new ArrayList<>();
            for(MentalHealthScreeningSupportContract contract : MentalHealthScreeningSupportContract.findByMentalHealthScreening(screening)) {
                supports.add(contract.support);
            }
            screening.supports = supports;
            List<zw.org.zvandiri.business.domain.util.Referral> referrals = new ArrayList<>();
            for(MentalHealthScreeningReferralContract contract : MentalHealthScreeningReferralContract.findByMentalHealthScreening(screening)) {
                referrals.add(contract.referral);
            }
            screening.referrals = referrals;
            List<Diagnosis> diagnoses = new ArrayList<>();
            for(MentalHealthScreeningDiagnosisContract contract : MentalHealthScreeningDiagnosisContract.findByMentalHealthScreening(screening)) {
                diagnoses.add(contract.diagnosis);
            }
            screening.diagnoses = diagnoses;
            List<Intervention> interventions = new ArrayList<>();
            for(MentalHealthScreeningInterventionContract contract : MentalHealthScreeningInterventionContract.findByMentalHealthScreening(screening)) {
                interventions.add(contract.intervention);
            }
            screening.interventions = interventions;
            list.add(screening);
        }
        return list;
    }

    public List<Patient> getAllPatients(){
        final List<Patient> patients = new ArrayList<>();
        for(Patient c : Patient.findByPushed()){
            c.disabilityCategorys = PatientDisability.findByPatient(c);
            c.id = "";
            patients.add(c);
        }
        return patients;
    }

    public List<Referral> getAllReferrals(){
        final List<Referral> referrals = new ArrayList<>();
        for(Referral c : Referral.getAll()){
            c.hivStiServicesAvailed = ServicesReferred.HivStiAvailed(c);
            c.hivStiServicesReq = ServicesReferred.HivStiReq(c);
            c.laboratoryAvailed = ServicesReferred.LabAvailed(c);
            c.laboratoryReq = ServicesReferred.LabReq(c);
            c.legalAvailed = ServicesReferred.LegalAvailed(c);
            c.legalReq = ServicesReferred.LegalReq(c);
            c.oiArtAvailed = ServicesReferred.OIArtAvailed(c);
            c.oiArtReq = ServicesReferred.OIArtReq(c);
            c.psychAvailed = ServicesReferred.PsychAvailed(c);
            c.psychReq = ServicesReferred.PsychReq(c);
            c.srhAvailed = ServicesReferred.SrhAvailed(c);
            c.srhReq = ServicesReferred.SrhReq(c);
            c.tbAvailed = ServicesReferred.TbAvailed(c);
            c.tbReq = ServicesReferred.TbReq(c);
            c.id = "";
            referrals.add(c);
        }
        return referrals;
    }

    private List<TbIpt> getTbs() {
        final List<TbIpt> list = new ArrayList<>();
        for(TbIpt item : TbIpt.getAll()) {
            List<TbSymptom> tbSymptoms = new ArrayList<>();
            for(TbIptTbSymptomContract contract : TbIptTbSymptomContract.findByTbIpt(item)){
                tbSymptoms.add(contract.tbSymptom);
            }
            item.tbSymptoms = tbSymptoms;
            list.add(item);
        }
        return list;
    }


}
