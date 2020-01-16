package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.PatientDisabiliyAdapter;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.PatientDisability;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

public class PatientDisabilityListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    PatientDisabiliyAdapter adapter;
    String name;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list_view);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        Patient patient = Patient.getById(id);
        final ArrayList<PatientDisability> list = (ArrayList<PatientDisability>) PatientDisability.findByPatient(patient);
        adapter = (new PatientDisabiliyAdapter(this, list));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);
        adapter.onDataSetChanged((ArrayList<PatientDisability>) PatientDisability.findByPatient(patient));
        setSupportActionBar(createToolBar("Disabilities For " + patient));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientDisabilityActivity.class);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PatientDisability item = (PatientDisability) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, PatientDisabilityActivity.class);
        intent.putExtra("itemId", item.getId());
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}
