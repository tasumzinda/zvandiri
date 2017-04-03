package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.ChronicInfection;
import zw.org.zvandiri.business.domain.ChronicInfectionItem;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.CurrentStatus;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;

public class ChronicInfectionItemActivity extends BaseActivity implements View.OnClickListener {

    Spinner chronicInfection;
    EditText infectionDate;
    EditText medication;
    Spinner currentStatus;
    Button save;
    String id;
    String name;
    DatePickerDialog dialog;
    private ChronicInfectionItem item;
    private String chronicInfectionItemID;
    private EditText[] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronic_infection_item);
        chronicInfection = (Spinner) findViewById(R.id.chronicInfection);
        infectionDate = (EditText) findViewById(R.id.infectionDate);
        medication = (EditText) findViewById(R.id.medication);
        currentStatus = (Spinner) findViewById(R.id.currentStatus);
        save = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        fields = new EditText[] {medication, infectionDate};
        chronicInfectionItemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<ChronicInfection> chronicInfectionArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ChronicInfection.getAll());
        chronicInfectionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chronicInfection.setAdapter(chronicInfectionArrayAdapter);
        chronicInfectionArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<CurrentStatus> currentStatusArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CurrentStatus.values());
        currentStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentStatus.setAdapter(currentStatusArrayAdapter);
        currentStatusArrayAdapter.notifyDataSetChanged();
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), infectionDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        infectionDate.setOnClickListener(this);
        save.setOnClickListener(this);
        if(chronicInfectionItemID != null){
            item = ChronicInfectionItem.getItem(chronicInfectionItemID);
            updateLabel(item.infectionDate, infectionDate);
            medication.setText(item.medication);
            int i = 0;
            for(ChronicInfection c: ChronicInfection.getAll()){
                if(item.chronicInfection != null && item.chronicInfection.equals(chronicInfection.getItemAtPosition(i))){
                    chronicInfection.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(CurrentStatus c: CurrentStatus.values()){
                if(item.currentStatus != null && item.currentStatus.equals(currentStatus.getItemAtPosition(i))){
                    currentStatus.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Opportunistic Infections for " + name));
        }else{
            item = new ChronicInfectionItem();
            setSupportActionBar(createToolBar("Add Opportunistic Infection Item"));
        }
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
        Intent intent = new Intent(ChronicInfectionItemActivity.this, ChronicInfectionItemListActivity.class);
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
        if (view.getId() == infectionDate.getId()){
            dialog.show();
        }
    }

    public void save(){
        if(validate(fields)){
            if(validateLocal()){
                item.chronicInfection = (ChronicInfection) chronicInfection.getSelectedItem();
                item.currentStatus = (CurrentStatus) currentStatus.getSelectedItem();
                if(chronicInfectionItemID != null){
                    item.id = chronicInfectionItemID;
                    item.dateModified = new Date();
                    item.isNew = false;
                }else{
                    item.dateCreated = new Date();
                    item.id = UUIDGen.generateUUID();
                    item.isNew = true;
                }
                item.infectionDate = DateUtil.getDateFromString(infectionDate.getText().toString());
                item.medication = medication.getText().toString();
                Patient p = Patient.findById(id);
                item.patient = p;
                item.pushed = false;
                item.save();
                p.pushed = false;
                p.save();
                AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
                Intent intent = new Intent(ChronicInfectionItemActivity.this, ChronicInfectionItemListActivity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }
        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(infectionDate.getText().toString())){
            infectionDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            infectionDate.setError(null);
        }
        return isValid;
    }
}
