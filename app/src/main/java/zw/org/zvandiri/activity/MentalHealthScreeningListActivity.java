package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.MentalHealthScreeningAdapter;
import zw.org.zvandiri.business.domain.MentalHealthScreening;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

/**
 * @uthor Tasu Muzinda
 */
public class MentalHealthScreeningListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    MentalHealthScreeningAdapter adapter;
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
        final ArrayList<MentalHealthScreening> list = (ArrayList<MentalHealthScreening>) MentalHealthScreening.findByPatient(patient);
        adapter = (new MentalHealthScreeningAdapter(this, list));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);
        adapter.onDataSetChanged((ArrayList<MentalHealthScreening>) MentalHealthScreening.findByPatient(patient));
        setSupportActionBar(createToolBar("Mental Health Screening History For " + patient));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MentalHealthScreeningActivity.class);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MentalHealthScreening item = (MentalHealthScreening) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, MentalHealthScreeningActivity.class);
        intent.putExtra("itemId", item.getId());
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(MentalHealthScreeningListActivity.this, SelectionActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}
