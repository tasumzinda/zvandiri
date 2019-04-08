package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.adapter.ContactAdapter;
import zw.org.zvandiri.adapter.SrhHistAdapter;
import zw.org.zvandiri.business.domain.Contact;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.SrhHist;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;

public class PatientContactListActivity extends BaseActivity /*implements AdapterView.OnItemClickListener*/ {

    String name;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_list_view);
        Intent intent = getIntent();
        name = intent.getStringExtra(AppUtil.NAME);
        id = intent.getStringExtra(AppUtil.ID);
        Patient patient = Patient.getById(id);
        ContactAdapter contactAdapter = (new ContactAdapter(this, new ArrayList<>(Contact.findByPatient(patient))));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(contactAdapter);
        setSupportActionBar(createToolBar("Contacts for " + name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientContactListActivity.this, PatientContactActivity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent(PatientContactListActivity.this, SelectionActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
        Contact contact = (Contact) parent.getAdapter().getItem(position);
        Intent intent = new Intent(PatientContactListActivity.this, PatientContactActivity.class);
        intent.putExtra(AppUtil.DETAILS_ID, contact.id);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }*/
}
