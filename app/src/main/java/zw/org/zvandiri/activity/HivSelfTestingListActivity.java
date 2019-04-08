package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.HivSelfTestingAdapter;
import zw.org.zvandiri.business.domain.HivSelfTesting;
import zw.org.zvandiri.business.domain.Person;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

/**
 * @uthor Tasu Muzinda
 */
public class HivSelfTestingListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    HivSelfTestingAdapter adapter;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list_view);
        Intent intent = getIntent();
        id = intent.getLongExtra(AppUtil.ID, 0L);
        Person person = Person.getItem(id);
        final ArrayList<HivSelfTesting> list = (ArrayList<HivSelfTesting>) HivSelfTesting.findByPerson(person);
        adapter = (new HivSelfTestingAdapter(this, list));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);
        adapter.onDataSetChanged((ArrayList<HivSelfTesting>) HivSelfTesting.findByPerson(Person.getItem(id)));
        setSupportActionBar(createToolBar("HIV Self Testing History For " + person.nameOfClient));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HivSelfTestingActivity.class);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HivSelfTesting item = (HivSelfTesting) parent.getAdapter().getItem(position);
        Intent intent = new Intent(HivSelfTestingListActivity.this, HivSelfTestingActivity.class);
        intent.putExtra("itemId", item.getId());
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, PersonDashboardActivity.class);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}
