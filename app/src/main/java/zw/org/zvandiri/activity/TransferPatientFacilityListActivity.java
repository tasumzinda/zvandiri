package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.TransferPatientFacilityAdapter;
import zw.org.zvandiri.adapter.TransferPatientToSupportGroupAdapter;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;
/*
public class TransferPatientFacilityListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    String name;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list_view);
        Intent intent = getIntent();
        name = intent.getStringExtra(AppUtil.NAME);
        id = intent.getStringExtra(AppUtil.ID);
        TransferPatientFacilityAdapter adapter = (new TransferPatientFacilityAdapter(this, new ArrayList<>(Patient.getById(id))));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);
        setSupportActionBar(createToolBar("Primary Clinic for " + name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_exit:
                onExit();
                return true;
            case R.id.action_add:
                Intent intent = new Intent(TransferPatientFacilityListActivity.this, TransferPatientFacilityActivity.class);
                intent.putExtra(AppUtil.ID, id);
                intent.putExtra(AppUtil.NAME, name);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }


    public void onBackPressed(){
        Intent intent = new Intent(TransferPatientFacilityListActivity.this, OperationsDashBoard.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
        Patient changePatientSupportGroup = (Patient) parent.getAdapter().getItem(position);
        Intent intent = new Intent(TransferPatientFacilityListActivity.this, TransferPatientFacilityActivity.class);
        intent.putExtra(AppUtil.DETAILS_ID, changePatientSupportGroup.id);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}*/
