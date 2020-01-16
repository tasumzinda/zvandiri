package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.DisabilityCategory;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.PatientDisability;
import zw.org.zvandiri.business.domain.util.DisabilitySeverity;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.util.Calendar;

public class PatientDisabilityActivity extends BaseActivity implements View.OnClickListener {

    String id;
    Long itemId;
    Spinner disabilityCategory;
    Spinner disabilitySeverity;
    Spinner screened;
    EditText dateScreened;
    Button save;
    ArrayAdapter<YesNo> yesNoArrayAdapter;
    ArrayAdapter<DisabilityCategory> disabilityCategoryArrayAdapter;
    ArrayAdapter<DisabilitySeverity> disabilitySeverityArrayAdapter;
    DatePickerDialog dialog;
    Patient patient;
    PatientDisability item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_disability);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        itemId = intent.getLongExtra("itemId", 0L);
        disabilityCategory = (Spinner) findViewById(R.id.disabilityCategory);
        disabilitySeverity = (Spinner) findViewById(R.id.disabilitySeverity);
        screened = (Spinner) findViewById(R.id.screened);
        dateScreened = (EditText) findViewById(R.id.dateScreened);
        save = (Button) findViewById(R.id.save);
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        screened.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        disabilityCategoryArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, DisabilityCategory.getAll());
        disabilityCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disabilityCategory.setAdapter(disabilityCategoryArrayAdapter);
        disabilityCategoryArrayAdapter.notifyDataSetChanged();
        disabilitySeverityArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, DisabilitySeverity.values());
        disabilitySeverityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disabilitySeverity.setAdapter(disabilitySeverityArrayAdapter);
        disabilitySeverityArrayAdapter.notifyDataSetChanged();
        save.setOnClickListener(this);
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateScreened);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateScreened.setFocusable(false);
        dateScreened.setOnClickListener(this);
        screened.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = (YesNo) adapterView.getItemAtPosition(i);
                if(selected.equals(YesNo.YES)) {
                    dateScreened.setVisibility(View.VISIBLE);
                }else {
                    dateScreened.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemId != 0L){
            item = PatientDisability.getItem(itemId);
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.screened != null && item.screened.equals(screened.getItemAtPosition(i))){
                    screened.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(DisabilitySeverity m : DisabilitySeverity.values()){
                if(item.disabilitySeverity != null && item.disabilitySeverity.equals(disabilitySeverity.getItemAtPosition(i))){
                    disabilitySeverity.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(DisabilityCategory m : DisabilityCategory.getAll()){
                if(item.disabilityCategory != null && item.disabilityCategory.equals(disabilityCategory.getItemAtPosition(i))){
                    disabilityCategory.setSelection(i, true);
                    break;
                }
                i++;
            }
            updateLabel(item.dateScreened, dateScreened);
            patient = item.patient;
            id = patient.id;
            setSupportActionBar(createToolBar("Update Disability Details For " + patient));
        }else{
            item = new PatientDisability();
            patient = Patient.getById(id);
            setSupportActionBar(createToolBar("Add Disability Details For " + patient));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == dateScreened.getId()) {
            dialog.show();
        }
        if(view.getId() == save.getId()) {
            item.patient = patient;
            item.screened = (YesNo) screened.getSelectedItem();
            item.disabilitySeverity = (DisabilitySeverity) disabilitySeverity.getSelectedItem();
            item.disabilityCategory = (DisabilityCategory) disabilityCategory.getSelectedItem();
            item.dateScreened = DateUtil.getDateFromString(dateScreened.getText().toString());
            item.save();
            AppUtil.createShortNotification(this, "Saved successfully!");
            Intent intent = new Intent(this, PatientDisabilityListActivity.class);
            intent.putExtra(AppUtil.ID, patient.id);
            startActivity(intent);
            finish();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, PatientDisabilityListActivity.class);
        intent.putExtra(AppUtil.ID, patient.id);
        startActivity(intent);
        finish();
    }
}
