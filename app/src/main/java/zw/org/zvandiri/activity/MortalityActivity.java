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
        Patient patient = Patient.getById(id);
        if(itemId != 0L){
            item = Mortality.getItem(itemId);
            int i = 0;
            /*for(YesNo m : YesNo.values()){
                if(item.screenedForMentalHealth != null && item.screenedForMentalHealth.equals(screenedForMentalHealth.getItemAtPosition(i))){
                    screenedForMentalHealth.setSelection(i, true);
                    break;
                }
                i++;
            }*/
            i = 0;
            /*actionTakenText.setText(item.actionTakenText);
            rescreenActionTakenText.setText(item.rescreenActionTakenText);*/
            patient = item.patient;
            setSupportActionBar(createToolBar("Update Mortality Details For " + patient));
        }else{
            item = new Mortality();
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
        /*item.screenedForMentalHealth = (YesNo) screenedForMentalHealth.getSelectedItem();
        item.identifiedRisk = (IdentifiedRisk) identifiedRisk.getSelectedItem();
        item.rescreenIdentifiedRisk = (IdentifiedRisk) rescreenIdentifiedRisk.getSelectedItem();
        item.actionTaken = (ActionTaken) actionTaken.getSelectedItem();
        item.rescreenActionTaken = (ActionTaken) rescreenActionTaken.getSelectedItem();
        item.mentalScreenResult = (MentalScreenResult) mentalScreenResult.getSelectedItem();
        item.actionTakenText = actionTakenText.getText().toString();
        item.rescreenActionTakenText = rescreenActionTakenText.getText().toString();*/
            item.save();
            AppUtil.createShortNotification(this, "Saved successfully!");
            Intent intent = new Intent(this, HivSelfTestingListActivity.class);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }
}
