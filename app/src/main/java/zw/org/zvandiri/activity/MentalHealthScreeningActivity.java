package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.domain.util.Referral;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;
import zw.org.zvandiri.toolbox.ListViewUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class MentalHealthScreeningActivity extends BaseActivity implements View.OnClickListener {

    Spinner screenedForMentalHealth;
    ListView screening;
    Spinner risk;
    ListView identifiedRisks;
    Spinner support;
    ListView supports;
    Spinner referral;
    ListView referrals;
    Spinner diagnosis;
    ListView diagnoses;
    EditText otherDiagnosis;
    TextView otherDiagnosisLabel;
    LinearLayout diagnosisContainer;
    Spinner intervention;
    ListView interventions;
    EditText otherIntervention;
    TextView otherInterventionLabel;
    EditText dateScreened;
    TextView completeLabel;
    Spinner referralComplete;
    LinearLayout interventionContainer;
    LinearLayout container;
    Button save;
    Patient patient;
    MentalHealthScreening item;
    ArrayAdapter<YesNo> yesNoArrayAdapter;
    ArrayAdapter<Screening> screeningArrayAdapter;
    ArrayAdapter<IdentifiedRisk> identifiedRiskArrayAdapter;
    ArrayAdapter<Support> supportArrayAdapter;
    ArrayAdapter<Referral>  referralArrayAdapter;
    ArrayAdapter<Diagnosis> diagnosisArrayAdapter;
    ArrayAdapter<Intervention> interventionArrayAdapter;
    String id;
    Long itemId;
    DatePickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health_screening);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        itemId = intent.getLongExtra("itemId", 0L);
        screenedForMentalHealth = (Spinner) findViewById(R.id.screenedForMentalHealth);
        screening = (ListView) findViewById(R.id.screening);
        risk = (Spinner) findViewById(R.id.risk);
        identifiedRisks = (ListView) findViewById(R.id.identifiedRisks);
        support = (Spinner) findViewById(R.id.support);
        supports = (ListView) findViewById(R.id.supports);
        referral = (Spinner) findViewById(R.id.referral);
        referrals = (ListView) findViewById(R.id.referrals) ;
        diagnosis = (Spinner) findViewById(R.id.diagnosis);
        diagnoses = (ListView) findViewById(R.id.diagnoses);
        otherDiagnosis = (EditText) findViewById(R.id.otherDiagnosis);
        otherDiagnosisLabel = (TextView) findViewById(R.id.otherDiagnosisLabel);
        diagnosisContainer = (LinearLayout) findViewById(R.id.diagnosisContainer);
        intervention = (Spinner) findViewById(R.id.intervention);
        interventions = (ListView) findViewById(R.id.interventions);
        otherIntervention = (EditText) findViewById(R.id.otherIntervention);
        otherInterventionLabel = (TextView) findViewById(R.id.otherInterventionLabel);
        interventionContainer = (LinearLayout) findViewById(R.id.interventionContainer);
        container = (LinearLayout) findViewById(R.id.container);
        dateScreened = (EditText) findViewById(R.id.dateScreened);
        dateScreened.setFocusable(false);
        dateScreened.setOnClickListener(this);
        completeLabel = (TextView) findViewById(R.id.completeLabel);
        referralComplete = (Spinner) findViewById(R.id.referralComplete);
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        screenedForMentalHealth.setAdapter(yesNoArrayAdapter);
        risk.setAdapter(yesNoArrayAdapter);
        support.setAdapter(yesNoArrayAdapter);
        referral.setAdapter(yesNoArrayAdapter);
        diagnosis.setAdapter(yesNoArrayAdapter);
        intervention.setAdapter(yesNoArrayAdapter);
        referralComplete.setAdapter(yesNoArrayAdapter);
        screeningArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Screening.values());
        screening.setAdapter(screeningArrayAdapter);
        screeningArrayAdapter.notifyDataSetChanged();
        screening.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        screening.setItemsCanFocus(false);
        screening.setItemChecked(0, true);
        ListViewUtil.setListViewHeightBasedOnChildren(screening);
        identifiedRiskArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, IdentifiedRisk.values());
        identifiedRisks.setAdapter(identifiedRiskArrayAdapter);
        identifiedRiskArrayAdapter.notifyDataSetChanged();
        identifiedRisks.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        identifiedRisks.setItemsCanFocus(false);
        ListViewUtil.setListViewHeightBasedOnChildren(identifiedRisks);
        supportArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Support.values());
        supports.setAdapter(supportArrayAdapter);
        supportArrayAdapter.notifyDataSetChanged();
        supports.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        supports.setItemsCanFocus(false);
        ListViewUtil.setListViewHeightBasedOnChildren(supports);
        referralArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Referral.values());
        referrals.setAdapter(referralArrayAdapter);
        referralArrayAdapter.notifyDataSetChanged();
        referrals.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        referrals.setItemsCanFocus(false);
        ListViewUtil.setListViewHeightBasedOnChildren(referrals);
        diagnosisArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Diagnosis.values());
        diagnoses.setAdapter(diagnosisArrayAdapter);
        diagnosisArrayAdapter.notifyDataSetChanged();
        diagnoses.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        diagnoses.setItemsCanFocus(false);
        ListViewUtil.setListViewHeightBasedOnChildren(diagnoses);
        interventionArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Intervention.values());
        interventions.setAdapter(interventionArrayAdapter);
        interventionArrayAdapter.notifyDataSetChanged();
        interventions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        interventions.setItemsCanFocus(false);
        ListViewUtil.setListViewHeightBasedOnChildren(interventions);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        screenedForMentalHealth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(screenedForMentalHealth.getSelectedItem().equals(YesNo.YES)) {
                    container.setVisibility(View.VISIBLE);
                }else{
                    container.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        risk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = (YesNo) adapterView.getItemAtPosition(i);
                if(selected.equals(YesNo.YES)) {
                    identifiedRisks.setVisibility(View.VISIBLE);
                }else {
                    identifiedRisks.setVisibility(View.GONE);
                    identifiedRisks.clearChoices();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        support.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = (YesNo) adapterView.getItemAtPosition(i);
                if(selected.equals(YesNo.YES)) {
                    supports.setVisibility(View.VISIBLE);
                    completeLabel.setVisibility(View.VISIBLE);
                    referralComplete.setVisibility(View.VISIBLE);
                }else{
                    supports.setVisibility(View.GONE);
                    completeLabel.setVisibility(View.GONE);
                    referralComplete.setVisibility(View.GONE);
                    supports.clearChoices();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        referral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = (YesNo) adapterView.getItemAtPosition(i);
                if(selected.equals(YesNo.YES)) {
                    referrals.setVisibility(View.VISIBLE);
                }else{
                    referrals.setVisibility(View.GONE);
                    referrals.clearChoices();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        diagnosis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = (YesNo) adapterView.getItemAtPosition(i);
                if(selected.equals(YesNo.YES)) {
                    diagnosisContainer.setVisibility(View.VISIBLE);
                }else{
                    diagnosisContainer.setVisibility(View.GONE);
                    for(int k = 0; k < diagnoses.getCount(); k++) {
                        diagnoses.setItemChecked(k, false);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        intervention.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = (YesNo) adapterView.getItemAtPosition(i);
                if(selected.equals(YesNo.YES)) {
                    interventionContainer.setVisibility(View.VISIBLE);
                }else{
                    interventionContainer.setVisibility(View.GONE);
                    for(int k = 0; k < interventions.getCount(); k++) {
                        interventions.setItemChecked(k, false);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        interventions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intervention selected = (Intervention) adapterView.getItemAtPosition(i);
                boolean isSelected = interventions.isItemChecked(i);
                if(selected.equals(Intervention.OTHER)) {
                    if(isSelected) {
                        otherIntervention.setVisibility(View.VISIBLE);
                        otherInterventionLabel.setVisibility(View.VISIBLE);
                    }else{
                        otherIntervention.setVisibility(View.GONE);
                        otherInterventionLabel.setVisibility(View.GONE);
                        otherIntervention.setText(null);
                    }
                }
            }
        });
        diagnoses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Diagnosis selected = (Diagnosis) adapterView.getItemAtPosition(i);
                boolean isSelected = diagnoses.isItemChecked(i);
                if(selected.equals(Diagnosis.OTHER)) {
                    if(isSelected) {
                        otherDiagnosis.setVisibility(View.VISIBLE);
                        otherDiagnosisLabel.setVisibility(View.VISIBLE);
                    }else{
                        otherDiagnosis.setVisibility(View.GONE);
                        otherDiagnosisLabel.setVisibility(View.GONE);
                        otherDiagnosis.setText(null);
                    }
                }
            }
        });
        if(itemId != 0L){
            item = MentalHealthScreening.getItem(itemId);
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.screenedForMentalHealth != null && item.screenedForMentalHealth.equals(screenedForMentalHealth.getItemAtPosition(i))){
                    screenedForMentalHealth.setSelection(i, true);
                    break;
                }
                i++;
            }
            patient = item.patient;
            setSupportActionBar(createToolBar("Update Mental Health Screening History For " + patient));
        }else{
            patient = Patient.getById(id);
            item = new MentalHealthScreening();
            item.id = UUIDGen.generateUUID();
            setSupportActionBar(createToolBar("Add Mental Health Screening History For " + patient));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == save.getId()) {
            item.patient = patient;
            item.screenedForMentalHealth = (YesNo) screenedForMentalHealth.getSelectedItem();
            item.risk = (YesNo) risk.getSelectedItem();
            item.support = (YesNo) support.getSelectedItem();
            item.referral = (YesNo) referral.getSelectedItem();
            item.diagnosis = (YesNo) diagnosis.getSelectedItem();
            item.intervention = (YesNo) intervention.getSelectedItem();
            item.otherDiagnosis = otherDiagnosis.getText().toString();
            item.otherIntervention = otherIntervention.getText().toString();
            item.screening = getMode();
            if(!dateScreened.getText().toString().isEmpty()) {
                item.dateScreened = DateUtil.getDateFromString(dateScreened.getText().toString());
            }
            item.referralComplete = (YesNo) referralComplete.getSelectedItem();
            item.isNew = 1;
            Long id = item.save();
            if(itemId != 0L) {
                deleteSelections();
            }
            for(IdentifiedRisk current : getIdentifiedRisks()) {
                MentalHealthScreeningRiskContract contract = new MentalHealthScreeningRiskContract();
                contract.identifiedRisk = current;
                contract.mentalHealthScreening = MentalHealthScreening.getItem(id);
                contract.save();
            }
            for(Support current : getSupports()) {
                MentalHealthScreeningSupportContract contract = new MentalHealthScreeningSupportContract();
                contract.support = current;
                contract.mentalHealthScreening = MentalHealthScreening.getItem(id);
                contract.save();
            }
            for(Referral current : getReferrals()) {
                MentalHealthScreeningReferralContract contract = new MentalHealthScreeningReferralContract();
                contract.referral = current;
                contract.mentalHealthScreening = MentalHealthScreening.getItem(id);
                contract.save();
            }
            for(Diagnosis current : getDiagnoses()) {
                MentalHealthScreeningDiagnosisContract contract = new MentalHealthScreeningDiagnosisContract();
                contract.diagnosis = current;
                contract.mentalHealthScreening = MentalHealthScreening.getItem(id);
                contract.save();
            }
            for(Intervention current : getInterventions()) {
                MentalHealthScreeningInterventionContract contract = new MentalHealthScreeningInterventionContract();
                contract.intervention = current;
                contract.mentalHealthScreening = MentalHealthScreening.getItem(id);
                contract.save();
            }
            AppUtil.createShortNotification(this, "Saved successfully!");
            Intent intent = new Intent(this, MentalHealthScreeningListActivity.class);
            intent.putExtra(AppUtil.ID, patient.id);
            startActivity(intent);
            finish();
        }
        if(v.getId() == dateScreened.getId()) {
            showDatePickerDialog(dateScreened);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, MentalHealthScreeningListActivity.class);
        intent.putExtra(AppUtil.ID, patient.id);
        startActivity(intent);
        finish();
    }

    private ArrayList<IdentifiedRisk> getIdentifiedRisks(){
        ArrayList<IdentifiedRisk> a = new ArrayList<>();
        for(int i = 0; i < identifiedRisks.getCount(); i++){
            if(identifiedRisks.isItemChecked(i)){
                a.add(identifiedRiskArrayAdapter.getItem(i));
            }else{
                a.remove(identifiedRiskArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    private ArrayList<Support> getSupports(){
        ArrayList<Support> a = new ArrayList<>();
        for(int i = 0; i < supports.getCount(); i++){
            if(supports.isItemChecked(i)){
                a.add(supportArrayAdapter.getItem(i));
            }else{
                a.remove(supportArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    private ArrayList<Referral> getReferrals(){
        ArrayList<Referral> a = new ArrayList<>();
        for(int i = 0; i < referrals.getCount(); i++){
            if(referrals.isItemChecked(i)){
                a.add(referralArrayAdapter.getItem(i));
            }else{
                a.remove(referralArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    private ArrayList<Diagnosis> getDiagnoses(){
        ArrayList<Diagnosis> a = new ArrayList<>();
        for(int i = 0; i < diagnoses.getCount(); i++){
            if(diagnoses.isItemChecked(i)){
                a.add(diagnosisArrayAdapter.getItem(i));
            }else{
                a.remove(diagnosisArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    private ArrayList<Intervention> getInterventions(){
        ArrayList<Intervention> a = new ArrayList<>();
        for(int i = 0; i < interventions.getCount(); i++){
            if(interventions.isItemChecked(i)){
                a.add(interventionArrayAdapter.getItem(i));
            }else{
                a.remove(interventionArrayAdapter.getItem(i));
            }
        }
        return a;
    }

    private Screening getMode() {
        ArrayList<Screening> a = new ArrayList<>();
        for(int i = 0; i < screening.getCount(); i++){
            if(screening.isItemChecked(i)){
                a.add(screeningArrayAdapter.getItem(i));
            }else{
                a.remove(screeningArrayAdapter.getItem(i));
            }
        }
        return a.get(0);
    }

    private void deleteSelections() {
        for(MentalHealthScreeningRiskContract contract : MentalHealthScreeningRiskContract.findByMentalHealthScreening(item)) {
            if(contract != null) {
                contract.delete();
            }
        }
        for(MentalHealthScreeningSupportContract contract : MentalHealthScreeningSupportContract.findByMentalHealthScreening(item)) {
            if(contract != null) {
                contract.delete();
            }
        }
        for(MentalHealthScreeningReferralContract contract : MentalHealthScreeningReferralContract.findByMentalHealthScreening(item)) {
            if(contract != null) {
                contract.delete();
            }
        }
        for(MentalHealthScreeningDiagnosisContract contract : MentalHealthScreeningDiagnosisContract.findByMentalHealthScreening(item)) {
            if(contract != null) {
                contract.delete();
            }
        }
        for(MentalHealthScreeningInterventionContract contract : MentalHealthScreeningInterventionContract.findByMentalHealthScreening(item)) {
            if(contract != null) {
                contract.delete();
            }
        }
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
