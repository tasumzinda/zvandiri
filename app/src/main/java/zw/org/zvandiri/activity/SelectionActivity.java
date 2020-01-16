package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;

/**
 * Created by User on 4/7/2017.
 */
public class SelectionActivity extends BaseActivity implements View.OnClickListener{

    private CardView contact;
    private CardView referral;
    private CardView mentalHealth;
    private CardView mortality;
    private CardView tbIpt;
    private CardView disability;
    private String id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_activity);
        contact = (CardView) findViewById(R.id.card_contact);
        referral = (CardView) findViewById(R.id.card_referral);
        mentalHealth = (CardView) findViewById(R.id.card_mental_health);
        mortality = (CardView) findViewById(R.id.card_mortality);
        tbIpt = (CardView) findViewById(R.id.tb_ipt);
        disability = (CardView) findViewById(R.id.disability);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        Patient patient = Patient.getById(id);
        name = intent.getStringExtra(AppUtil.NAME);
        contact.setOnClickListener(this);
        referral.setOnClickListener(this);
        mentalHealth.setOnClickListener(this);
        mortality.setOnClickListener(this);
        tbIpt.setOnClickListener(this);
        disability.setOnClickListener(this);
        setSupportActionBar(createToolBar(patient + "'s Dashboard"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void onBackPressed(){
        Intent intent = new Intent(this, PatientListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == contact.getId()){
            Intent intent = new Intent(this, PatientContactListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == referral.getId()){
            Intent intent = new Intent(this, PatientReferralListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }

        if(view.getId() == mentalHealth.getId()){
            Intent intent = new Intent(this, MentalHealthScreeningListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == mortality.getId()){
            Intent intent = new Intent(this, MortalityListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == tbIpt.getId()){
            Intent intent = new Intent(this, TbIptListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == disability.getId()){
            Intent intent = new Intent(this, PatientDisabilityListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }
}
