package zw.org.zvandiri.remote;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 12/19/2016.
 */
public class SetUpDataDownloadService extends IntentService{

    public static final String NOTIFICATION = "zw.org.zvandiri";
    public static final String RESULT = "result";
    private int result = Activity.RESULT_CANCELED;

    Context context = this;
    public SetUpDataDownloadService(){
        super("SetUpDataDownloadService");
    }

    @Override
    public void onHandleIntent(Intent intent){
        result = Activity.RESULT_OK;
        fetchStatic();
        publishResults(result);
    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }


    public  void fetchStatic(){
        InternalReferral.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        ExternalReferral.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Assessment.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        //Intensive.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Location.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Position.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Enhanced.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        Stable.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
        //Patient.fetchRemote(context, AppUtil.getUsername(context), AppUtil.getPassword(context));
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
