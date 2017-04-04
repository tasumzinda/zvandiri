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
import com.android.volley.toolbox.StringRequest;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;
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
                            AppUtil.savePreferences(getApplicationContext(), AppUtil.LOGGED_IN, Boolean.TRUE);
                            AppUtil.savePreferences(getApplicationContext(), AppUtil.USERNAME, userNameField.getText().toString());
                            AppUtil.savePreferences(getApplicationContext(), AppUtil.PASSWORD, passwordField.getText().toString());
                            AppUtil.savePreferences(getApplicationContext(), AppUtil.BASE_URL, urlField.getText().toString());
                            syncAppData();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        AppUtil.createShortNotification(getApplicationContext(), "Incorrect username or password");
                        //Log.d("Error", volleyError.toString());
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

