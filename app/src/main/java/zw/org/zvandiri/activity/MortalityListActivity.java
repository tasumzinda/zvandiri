package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.MortalityAdapter;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

public class MortalityListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    MortalityAdapter adapter;
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
        final ArrayList<Mortality> list = (ArrayList<Mortality>) Mortality.findByPatient(patient);
        adapter = (new MortalityAdapter(this, list));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);
        adapter.onDataSetChanged((ArrayList<Mortality>) Mortality.findByPatient(patient));
        setSupportActionBar(createToolBar("Mortality Details For " + patient));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MortalityActivity.class);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Mortality item = (Mortality) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, MortalityActivity.class);
        intent.putExtra("itemId", item.getId());
        startActivity(intent);
        finish();
    }
}
