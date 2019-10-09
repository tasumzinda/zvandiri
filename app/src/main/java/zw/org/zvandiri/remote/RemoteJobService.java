package zw.org.zvandiri.remote;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasu on 6/19/17.
 */
public class RemoteJobService extends JobService {

    Context context = this;

    @Override
    public boolean onStartJob(JobParameters parameters){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(Contact item : getAllContacts()){
                        int res = Integer.parseInt(push(AppUtil.getPushContactUrl(context), item));
                        if(res == 1){
                            for(ContactStableContract c : ContactStableContract.findByContact(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted stables", c.stable.name);
                            }
                            for(ContactEnhancedContract c: ContactEnhancedContract.findByContact(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted enhanceds", c.enhanced.name);
                            }
                            item.delete();
                            Log.d("Deleted", " " + item.contactDate);
                        }

                        Log.d("Result", "Result " + res);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                try{
                    for(Patient item : getAllPatients()){
                        Log.d("Size", getAllPatients() + " ");
                        int res = Integer.parseInt(push(AppUtil.getPushPatientUrl(context), item));
                        if(res == 1){
                            for(PatientDisabilityCategoryContract c : PatientDisabilityCategoryContract.findByPatient(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted disability", c.disabilityCategory.name);
                            }
                            item.delete();
                            Log.d("Deleted", " " + item.firstName);
                        }

                        Log.d("Result", "Result " + res);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                try{
                    for(Referral item : getAllReferrals()){
                        int res = Integer.parseInt(push(AppUtil.getPushReferralUrl(context), item));
                        if(res == 1){
                            for(ReferralHivStiServicesReqContract c : ReferralHivStiServicesReqContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted hivServicesReq", c.hivStiServicesReq.name);
                            }
                            for(ReferralHivStiServicesAvailedContract c : ReferralHivStiServicesAvailedContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted hivAvailed", c.hivStiServicesAvailed.name);
                            }
                            for(ReferralLaboratoryAvailedContract c : ReferralLaboratoryAvailedContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted labAvailed", c.laboratoryAvailed.name);
                            }
                            for(ReferralLaboratoryReqContract c : ReferralLaboratoryReqContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted laboratoryReq", c.laboratoryReq.name);
                            }
                            for(ReferralLegalAvailedContract c : ReferralLegalAvailedContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted legalAvailed", c.legalAvailed.name);
                            }
                            for(ReferralLegalReqContract c : ReferralLegalReqContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted legalReq", c.legalReq.name);
                            }
                            for(ReferralOIArtAvailedContract c : ReferralOIArtAvailedContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted oiArtAvailed", c.oiArtAvailed.name);
                            }
                            for(ReferralOIArtReqContract c : ReferralOIArtReqContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted oiArtReq", c.oiArtReq.name);
                            }
                            for(ReferralPsychAvailedContract c : ReferralPsychAvailedContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted psychAvailed", c.psychAvailed.name);
                            }
                            for(ReferralPsychReqContract c : ReferralPsychReqContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted psychReq", c.psychReq.name);
                            }
                            for(ReferralSrhAvailedContract c : ReferralSrhAvailedContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted srhAvailed", c.srhAvailed.name);
                            }
                            for(ReferralSrhReqContract c : ReferralSrhReqContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted srhReq", c.srhReq.name);
                            }
                            for(ReferralTbAvailedContract c : ReferralTbAvailedContract.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted tbAvailed", c.tbAvailed.name);
                            }
                            for(ReferralTbReqContact c : ReferralTbReqContact.findByReferral(item)){
                                if(c != null)
                                    c.delete();
                                Log.d("Deleted tbReq", c.tbReq.name);
                            }
                            item.delete();
                            Log.d("Deleted", " " + item.referralDate);
                        }

                        Log.d("Result", "Result " + res);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                fetchStatic();
            }
        }).start();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters parameters){
        return false;
    }

    private String push(HttpUrl httpUrl, Contact form) {
        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String push(HttpUrl httpUrl, Patient form) {

        OkHttpClient client = new OkHttpClient();
        client = AppUtil.connectionSettings(client);
        client = AppUtil.getUnsafeOkHttpClient(client);
        client = AppUtil.createAuthenticationData(client, context);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    private String push(HttpUrl httpUrl, Referral form) {

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
            c.id = "";
            contacts.add(c);
        }
        return contacts;
    }

    public List<Patient> getAllPatients(){
        final List<Patient> patients = new ArrayList<>();
        for(Patient c : Patient.findByPushed()){
            c.disabilityCategorys = DisabilityCategory.findByPatient(c);
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

    public  void fetchStatic(){
        InternalReferral.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        ExternalReferral.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Assessment.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Location.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Position.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Enhanced.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Stable.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        ActionTaken.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Province.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Relationship.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Referer.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        OrphanStatus.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Education.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        EducationLevel.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        ChronicInfection.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        ServicesReferred.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        HivCoInfection.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        MentalHealth.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        DisabilityCategory.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Substance.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        ArvMedicine.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        HospCause.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        ReasonForNotReachingOLevel.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
    }
}
