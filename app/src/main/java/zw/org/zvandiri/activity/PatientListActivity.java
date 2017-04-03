package zw.org.zvandiri.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.PatientAdapter;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.remote.PushPullService;
import zw.org.zvandiri.remote.PushService;
import zw.org.zvandiri.remote.SetUpDataDownloadService;

import java.util.ArrayList;

public class PatientListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    PatientAdapter patientAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (savedInstanceState == null) {
            syncAppData();
        }*/
        setContentView(R.layout.generic_list_view);
        final ArrayList<Patient> list = (ArrayList<Patient>) Patient.getAll();
        patientAdapter = (new PatientAdapter(this, list));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(patientAdapter);
        patientAdapter.onDataSetChanged((ArrayList<Patient>) Patient.getAll());
        setSupportActionBar(createToolBar("Patients"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Patient patient = (Patient) parent.getAdapter().getItem(position);
        Intent intent = new Intent(PatientListActivity.this, PatientContactListActivity.class);
        intent.putExtra(AppUtil.ID, patient.id);
        intent.putExtra(AppUtil.NAME, patient.name);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        onExit();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_exit:
                onExit();
                return true;
            case R.id.action_refresh:
                //String p = new PushService().toJson();
                //zw.org.zvandiri.toolbox.Log.d("Patients", p);
                //syncAppData();
                Intent intent = new Intent(this, PushPullService.class);
                startService(intent);
                return true;
            case R.id.action_add:
                Intent intent1 = new Intent(this, PatientRegStep1Activity.class);
                startActivity(intent1);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(PushPullService.NOTIFICATION));
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }


}
