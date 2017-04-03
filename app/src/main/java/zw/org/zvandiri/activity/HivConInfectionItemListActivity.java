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
import zw.org.zvandiri.adapter.HivConInfectionItemAdapter;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.HivConInfectionItem;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

public class HivConInfectionItemListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
        HivConInfectionItemAdapter hivConInfectionItemAdapter = (new HivConInfectionItemAdapter(this, new ArrayList<>(HivConInfectionItem.findByPatient(patient))));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(hivConInfectionItemAdapter);
        setSupportActionBar(createToolBar("Hiv Con-Infection for " + name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(HivConInfectionItemListActivity.this, PatientActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
        HivConInfectionItem hivConInfectionItem = (HivConInfectionItem) parent.getAdapter().getItem(position);
        Intent intent = new Intent(HivConInfectionItemListActivity.this, HivConInfectionItemActivity.class);
        intent.putExtra(AppUtil.DETAILS_ID, hivConInfectionItem.id);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}
