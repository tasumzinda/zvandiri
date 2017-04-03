package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;

public class LabResultsDashBoard extends BaseActivity implements View.OnClickListener {

    private Button viralLoadItem;
    private Button cd4CountItem;
    private String id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_results_dash_board);
        viralLoadItem = (Button) findViewById(R.id.viral_load_count);
        cd4CountItem = (Button) findViewById(R.id.cd4_count);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        viralLoadItem.setOnClickListener(this);
        cd4CountItem.setOnClickListener(this);
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


    public void onBackPressed(){
        Intent intent = new Intent(LabResultsDashBoard.this, PatientActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == viralLoadItem.getId()){
            Intent intent = new Intent(LabResultsDashBoard.this, ViralLoadListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == cd4CountItem.getId()){
            Intent intent = new Intent(LabResultsDashBoard.this, Cd4CountListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }
}
