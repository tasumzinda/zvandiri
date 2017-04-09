package zw.org.zvandiri.remote;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONObject;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.*;

/**
 * Created by jackie muzinda on 22/1/2017.
 */
public class PushService extends IntentService{

    public static final String NOTIFICATION = "zw.org.zvandiri";
    public static final String RESULT = "result";
    private int result = Activity.RESULT_CANCELED;
    private Context context = this;

    public PushService(){
        super("PushService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final List<Patient> patients = new ArrayList<>();
        final List<Contact> contacts = new ArrayList<>();
        for(Patient p : Patient.findByPushed()){
            for(Contact c : Contact.findByPatientAndPushed(p)){
                c.assessments = Assessment.findByContact(c);
                c.stables = Stable.findByContact(c);
                c.enhanceds = Enhanced.findByContact(c);
                //c.dateOfContact = c.contactDate.toString();
                if(c.isNew == true){
                    c.id = "";
                }
                contacts.add(c);
            }
            p.contacts = contacts;
            patients.add(p);
            Log.d("size", patients.size() + "");
            Log.d("test", "test");
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUtil.getBaseUrl(context) + "/patient/add-contact",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    Long res = Long.valueOf(response);
                    if(res == 1){
                        result = Activity.RESULT_OK;
                    }else {
                        result = Activity.RESULT_CANCELED;
                    }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result = Activity.RESULT_CANCELED;
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                for(Contact m : contacts){
                    MyData.put("contact", AppUtil.createGson().toJson(m).trim()); //Add the data you'd like to send to the server.
                }

                return MyData;
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", AppUtil.getUsername(context), AppUtil.getPassword(context)).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
        publishResults(result);

    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }
}
