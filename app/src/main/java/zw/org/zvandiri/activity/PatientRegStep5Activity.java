package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.DisabilityCategory;
import zw.org.zvandiri.business.domain.EducationLevel;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.HIVDisclosureLocation;
import zw.org.zvandiri.business.domain.util.TransmissionMode;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 4/3/2017.
 */
public class PatientRegStep5Activity extends BaseActivity implements View.OnClickListener{

    private Spinner hivStatusKnown;
    private Spinner transmissionMode;
    private TextView transmissionLabel;
    private EditText dateTested;
    private TextView locationLabel;
    private Spinner hIVDisclosureLocation;
    private Button next;
    private String itemID;
    private DatePickerDialog dialog;
    private Patient holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step5);
        Intent intent = getIntent();
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        holder = (Patient) intent.getSerializableExtra("patient");
        next = (Button) findViewById(R.id.btn_save);
        dateTested = (EditText) findViewById(R.id.dateTested);
        hivStatusKnown = (Spinner) findViewById(R.id.hivStatusKnown);
        transmissionMode = (Spinner) findViewById(R.id.transmissionMode);
        hIVDisclosureLocation = (Spinner) findViewById(R.id.hIVDisclosureLocation);
        transmissionLabel = (TextView) findViewById(R.id.transmissionLabel);
        locationLabel = (TextView) findViewById(R.id.locationLabel);
        ArrayAdapter<YesNo> hivStatusKnownArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        hivStatusKnownArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivStatusKnown.setAdapter(hivStatusKnownArrayAdapter);
        hivStatusKnownArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<TransmissionMode> transmissionModeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, TransmissionMode.values());
        transmissionModeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transmissionMode.setAdapter(transmissionModeArrayAdapter);
        transmissionModeArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<HIVDisclosureLocation> hIVDisclosureLocationArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, HIVDisclosureLocation.values());
        hIVDisclosureLocationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hIVDisclosureLocation.setAdapter(hIVDisclosureLocationArrayAdapter);
        hIVDisclosureLocationArrayAdapter.notifyDataSetChanged();
        hivStatusKnown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(hivStatusKnown.getSelectedItem().equals(YesNo.YES)){
                    transmissionMode.setVisibility(View.VISIBLE);
                    transmissionLabel.setVisibility(View.VISIBLE);
                    dateTested.setVisibility(View.VISIBLE);
                    locationLabel.setVisibility(View.VISIBLE);
                    hIVDisclosureLocation.setVisibility(View.VISIBLE);

                }else{
                    transmissionMode.setVisibility(View.GONE);
                    transmissionLabel.setVisibility(View.GONE);
                    dateTested.setVisibility(View.GONE);
                    locationLabel.setVisibility(View.GONE);
                    hIVDisclosureLocation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateTested);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateTested.setOnClickListener(this);
        if(holder.hivStatusKnown != null){
            if(holder.dateTested != null){
                updateLabel(holder.dateTested, dateTested);
            }
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.hivStatusKnown != null  && holder.hivStatusKnown.equals(hivStatusKnown.getItemAtPosition(i))){
                    hivStatusKnown.setSelection(i, true);
                    break;
                }
                i++;
            }

            i = 0;
            for(TransmissionMode m : TransmissionMode.values()){
                if(holder.transmissionMode != null && holder.transmissionMode.equals(transmissionMode.getItemAtPosition(i))){
                    transmissionMode.setSelection(i);
                    break;
                }
                i++;
            }

            i = 0;
            for(HIVDisclosureLocation m : HIVDisclosureLocation.values()){
                if(holder.hIVDisclosureLocation != null && holder.hIVDisclosureLocation.equals(hIVDisclosureLocation.getItemAtPosition(i))){
                    hIVDisclosureLocation.setSelection(i);
                    break;
                }
                i++;
            }
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add HIV and Health Details Step 5 of 7 "));
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

    public void onBackPressed(){
        Intent intent = new Intent(PatientRegStep5Activity.this, PatientRegStep4Activity.class);
        holder.hivStatusKnown = (YesNo) hivStatusKnown.getSelectedItem();
        holder.transmissionMode = (TransmissionMode) transmissionMode.getSelectedItem();
        holder.hIVDisclosureLocation = (HIVDisclosureLocation) hIVDisclosureLocation.getSelectedItem();
        if( ! dateTested.getText().toString().isEmpty()){
            holder.dateTested = DateUtil.getDateFromString(dateTested.getText().toString());
        }
        intent.putExtra("patient", holder);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == dateTested.getId()){
            dialog.show();
        }
        if(view.getId() == next.getId()){
            Intent intent = new Intent(PatientRegStep5Activity.this, PatientRegStep5ContActivity.class);
            intent.putExtra(AppUtil.DETAILS_ID, itemID);
            holder.hivStatusKnown = (YesNo) hivStatusKnown.getSelectedItem();
            holder.transmissionMode = (TransmissionMode) transmissionMode.getSelectedItem();
            holder.hIVDisclosureLocation = (HIVDisclosureLocation) hIVDisclosureLocation.getSelectedItem();
            if( ! dateTested.getText().toString().isEmpty()){
                holder.dateTested = DateUtil.getDateFromString(dateTested.getText().toString());
            }
            intent.putExtra("patient", holder);
            startActivity(intent);
            finish();
        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        Date today = new Date();
        if( ! dateTested.getText().toString().isEmpty()){
            if(DateUtil.getDateFromString(dateTested.getText().toString()).after(today)){
                dateTested.setError(this.getString(R.string.date_aftertoday));
                isValid = false;
            }else{
                dateTested.setError(null);
            }
        }
        return isValid;
    }
}
