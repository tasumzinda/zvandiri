package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.Date;
/*
public class ChangePatientStatusActivity extends BaseActivity implements View.OnClickListener {

    private Spinner status;
    private Button save;
    private Patient item;
    private String id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_patient_status);
        status = (Spinner) findViewById(R.id.status);
        save = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        ArrayAdapter<PatientChangeEvent> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, PatientChangeEvent.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(id != null){
            item = Patient.findById(id);
            int i = 0;
            for(PatientChangeEvent m : PatientChangeEvent.values()){
                if(item.status != null && item.status.equals(status.getItemAtPosition(i))){
                    status.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        save.setOnClickListener(this);
        setSupportActionBar(createToolBar("Change Patient Status"));
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ChangePatientStatusActivity.this, ChangePatientStatusListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == save.getId()){
            save();
        }
    }

    public void save(){
        item.status = (PatientChangeEvent) status.getSelectedItem();
        item.dateModified = new Date();
        item.pushed = false;
        item.save();
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent = new Intent(ChangePatientStatusActivity.this, ChangePatientStatusListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}*/
