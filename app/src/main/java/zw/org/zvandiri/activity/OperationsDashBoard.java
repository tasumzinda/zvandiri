package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.util.AppUtil;

public class OperationsDashBoard extends BaseActivity implements View.OnClickListener {

    private String id;
    private String name;
    private Button referrals;
    private Button changePatientStatus;
    private Button transferSupportGroup;
    private Button transferClinic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations_dash_board);
        referrals = (Button) findViewById(R.id.add_referrals);
        changePatientStatus = (Button) findViewById(R.id.change_patient_status);
        transferClinic = (Button) findViewById(R.id.transfer_clinic);
        transferSupportGroup = (Button) findViewById(R.id.transfer_support_group);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        referrals.setOnClickListener(this);
        changePatientStatus.setOnClickListener(this);
        transferSupportGroup.setOnClickListener(this);
        transferClinic.setOnClickListener(this);
        setSupportActionBar(createToolBar(name + "s Dashboard"));
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
        Intent intent = new Intent(OperationsDashBoard.this, PatientActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId() == referrals.getId()){
            intent = new Intent(OperationsDashBoard.this, PatientReferralListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        /*if(view.getId() == changePatientStatus.getId()){
            intent = new Intent(OperationsDashBoard.this, ChangePatientStatusListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == transferSupportGroup.getId()){
            intent = new Intent(OperationsDashBoard.this, TransferPatientSupportGroupListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
        if(view.getId() == transferClinic.getId()){
            intent = new Intent(OperationsDashBoard.this, TransferPatientFacilityListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }*/
    }
}
