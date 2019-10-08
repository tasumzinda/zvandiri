package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

/**
 * @uthor Tasu Muzinda
 */
public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    CardView person;
    CardView patient;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setSupportActionBar(createToolBar("Zvandiri:: Home"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        person = (CardView) findViewById(R.id.card_person);
        patient = (CardView) findViewById(R.id.card_patient);
        person.setOnClickListener(this);
        patient.setOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getLongExtra(AppUtil.ID, 0L);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId() == person.getId()){
            intent = new Intent(this, PersonListActivity.class);
        }
        if(v.getId() == patient.getId()){
            intent = new Intent(this, PatientListActivity.class);
        }
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        onExit();
    }
}
