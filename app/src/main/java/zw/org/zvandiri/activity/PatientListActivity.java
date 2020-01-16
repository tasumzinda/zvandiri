package zw.org.zvandiri.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Trigger;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.PatientAdapter;
import zw.org.zvandiri.business.domain.DisabilityCategory;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.remote.PushPullService;
import zw.org.zvandiri.remote.RemoteJobService;
import zw.org.zvandiri.remote.SetUpDataDownloadService;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PatientListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    PatientAdapter patientAdapter;
    ProgressDialog progressDialog;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        WVersionManager versionManager = new WVersionManager(this);
        versionManager.setVersionContentUrl("http://db.zvandiri.org/version.txt"); // your update content url, see the response format below
        versionManager.setUpdateUrl("http://db.zvandiri.org/zvandiri.apk");
        versionManager.setIgnoreThisVersionLabel(" ");
        versionManager.setRemindMeLaterLabel("");
        versionManager.checkVersion();
        final ArrayList<Patient> list = (ArrayList<Patient>) Patient.getAll();
        patientAdapter = (new PatientAdapter(this, list));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(patientAdapter);
        patientAdapter.onDataSetChanged((ArrayList<Patient>) Patient.getAll());
        search = (EditText) findViewById(R.id.search);
        setSupportActionBar(createToolBar("Patients"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        if (AppUtil.isNetworkAvailable(getApplicationContext())) {
                            Intent intent = new Intent(getApplicationContext(), PushPullService.class);
                            startService(intent);
                        }

                    }
                }, 1, 2, TimeUnit.HOURS);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientRegStep1Activity.class);
                startActivity(intent);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = charSequence.toString();
                List<Patient> patients = Patient.search(name);
                patientAdapter.clear();
                patientAdapter.addAll(patients);
                patientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Patient patient = (Patient) parent.getAdapter().getItem(position);
        Intent intent;
        if (patient.pushed == 1 && patient.hei != null && patient.hei.equals(YesNo.YES) && patient.motherOfHei == null) {
            intent = new Intent(PatientListActivity.this, HeuMotherDetailsActivity.class);
            intent.putExtra(AppUtil.ID, patient.getId());
            String name = patient.name != null ? patient.name : patient.firstName + " " + patient.lastName;
            intent.putExtra(AppUtil.NAME, name);
            startActivity(intent);
            finish();
        } else {
            if (patient.pushed == 1) {
                AppUtil.createShortNotification(this, "Please upload client to server before performing any operation on the patient");
            } else if(!patient.active || patient.status.equals(PatientChangeEvent.DECEASED)) {
                AppUtil.createShortNotification(this, "This client is no longer active in the database!");
            }else {
                intent = new Intent(PatientListActivity.this, SelectionActivity.class);
                intent.putExtra(AppUtil.ID, patient.id);
                String name = patient.name != null ? patient.name : patient.firstName + " " + patient.lastName;
                intent.putExtra(AppUtil.NAME, name);
                startActivity(intent);
                finish();
            }

        }

    }

    public void onBackPressed(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateView() {
        patientAdapter.clear();
        patientAdapter.addAll(Patient.getAll());
        patientAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            updateView();
            if (bundle != null) {
                int resultCode = bundle.getInt(PushPullService.RESULT);
                if (resultCode == RESULT_OK) {
                    createNotificationDataSync("Sync Success", "Application Data Updated");
                    AppUtil.createShortNotification(context, "Application Data Updated");
                } else {
                    createNotificationDataSync("Sync Fail", "Incomplete Application Data");
                    AppUtil.createShortNotification(context, "Incomplete Application Data");
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(PushPullService.NOTIFICATION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void syncAppData() {
        if (AppUtil.isNetworkAvailable(getApplicationContext())) {
            progressDialog = ProgressDialog.show(this, "Please wait", "Syncing with Server...", true);
            progressDialog.setCancelable(true);
            Intent intent = new Intent(this, PushPullService.class);
            startService(intent);
        } else {
            AppUtil.createShortNotification(this, "No Internet, Check Connectivity!");
        }
    }


}
