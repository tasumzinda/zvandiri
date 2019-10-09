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
    EditText caseBackground;
    EditText careProvided;
    EditText home;
    EditText beneficiary;
    EditText facility;
    EditText cats;
    EditText zm;
    EditText others;
    EditText learningPoints;
    EditText actionPlan;
    Button save;
    LinearLayout careLayout;
    Patient patient;
    Mortality item;
    ArrayAdapter<CauseOfDeath> causeOfDeathArrayAdapter;
    ArrayAdapter<YesNo> yesNoArrayAdapter;
    DatePickerDialog dialog;
    DatePickerDialog dialog1;
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
        caseBackground = (EditText) findViewById(R.id.caseBackground);
        careProvided = (EditText) findViewById(R.id.careProvided);
        home = (EditText) findViewById(R.id.home);
        beneficiary = (EditText) findViewById(R.id.beneficiary);
        facility = (EditText) findViewById(R.id.facility);
        cats = (EditText) findViewById(R.id.cats);
        zm = (EditText) findViewById(R.id.zm);
        others = (EditText) findViewById(R.id.others);
        learningPoints= (EditText) findViewById(R.id.learningPoints);
        actionPlan = (EditText) findViewById(R.id.actionPlan);
        careLayout = (LinearLayout) findViewById(R.id.careLayout);
        causeOfDeathArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CauseOfDeath.values());
        causeOfDeathArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        causeOfDeath.setAdapter(causeOfDeathArrayAdapter);
        causeOfDeathArrayAdapter.notifyDataSetChanged();
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        receivingEnhancedCare.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateOfDeath);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateOfDeath.setFocusable(false);
        dateOfDeath.setOnClickListener(this);
        dialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), datePutOnEnhancedCare);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePutOnEnhancedCare.setFocusable(false);
        datePutOnEnhancedCare.setOnClickListener(this);
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
            updateLabel(item.dateOfDeath, dateOfDeath);
            if(item.datePutOnEnhancedCare != null) {
                updateLabel(item.datePutOnEnhancedCare, datePutOnEnhancedCare);
            }
            caseBackground.setText(item.caseBackground);
            careProvided.setText(item.careProvided);
            home.setText(item.home);
            beneficiary.setText(item.beneficiary);
            facility.setText(item.facility);
            cats.setText(item.cats);
            zm.setText(item.zm);
            others.setText(item.others);
            learningPoints.setText(item.learningPoints);
            actionPlan.setText(item.actionPlan);
            patient = item.patient;
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
            dialog.show();
        }
        if(v.getId() == datePutOnEnhancedCare.getId()) {
            dialog1.show();
        }
        if(v.getId() == save.getId()) {
            item.patient = patient;
            item.receivingEnhancedCare = (YesNo) receivingEnhancedCare.getSelectedItem();
            item.causeOfDeath = (CauseOfDeath) causeOfDeath.getSelectedItem();
            item.dateOfDeath = DateUtil.getDateFromString(dateOfDeath.getText().toString());
            if(!datePutOnEnhancedCare.getText().toString().isEmpty()) {
                item.datePutOnEnhancedCare = DateUtil.getDateFromString(datePutOnEnhancedCare.getText().toString());
            }
            item.caseBackground = caseBackground.getText().toString();
            item.careProvided = careProvided.getText().toString();
            item.home = home.getText().toString();
            item.beneficiary = beneficiary.getText().toString();
            item.facility = facility.getText().toString();
            item.cats = cats.getText().toString();
            item.zm = zm.getText().toString();
            item.others = others.getText().toString();
            item.learningPoints = learningPoints.getText().toString();
            item.actionPlan = actionPlan.getText().toString();
            item.save();
            AppUtil.createShortNotification(this, "Saved successfully!");
            Intent intent = new Intent(this, MortalityListActivity.class);
            intent.putExtra(AppUtil.ID, patient.id);
            startActivity(intent);
            finish();
        }
    }
}
