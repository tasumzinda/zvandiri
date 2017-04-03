package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.util.AppUtil;

public class PatientActivity extends BaseActivity implements View.OnClickListener{

    String id;
    String name;
    Button patientHistory;
    Button labResults;
    Button primaryCareGiver;
    Button dependant;
    Button operations;
    Button contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name= intent.getStringExtra(AppUtil.NAME);
        primaryCareGiver = (Button) findViewById(R.id.primaryCareGiver);
        primaryCareGiver.setOnClickListener(this);
        patientHistory = (Button) findViewById(R.id.patient_history);
        patientHistory.setOnClickListener(this);
        labResults = (Button) findViewById(R.id.lab_results);
        labResults.setOnClickListener(this);
        dependant = (Button) findViewById(R.id.add_dependant);
        dependant.setOnClickListener(this);
        operations = (Button) findViewById(R.id.operations);
        operations.setOnClickListener(this);
        contact = (Button) findViewById(R.id.contact);
        contact.setOnClickListener(this);
        setSupportActionBar(createToolBar(name.toUpperCase() + "'S " + "DASHBOARD"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId() == patientHistory.getId()){
            intent = new Intent(PatientActivity.this, PatientHistoryDashboard.class);
            intent.putExtra(AppUtil.ID, id);
            intent.putExtra(AppUtil.NAME, name);
            startActivity(intent);
            finish();
        }

        if(view.getId() == labResults.getId()){
            intent = new Intent(PatientActivity.this, LabResultsDashBoard.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }

        /*if(view.getId() == primaryCareGiver.getId()){
            intent = new Intent(PatientActivity.this, PrimaryCareGiverListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }*/

        if(view.getId() == dependant.getId()){
            intent = new Intent(PatientActivity.this, DependantListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == operations.getId()){
            intent = new Intent(PatientActivity.this, OperationsDashBoard.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == contact.getId()){
            intent = new Intent(PatientActivity.this, PatientContactListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(PatientActivity.this, PatientListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_exit:
                onExit();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }
}
