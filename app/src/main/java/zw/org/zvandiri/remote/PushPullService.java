package zw.org.zvandiri.remote;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import org.json.JSONArray;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;

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

        /*for (HttpUrl httpUrl : getHttpUrls()) {
            try {
                if (httpUrl.equals(AppUtil.getInternalRefferalUrl(context))) {
                    loadInternalRefferals(AppUtil.run(httpUrl, context));
                }

                if (httpUrl.equals(AppUtil.getExternalRefferalUrl(context))) {
                    loadExternalRefferals(AppUtil.run(httpUrl, context));
                }

                if (httpUrl.equals(AppUtil.getAssessmentUrl(context))) {
                    loadAssessment(AppUtil.run(httpUrl, context));
                }
                if (httpUrl.equals(AppUtil.getIntensiveUrl(context))) {
                    loadIntensive(AppUtil.run(httpUrl, context));
                }
                if (httpUrl.equals(AppUtil.getLocationUrl(context))) {
                    loadLocation(AppUtil.run(httpUrl, context));
                }
                if (httpUrl.equals(AppUtil.getPositionUrl(context))) {
                    loadPosition(AppUtil.run(httpUrl, context));
                }

                if (httpUrl.equals(AppUtil.getEnhancedUrl(context))) {
                    loadEnhanced(AppUtil.run(httpUrl, context));
                }
                if (httpUrl.equals(AppUtil.getStableUrl(context))) {
                    loadStable(AppUtil.run(httpUrl, context));
                }
                if (httpUrl.equals(AppUtil.getPatientUrl(context))) {
                    loadPatient(AppUtil.run(httpUrl, context));
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = Activity.RESULT_CANCELED;
            }

        }*/
        try{
            for(Contact item : getAllContacts()){
                //int res = Integer.parseInt(run(AppUtil.getPushContactUrl(context), item));
                /*if(res == 1){
                    for(ContactAssessmentContract c : ContactAssessmentContract.findByContact(Contact.findById(item.id))){
                        c.delete();
                    }
                    for(ContactActionTakenContract c : ContactActionTakenContract.findByContact(Contact.findById(item.id))){
                        c.delete();
                    }
                    for(ContactIntensiveContract c : ContactIntensiveContract.findByContact(Contact.findById(item.id))){
                        if(c != null)
                            c.delete();
                        Log.d("Deleted intensives", c.intensive.name);
                    }
                    for(ContactStableContract c : ContactStableContract.findByContact(Contact.findById(item.id))){
                        if(c != null)
                            c.delete();
                        Log.d("Deleted stables", c.stable.name);
                    }
                    for(ContactEnhancedContract c: ContactEnhancedContract.findByContact(Contact.findById(item.id))){
                        if(c != null)
                            c.delete();
                        Log.d("Deleted enhanceds", c.enhanced.name);
                    }
                    item.delete();
                }*/
                Log.d("Result", "Result " + run(AppUtil.getPushContactUrl(context), item));
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = Activity.RESULT_CANCELED;
        }
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
        form.dateOfContact = AppUtil.getStringDate(form.contactDate);
        String json = AppUtil.createGson().toJson(form);
        return AppUtil.getResponeBody(client, httpUrl, json);
    }

    public Contact save(String data, Contact item) {
        try {
            /*Long id = Long.valueOf(data);
            if( id == 1)
                item.delete();*/
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String loadInternalRefferals(String data) {
        int i = 0;
        String msg = "InternalReferral Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                InternalReferral checkDuplicate = InternalReferral.getItem(staticData.id);
                if (checkDuplicate == null) {
                    InternalReferral item = new InternalReferral();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "InternalReferral Sync Failed";
        }
        return msg;
    }

    private String loadExternalRefferals(String data) {
        int i = 0;
        String msg = "ExternalRefferal Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                ExternalReferral checkDuplicate = ExternalReferral.getItem(staticData.id);
                if (checkDuplicate == null) {
                    ExternalReferral item = new ExternalReferral();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "ExternalReferral Sync Failed";
        }
        return msg;
    }

    private String loadAssessment(String data) {
        int i = 0;
        String msg = "Assessment Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Assessment checkDuplicate = Assessment.getItem(staticData.id);
                if (checkDuplicate == null) {
                    Assessment item = new Assessment();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "Assessment Sync Failed";
        }
        return msg;
    }

    private String loadIntensive(String data) {
        int i = 0;
        String msg = "Intensive Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Intensive checkDuplicate = Intensive.getItem(staticData.id);
                if (checkDuplicate == null) {
                    Intensive item = new Intensive();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "Intensive Sync Failed";
        }
        return msg;
    }

    private String loadLocation(String data) {
        int i = 0;
        String msg = "Location Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Location checkDuplicate = Location.getItem(staticData.id);
                if (checkDuplicate == null) {
                    Location item = new Location();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "Location Sync Failed";
        }
        return msg;
    }

    private String loadPosition(String data) {
        int i = 0;
        String msg = "Position Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Position checkDuplicate = Position.getItem(staticData.id);
                if (checkDuplicate == null) {
                    Position item = new Position();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "Position Sync Failed";
        }
        return msg;
    }

    private String loadEnhanced(String data) {
        int i = 0;
        String msg = "Enhanced Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Enhanced checkDuplicate = Enhanced.getItem(staticData.id);
                if (checkDuplicate == null) {
                    Enhanced item = new Enhanced();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "Enhanced Sync Failed";
        }
        return msg;
    }

    private String loadStable(String data) {
        int i = 0;
        String msg = "Stable Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Stable checkDuplicate = Stable.getItem(staticData.id);
                if (checkDuplicate == null) {
                    Stable item = new Stable();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "Stable Sync Failed";
        }
        return msg;
    }

    private String loadPatient(String data) {
        int i = 0;
        String msg = "Patient Synced";
        try {
            JSONArray jsonArray = new JSONArray(data);
            List<StaticData> list = StaticData.fromJson(jsonArray);
            for (StaticData staticData : list) {
                Patient checkDuplicate = Patient.getById(staticData.id);
                if (checkDuplicate == null) {
                    Patient item = new Patient();
                    item.id = staticData.id;
                    item.name = staticData.name;
                    item.save();
                } else {
                    checkDuplicate.name = staticData.name;
                    checkDuplicate.save();
                }
                i++;
            }
            msg = msg.concat(" - " + i);

        } catch (Exception e) {
            msg = "Patient Sync Failed";
        }
        return msg;
    }

    public List<HttpUrl> getHttpUrls() {
        List<HttpUrl> static_lists = new ArrayList<>();
        static_lists.add(AppUtil.getInternalRefferalUrl(context));
        static_lists.add(AppUtil.getExternalRefferalUrl(context));
        static_lists.add(AppUtil.getAssessmentUrl(context));
        static_lists.add(AppUtil.getIntensiveUrl(context));
        static_lists.add(AppUtil.getLocationUrl(context));
        static_lists.add(AppUtil.getPositionUrl(context));
        static_lists.add(AppUtil.getEnhancedUrl(context));
        static_lists.add(AppUtil.getStableUrl(context));
        static_lists.add(AppUtil.getPatientUrl(context));
        return static_lists;
    }

    public List<Contact> getAllContacts(){
        final List<Contact> contacts = new ArrayList<>();
        for(Contact c : Contact.getAll()){
            c.assessments = Assessment.findByContact(c);
            c.stables = Stable.findByContact(c);
            c.enhanceds = Enhanced.findByContact(c);
            c.dateOfContact = AppUtil.getStringDate(c.contactDate);
            contacts.add(c);
        }
        return contacts;
    }


}
