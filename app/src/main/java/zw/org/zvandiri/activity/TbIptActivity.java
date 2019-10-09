package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.TbIpt;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.TbTreatmentOutcome;
import zw.org.zvandiri.business.domain.util.TbIdentificationOutcome;
import zw.org.zvandiri.business.domain.util.TbSymptom;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class TbIptActivity extends BaseActivity implements View.OnClickListener {

    EditText dateScreened;
    Spinner screenedForTb;
    Spinner identifiedWithTb;
    Spinner tbIdentificationOutcome;
    EditText dateStartedTreatment;
    Spinner tbTreatmentOutcome;
    Spinner referredForIpt;
    Spinner onIpt;
    EditText dateStartedIpt;
    private ListView tbSymptoms;
    Button save;
    Patient patient;
    TbIpt item;
    ArrayAdapter<YesNo> yesNoArrayAdapter;
    private ArrayAdapter<TbSymptom> symptomArrayAdapter;
    ArrayAdapter<TbIdentificationOutcome> tbIdentificationOutcomeArrayAdapter;
    ArrayAdapter<TbTreatmentOutcome> tbTreatmentOutcomeArrayAdapter;
    DatePickerDialog dialog;
    DatePickerDialog dialog1;
    DatePickerDialog dialog2;
    String id;
    Long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_ipt);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        itemId = intent.getLongExtra("itemId", 0L);
        dateScreened = (EditText) findViewById(R.id.dateScreened);
        screenedForTb = (Spinner) findViewById(R.id.screenedForTb);
        tbSymptoms = (ListView) findViewById(R.id.tbSymptoms);
        identifiedWithTb = (Spinner) findViewById(R.id.identifiedWithTb);
        tbIdentificationOutcome = (Spinner) findViewById(R.id.tbIdentificationOutcome);
        dateStartedTreatment = (EditText) findViewById(R.id.dateStartedTreatment);
        tbTreatmentOutcome = (Spinner) findViewById(R.id.tbTreatmentOutcome);
        referredForIpt = (Spinner) findViewById(R.id.referredForIpt);
        onIpt = (Spinner) findViewById(R.id.onIpt);
        dateStartedIpt = (EditText) findViewById(R.id.dateStartedIpt);
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        screenedForTb.setAdapter(yesNoArrayAdapter);
        identifiedWithTb.setAdapter(yesNoArrayAdapter);
        referredForIpt.setAdapter(yesNoArrayAdapter);
        onIpt.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        tbIdentificationOutcomeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, TbIdentificationOutcome.values());
        tbIdentificationOutcomeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tbIdentificationOutcome.setAdapter(tbIdentificationOutcomeArrayAdapter);
        tbIdentificationOutcomeArrayAdapter.notifyDataSetChanged();
        tbTreatmentOutcomeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, TbTreatmentOutcome.values());
        tbTreatmentOutcomeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tbTreatmentOutcome.setAdapter(tbTreatmentOutcomeArrayAdapter);
        tbTreatmentOutcomeArrayAdapter.notifyDataSetChanged();
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        symptomArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, TbSymptom.values());
        tbSymptoms.setAdapter(symptomArrayAdapter);
        symptomArrayAdapter.notifyDataSetChanged();
        tbSymptoms.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        tbSymptoms.setItemsCanFocus(false);
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
        dialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateStartedTreatment);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateStartedTreatment.setFocusable(false);
        dateStartedTreatment.setOnClickListener(this);
        dialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateStartedIpt);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateStartedIpt.setFocusable(false);
        dateStartedIpt.setOnClickListener(this);
        if(itemId != 0L){
            item = TbIpt.getItem(itemId);
            int i = 0;
            /*for(YesNo m : YesNo.values()){
                if(item.receivingEnhancedCare != null && item.receivingEnhancedCare.equals(receivingEnhancedCare.getItemAtPosition(i))){
                    receivingEnhancedCare.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            updateLabel(item.dateScreened, dateOfDeath);
            if(item.datePutOnEnhancedCare != null) {
                updateLabel(item.datePutOnEnhancedCare, datePutOnEnhancedCare);
            }*/
            patient = item.patient;
            setSupportActionBar(createToolBar("Update TB/IPT Details For " + patient));
        }else{
            item = new TbIpt();
            patient = Patient.getById(id);
            setSupportActionBar(createToolBar("Add TB/IPT Details For " + patient));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == dateScreened.getId()) {
            dialog.show();
        }
        if(v.getId() == dateStartedTreatment.getId()) {
            dialog1.show();
        }
        if(v.getId() == dateStartedIpt.getId()) {
            dialog2.show();
        }
        if(v.getId() == save.getId()) {
            item.patient = patient;
            item.screenedForTb = (YesNo) screenedForTb.getSelectedItem();
            item.dateScreened = DateUtil.getDateFromString(dateScreened.getText().toString());
            item.save();
            AppUtil.createShortNotification(this, "Saved successfully!");
            Intent intent = new Intent(this, TbIptListActivity.class);
            intent.putExtra(AppUtil.ID, patient.id);
            startActivity(intent);
            finish();
        }
    }

    private ArrayList<TbSymptom> getTbSymptoms(){
        ArrayList<TbSymptom> a = new ArrayList<>();
        for(int i = 0; i < tbSymptoms.getCount(); i++){
            if(tbSymptoms.isItemChecked(i)){
                a.add(symptomArrayAdapter.getItem(i));
            }else{
                a.remove(symptomArrayAdapter.getItem(i));
            }
        }
        return a;
    }
}
