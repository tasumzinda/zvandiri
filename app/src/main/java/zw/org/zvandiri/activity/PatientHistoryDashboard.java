package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.MentalHealth;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.List;

public class PatientHistoryDashboard extends BaseActivity implements View.OnClickListener {

    Button addArvHists;
    Button addSocialHists;
    Button addSrhHists;
    Button addChronicInfectionItems;
    Button addMentalHealthItems;
    Button addFamily;
    Button addMedicalHists;
    Button addSubstanceItems;
    private Button addObstetricHistory;
    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history_dashboard);
        addArvHists = (Button) findViewById(R.id.add_arv_hist);
        addSrhHists = (Button) findViewById(R.id.add_srh_hist);
        addSocialHists = (Button) findViewById(R.id.add_social_hist);
        addChronicInfectionItems = (Button) findViewById(R.id.add_chronic_infection_item);
        addMentalHealthItems = (Button) findViewById(R.id.add_mental_health_item);
        addFamily = (Button) findViewById(R.id.add_family);
        addMedicalHists = (Button) findViewById(R.id.add_medical_hist);
        addSubstanceItems = (Button) findViewById(R.id.add_substance_item);
        addObstetricHistory = (Button) findViewById(R.id.add_obstetric_hist);
        addSocialHists.setOnClickListener(this);
        addSrhHists.setOnClickListener(this);
        addArvHists.setOnClickListener(this);
        addChronicInfectionItems.setOnClickListener(this);
        addMentalHealthItems.setOnClickListener(this);
        addFamily.setOnClickListener(this);
        addMedicalHists.setOnClickListener(this);
        addSubstanceItems.setOnClickListener(this);
        addObstetricHistory.setOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        setSupportActionBar(createToolBar(name + "'s Dashboard"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId() == addArvHists.getId()){
            intent = new Intent(PatientHistoryDashboard.this, ArvHistListActivity.class);
            intent.putExtra(AppUtil.ID, id);
            intent.putExtra(AppUtil.NAME, name);
            startActivity(intent);
            finish();
        }
        if(view.getId() == addSocialHists.getId()){
            intent = new Intent(PatientHistoryDashboard.this, SocialHistListActivity.class);
            intent.putExtra(AppUtil.ID, id);
            intent.putExtra(AppUtil.NAME, name);
            startActivity(intent);
            finish();
        }
        if(view.getId() == addSrhHists.getId()){
            intent = new Intent(PatientHistoryDashboard.this, SrhHistListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == addChronicInfectionItems.getId()){
            intent = new Intent(PatientHistoryDashboard.this, ChronicInfectionItemListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == addMentalHealthItems.getId()){
            intent = new Intent(PatientHistoryDashboard.this, MentalHealthItemListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            List<MentalHealth> mentalHealths = MentalHealth.getAll();
            Log.d("Mental Health", AppUtil.createGson().toJson(mentalHealths));
            startActivity(intent);
            finish();
        }
        if(view.getId() == addFamily.getId()){
            intent = new Intent(PatientHistoryDashboard.this, FamilyListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == addMedicalHists.getId()){
            intent = new Intent(PatientHistoryDashboard.this, MedicalHistListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == addSubstanceItems.getId()){
            intent = new Intent(PatientHistoryDashboard.this, SubstanceItemListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == addObstetricHistory.getId()){
            intent = new Intent(PatientHistoryDashboard.this, ObstetricHistListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(PatientHistoryDashboard.this, PatientActivity.class);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.NAME, name);
        startActivity(intent);
        finish();
    }
}
