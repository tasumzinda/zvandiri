package zw.org.zvandiri.activity;

import android.app.ProgressDialog;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.remote.LoginWebService;
import zw.org.zvandiri.remote.SetUpDataDownloadService;

import java.util.*;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText userNameField;
    private EditText passwordField;
    private EditText urlField;
    private Button button;
    private EditText[] fields;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        userNameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);
        urlField = (EditText) findViewById(R.id.url);
        urlField.setText(AppUtil.getBaseUrl(this));
        urlField.setEnabled(false);
        button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        fields = new EditText[] {userNameField, passwordField, urlField};
        setSupportActionBar(createToolBar("Zvandiri Mobile App: Login"));
    }
    private void loginRemote(){
        String URL = String.format(urlField.getText().toString()+ "/patient/get-cats?email=", userNameField.getText().toString()) ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        boolean hasLoggedIn = sharedPreferences.contains(AppUtil.LOGGED_IN);
                        if(! hasLoggedIn){
                            String username = AppUtil.getUsername(context);
                            String password = AppUtil.getPassword(context);
                            if(username.equals(userNameField.getText().toString()) && password.equals(passwordField.getText().toString())){
                                AppUtil.savePreferences(getApplicationContext(), AppUtil.LOGGED_IN, Boolean.TRUE);
                                AppUtil.savePreferences(getApplicationContext(), AppUtil.USERNAME, userNameField.getText().toString());
                                AppUtil.savePreferences(getApplicationContext(), AppUtil.PASSWORD, passwordField.getText().toString());
                                syncAppData();
                            }else{
                                delete();
                                AppUtil.savePreferences(getApplicationContext(), AppUtil.LOGGED_IN, Boolean.TRUE);
                                AppUtil.savePreferences(getApplicationContext(), AppUtil.USERNAME, userNameField.getText().toString());
                                AppUtil.savePreferences(getApplicationContext(), AppUtil.PASSWORD, passwordField.getText().toString());
                                syncAppData();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        AppUtil.createShortNotification(getApplicationContext(), "Incorrect username or password");
                    }
                }
        ){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", userNameField.getText().toString(), passwordField.getText().toString()).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }
        };
        AppUtil.getInstance(getApplicationContext()).getRequestQueue().add(stringRequest);
    }



    public void onClick(View view){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(view.getId() == button.getId()){
            if(validate(fields)){
                if(AppUtil.isNetworkAvailable(getApplicationContext())){
                    //loginAsyncTask();
                    loginRemote();
                }else if(sharedPreferences.contains("USERNAME")){
                    if(AppUtil.getUsername(this).equals(userNameField.getText().toString()) && AppUtil.getPassword(this).equals(passwordField.getText().toString())){
                        AppUtil.savePreferences(getApplicationContext(), AppUtil.LOGGED_IN, Boolean.TRUE);
                        Intent intent = new Intent(context, PatientListActivity.class);
                        startActivity(intent);
                    }else{
                        AppUtil.createShortNotification(getApplicationContext(), "Wrong username or password");
                    }

                }else{
                    AppUtil.createShortNotification(getApplicationContext(), "No internet connection");
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void delete(){
        for(ContactEnhancedContract c : ContactEnhancedContract.getAll()){
            c.delete();
            Log.d("Deletec enhanced", c.enhanced.name);
        }
        for(ContactStableContract c: ContactStableContract.getAll()){
            c.delete();
            Log.d("Deleted stable", c.stable.name);
        }
        for(Contact c : Contact.getAll()){
            c.delete();
            Log.d("Deleted contact", c.contactDate + "");
        }
        for(PatientDisabilityCategoryContract p : PatientDisabilityCategoryContract.getAll()){
            p.delete();
            Log.d("Deleted disability", p.disabilityCategory.name);
        }
        for(ReferralHivStiServicesReqContract c : ReferralHivStiServicesReqContract.getAll()){
            c.delete();
        }
        for(ReferralHivStiServicesAvailedContract c : ReferralHivStiServicesAvailedContract.getAll()){
            c.delete();
        }
        for(ReferralLaboratoryAvailedContract c : ReferralLaboratoryAvailedContract.getAll()){
            c.delete();
        }
        for(ReferralLaboratoryReqContract c : ReferralLaboratoryReqContract.getAll()){
            c.delete();
        }
        for(ReferralLegalAvailedContract c : ReferralLegalAvailedContract.getAll()){
            c.delete();
        }
        for(ReferralLegalReqContract c : ReferralLegalReqContract.getAll()){
            c.delete();
        }
        for(ReferralOIArtAvailedContract c : ReferralOIArtAvailedContract.getAll()){
            if(c != null)
                c.delete();
        }
        for(ReferralOIArtReqContract c : ReferralOIArtReqContract.getAll()){
            c.delete();
        }
        for(ReferralPsychAvailedContract c : ReferralPsychAvailedContract.getAll()){
            c.delete();
        }
        for(ReferralPsychReqContract c : ReferralPsychReqContract.getAll()){
            c.delete();
        }
        for(ReferralSrhAvailedContract c : ReferralSrhAvailedContract.getAll()){
            c.delete();
        }
        for(ReferralSrhReqContract c : ReferralSrhReqContract.getAll()){
            if(c != null)
                c.delete();
        }
        for(ReferralTbAvailedContract c : ReferralTbAvailedContract.getAll()){
            c.delete();
        }
        for(ReferralTbReqContact c : ReferralTbReqContact.getAll()){
            c.delete();
        }
        for(Referral r : Referral.getAll()){
            r.delete();
            Log.d("Deleted referral", r.id);
        }
        for(Patient p : Patient.getAll()){
            p.delete();
            Log.d("Deleted patient", p.name);
        }
    }

    @Override
    public void onBackPressed(){
        onExit();
    }

    public void syncAppData(){
        progressDialog.setMessage("Getting data from server");
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        Intent intent = new Intent(this, SetUpDataDownloadService.class);
        startService(intent);
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

                  @Override
                  public void run() {
                      boolean flag = Patient.isFinished;
                      if(flag == false){
                          Intent intent = new Intent(context, PatientListActivity.class);
                          startActivity(intent);
                          finish();
                          progressDialog.dismiss();
                          t.cancel();
                      }
                  }

              },
                0,
                20000);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(SetUpDataDownloadService.NOTIFICATION));
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void onStop(){
        super.onStop();
    }



}

