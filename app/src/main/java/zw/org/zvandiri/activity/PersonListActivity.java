package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.PatientAdapter;
import zw.org.zvandiri.adapter.PersonAdapter;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Person;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;

public class PersonListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    PersonAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list_view);
        Log.d("Persons", AppUtil.createGson().toJson(Person.getAll()));
        final ArrayList<Person> list = (ArrayList<Person>) Person.getAll();
        adapter = (new PersonAdapter(this, list));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);
        adapter.onDataSetChanged((ArrayList<Person>) Person.getAll());
        setSupportActionBar(createToolBar("Person List"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Person patient = (Person) parent.getAdapter().getItem(position);
        Intent intent;
        if(patient.pushed == 0){
            AppUtil.createShortNotification(this, "Please upload person to server before performing any operation on the person");
        }else{
            intent = new Intent(PersonListActivity.this, HivSelfTestingListActivity.class);
            intent.putExtra(AppUtil.ID, patient.getId());
            startActivity(intent);
            finish();
        }

    }

}
