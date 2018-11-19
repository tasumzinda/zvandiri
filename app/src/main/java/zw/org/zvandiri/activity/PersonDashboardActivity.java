package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

public class PersonDashboardActivity extends BaseActivity implements View.OnClickListener {

    CardView selfTesting;
    CardView tbScreening;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_dashboard);
        setSupportActionBar(createToolBar("Person Dashboard"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selfTesting = (CardView) findViewById(R.id.card_testing);
        tbScreening = (CardView) findViewById(R.id.card_screening);
        selfTesting.setOnClickListener(this);
        tbScreening.setOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getLongExtra(AppUtil.ID, 0L);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId() == selfTesting.getId()){
            intent = new Intent(this, HivSelfTestingListActivity.class);
        }
        if(v.getId() == tbScreening.getId()){
            intent = new Intent(this, TbScreeningListActivity.class);
        }
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, PersonListActivity.class);
        startActivity(intent);
        finish();
    }
}
