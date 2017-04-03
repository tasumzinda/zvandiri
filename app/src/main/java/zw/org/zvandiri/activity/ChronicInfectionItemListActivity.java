package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.ChronicInfectionItemAdapter;
import zw.org.zvandiri.business.domain.ChronicInfectionItem;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

public class ChronicInfectionItemListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    String name;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list_view);
        Intent intent = getIntent();
        name = intent.getStringExtra(AppUtil.NAME);
        id = intent.getStringExtra(AppUtil.ID);
        Patient patient = Patient.findById(id);
        ChronicInfectionItemAdapter chronicInfectionItemAdapter = (new ChronicInfectionItemAdapter(this, new ArrayList<>(ChronicInfectionItem.findByPatient(patient))));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(chronicInfectionItemAdapter);
        setSupportActionBar(createToolBar("Chronic Infection Items for " + name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
                Intent intent = new Intent(ChronicInfectionItemListActivity.this, ChronicInfectionItemActivity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_exit:
                onExit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(ChronicInfectionItemListActivity.this, PatientHistoryDashboard.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
        ChronicInfectionItem chronicInfectionItem = (ChronicInfectionItem) parent.getAdapter().getItem(position);
        Intent intent = new Intent(ChronicInfectionItemListActivity.this, ChronicInfectionItemActivity.class);
        intent.putExtra(AppUtil.DETAILS_ID, chronicInfectionItem.id);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}
