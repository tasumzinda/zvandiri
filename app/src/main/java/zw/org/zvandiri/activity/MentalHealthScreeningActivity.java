package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.IdentifiedRisk;
import zw.org.zvandiri.business.domain.util.MentalScreenResult;
import zw.org.zvandiri.business.domain.util.Result;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

public class MentalHealthScreeningActivity extends BaseActivity implements View.OnClickListener {

    Spinner screenedForMentalHealth;
    Spinner identifiedRisk;
    Spinner actionTaken;
    EditText actionTakenText;
    Spinner mentalScreenResult;
    Spinner rescreenIdentifiedRisk;
    Spinner rescreenActionTaken;
    EditText rescreenActionTakenText;
    LinearLayout rescreen;
    Button save;
    Patient patient;
    MentalHealthScreening item;
    ArrayAdapter<YesNo> yesNoArrayAdapter;
    ArrayAdapter<IdentifiedRisk> identifiedRiskArrayAdapter;
    ArrayAdapter<ActionTaken> actionTakenArrayAdapter;
    ArrayAdapter<MentalScreenResult> resultArrayAdapter;
    String id;
    Long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health_screening);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        itemId = intent.getLongExtra("itemId", 0L);
        screenedForMentalHealth = (Spinner) findViewById(R.id.screenedForMentalHealth);
        identifiedRisk = (Spinner) findViewById(R.id.identifiedRisk);
        actionTaken = (Spinner) findViewById(R.id.actionTaken);
        actionTakenText = (EditText) findViewById(R.id.actionTakenText);
        mentalScreenResult = (Spinner) findViewById(R.id.mentalScreenResult);
        rescreenIdentifiedRisk = (Spinner) findViewById(R.id.rescreenIdentifiedRisk);
        rescreenActionTaken = (Spinner) findViewById(R.id.rescreenActionTaken);
        rescreenActionTakenText = (EditText) findViewById(R.id.rescreenActionTakenText);
        rescreen = (LinearLayout) findViewById(R.id.rescreen);
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        screenedForMentalHealth.setAdapter(yesNoArrayAdapter);
        identifiedRiskArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, IdentifiedRisk.values());
        identifiedRiskArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        identifiedRisk.setAdapter(identifiedRiskArrayAdapter);
        rescreenIdentifiedRisk.setAdapter(identifiedRiskArrayAdapter);
        actionTakenArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ActionTaken.getAll());
        actionTakenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionTaken.setAdapter(actionTakenArrayAdapter);
        rescreenActionTaken.setAdapter(actionTakenArrayAdapter);
        resultArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, MentalScreenResult.values());
        resultArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mentalScreenResult.setAdapter(resultArrayAdapter);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        Patient patient = Patient.getById(id);
        mentalScreenResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mentalScreenResult.getSelectedItem().equals(MentalScreenResult.NO_IMPROVEMENT)){
                    rescreen.setVisibility(View.VISIBLE);
                }else{
                    rescreen.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(itemId != 0L){
            item = MentalHealthScreening.getItem(itemId);
            Log.d("Item", AppUtil.createGson().toJson(item));
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.screenedForMentalHealth != null && item.screenedForMentalHealth.equals(screenedForMentalHealth.getItemAtPosition(i))){
                    screenedForMentalHealth.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(IdentifiedRisk m : IdentifiedRisk.values()){
                if(item.identifiedRisk != null && item.identifiedRisk.equals(identifiedRisk.getItemAtPosition(i))){
                    identifiedRisk.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(IdentifiedRisk m : IdentifiedRisk.values()){
                if(item.rescreenIdentifiedRisk != null && item.rescreenIdentifiedRisk.equals(rescreenIdentifiedRisk.getItemAtPosition(i))){
                    rescreenIdentifiedRisk.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(ActionTaken m : ActionTaken.getAll()){
                if(item.actionTaken != null && item.actionTaken.equals(actionTaken.getItemAtPosition(i))){
                    actionTaken.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(ActionTaken m : ActionTaken.getAll()){
                if(item.rescreenActionTaken != null && item.rescreenActionTaken.equals(rescreenActionTaken.getItemAtPosition(i))){
                    rescreenActionTaken.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(MentalScreenResult m : MentalScreenResult.values()){
                if(item.mentalScreenResult != null && item.mentalScreenResult.equals(mentalScreenResult.getItemAtPosition(i))){
                    mentalScreenResult.setSelection(i, true);
                    break;
                }
                i++;
            }
            actionTakenText.setText(item.actionTakenText);
            rescreenActionTakenText.setText(item.rescreenActionTakenText);
            patient = item.patient;
            setSupportActionBar(createToolBar("Add Mental Health Screening History For " + patient));
        }else{
            item = new MentalHealthScreening();
            setSupportActionBar(createToolBar("Update Mental Health Screening History For " + patient));
        }
    }

    @Override
    public void onClick(View v) {
        item.patient = patient;
        item.screenedForMentalHealth = (YesNo) screenedForMentalHealth.getSelectedItem();
        item.identifiedRisk = (IdentifiedRisk) identifiedRisk.getSelectedItem();
        item.rescreenIdentifiedRisk = (IdentifiedRisk) rescreenIdentifiedRisk.getSelectedItem();
        item.actionTaken = (ActionTaken) actionTaken.getSelectedItem();
        item.rescreenActionTaken = (ActionTaken) rescreenActionTaken.getSelectedItem();
        item.mentalScreenResult = (MentalScreenResult) mentalScreenResult.getSelectedItem();
        item.actionTakenText = actionTakenText.getText().toString();
        item.rescreenActionTakenText = rescreenActionTakenText.getText().toString();
        item.save();
        AppUtil.createShortNotification(this, "Saved successfully!");
        Intent intent = new Intent(this, HivSelfTestingListActivity.class);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}
