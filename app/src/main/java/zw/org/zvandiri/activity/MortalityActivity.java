package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Mortality;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.CauseOfDeath;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.Calendar;

public class MortalityActivity extends BaseActivity implements View.OnClickListener {

    EditText dateOfDeath;
    Spinner causeOfDeath;
    Spinner receivingEnhancedCare;
    EditText datePutOnEnhancedCare;
    EditText descriptionOfCase;
    EditText careProvided;
    EditText home;
    EditText beneficiary;
    EditText facility;
    EditText cats;
    Spinner contactWithZM;
    EditText dateOfContactWithZim;
    LinearLayout zmLayout;
    EditText others;
    EditText learningPoints;
    EditText actionPlan;
    EditText zm;
    Button save;
    LinearLayout careLayout;
    Patient patient;
    Mortality item;
    ArrayAdapter<CauseOfDeath> causeOfDeathArrayAdapter;
    ArrayAdapter<YesNo> yesNoArrayAdapter;
    DatePickerDialog dialog;
    String id;
    Long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortality);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        itemId = intent.getLongExtra("itemId", 0L);
        dateOfDeath = (EditText) findViewById(R.id.dateOfDeath);
        causeOfDeath = (Spinner) findViewById(R.id.causeOfDeath);
        receivingEnhancedCare = (Spinner) findViewById(R.id.receivingEnhancedCare);
        datePutOnEnhancedCare = (EditText) findViewById(R.id.datePutOnEnhancedCare);
        descriptionOfCase = (EditText) findViewById(R.id.descriptionOfCase);
        careProvided = (EditText) findViewById(R.id.careProvided);
        home = (EditText) findViewById(R.id.home);
        beneficiary = (EditText) findViewById(R.id.beneficiary);
        facility = (EditText) findViewById(R.id.facility);
        cats = (EditText) findViewById(R.id.cats);
        contactWithZM = (Spinner) findViewById(R.id.contactWithZM);
        dateOfContactWithZim = (EditText) findViewById(R.id.dateOfContactWithZim);
        zmLayout = (LinearLayout) findViewById(R.id.zmLayout);
        others = (EditText) findViewById(R.id.others);
        learningPoints= (EditText) findViewById(R.id.learningPoints);
        actionPlan = (EditText) findViewById(R.id.actionPlan);
        zm = (EditText) findViewById(R.id.zm);
        careLayout = (LinearLayout) findViewById(R.id.careLayout);
        causeOfDeathArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CauseOfDeath.values());
        causeOfDeathArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        causeOfDeath.setAdapter(causeOfDeathArrayAdapter);
        causeOfDeathArrayAdapter.notifyDataSetChanged();
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        receivingEnhancedCare.setAdapter(yesNoArrayAdapter);
        contactWithZM.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        dateOfDeath.setFocusable(false);
        dateOfDeath.setOnClickListener(this);
        datePutOnEnhancedCare.setFocusable(false);
        datePutOnEnhancedCare.setOnClickListener(this);
        dateOfContactWithZim.setFocusable(false);
        dateOfContactWithZim.setOnClickListener(this);
        receivingEnhancedCare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = yesNoArrayAdapter.getItem(i);
                if(selected.equals(YesNo.YES)) {
                    careLayout.setVisibility(View.VISIBLE);
                }else {
                    careLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        contactWithZM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = yesNoArrayAdapter.getItem(i);
                if(selected.equals(YesNo.YES)) {
                    zmLayout.setVisibility(View.VISIBLE);
                }else {
                    zmLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemId != 0L){
            item = Mortality.getItem(itemId);
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.receivingEnhancedCare != null && item.receivingEnhancedCare.equals(receivingEnhancedCare.getItemAtPosition(i))){
                    receivingEnhancedCare.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(CauseOfDeath m : CauseOfDeath.values()){
                if(item.causeOfDeath != null && item.causeOfDeath.equals(causeOfDeath.getItemAtPosition(i))){
                    causeOfDeath.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.contactWithZM != null && item.contactWithZM.equals(contactWithZM.getItemAtPosition(i))){
                    contactWithZM.setSelection(i, true);
                    break;
                }
                i++;
            }
            updateLabel(item.dateOfDeath, dateOfDeath);
            if(item.datePutOnEnhancedCare != null) {
                updateLabel(item.datePutOnEnhancedCare, datePutOnEnhancedCare);
            }
            if(item.dateOfContactWithZim != null) {
                updateLabel(item.dateOfContactWithZim, dateOfContactWithZim);
            }
            descriptionOfCase.setText(item.descriptionOfCase);
            careProvided.setText(item.careProvided);
            home.setText(item.home);
            beneficiary.setText(item.beneficiary);
            facility.setText(item.facility);
            cats.setText(item.cats);
            others.setText(item.other);
            learningPoints.setText(item.learningPoints);
            actionPlan.setText(item.actionPlan);
            zm.setText(item.zm);
            patient = item.patient;
            id = patient.id;
            setSupportActionBar(createToolBar("Update Mortality Details For " + patient));
        }else{
            item = new Mortality();
            patient = Patient.getById(id);
            setSupportActionBar(createToolBar("Add Mortality Details For " + patient));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == dateOfDeath.getId()) {
            showDatePickerDialog(dateOfDeath);
        }
        if(v.getId() == datePutOnEnhancedCare.getId()) {
            showDatePickerDialog(datePutOnEnhancedCare);
        }
        if(v.getId() == dateOfContactWithZim.getId()) {
            showDatePickerDialog(dateOfContactWithZim);
        }
        if(v.getId() == save.getId()) {
            item.patient = patient;
            item.receivingEnhancedCare = (YesNo) receivingEnhancedCare.getSelectedItem();
            item.causeOfDeath = (CauseOfDeath) causeOfDeath.getSelectedItem();
            item.dateOfDeath = DateUtil.getDateFromString(dateOfDeath.getText().toString());
            if(!datePutOnEnhancedCare.getText().toString().isEmpty()) {
                item.datePutOnEnhancedCare = DateUtil.getDateFromString(datePutOnEnhancedCare.getText().toString());
            }
            if(!dateOfContactWithZim.getText().toString().isEmpty()) {
                item.dateOfContactWithZim = DateUtil.getDateFromString(dateOfContactWithZim.getText().toString());
            }
            item.descriptionOfCase = descriptionOfCase.getText().toString();
            item.careProvided = careProvided.getText().toString();
            item.home = home.getText().toString();
            item.beneficiary = beneficiary.getText().toString();
            item.facility = facility.getText().toString();
            item.cats = cats.getText().toString();
            item.contactWithZM = (YesNo) contactWithZM.getSelectedItem();
            item.other = others.getText().toString();
            item.learningPoints = learningPoints.getText().toString();
            item.actionPlan = actionPlan.getText().toString();
            item.zm = zm.getText().toString();
            item.save();
            AppUtil.createShortNotification(this, "Saved successfully!");
            Intent intent = new Intent(this, MortalityListActivity.class);
            intent.putExtra(AppUtil.ID, patient.id);
            startActivity(intent);
            finish();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, MortalityListActivity.class);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    private void showDatePickerDialog(final EditText field) {
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), field);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }
}
