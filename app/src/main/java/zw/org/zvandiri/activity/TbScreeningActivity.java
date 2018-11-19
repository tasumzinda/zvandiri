package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.Result;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

public class TbScreeningActivity extends BaseActivity implements View.OnClickListener {

    Spinner coughing;
    Spinner sweating;
    Spinner weightLoss;
    Spinner fever;
    Spinner currentlyOnTreatment;
    Spinner tbOutcome;
    Spinner tbTreatmentStatus;
    Spinner tbTreatmentOutcome;
    EditText typeOfDrug;
    Button save;
    ArrayAdapter<Result> resultArrayAdapter;
    ArrayAdapter<YesNo> yesNoArrayAdapter;
    TbScreening item;
    ArrayAdapter<TbTreatmentOutcome> tbTreatmentOutcomeArrayAdapter;
    ArrayAdapter<TbTreatmentStatus> tbTreatmentStatusArrayAdapter;
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_screening);
        Intent intent = getIntent();
        Long id = intent.getLongExtra(AppUtil.ID, 0L);
        Long itemId = intent.getLongExtra("itemId", 0L);
        coughing = (Spinner) findViewById(R.id.coughing);
        sweating = (Spinner) findViewById(R.id.sweating);
        weightLoss = (Spinner) findViewById(R.id.weightLoss);
        fever = (Spinner) findViewById(R.id.fever);
        currentlyOnTreatment = (Spinner) findViewById(R.id.currentlyOnTreatment);
        tbOutcome = (Spinner) findViewById(R.id.tbOutcome);
        tbTreatmentStatus = (Spinner) findViewById(R.id.tbTreatmentStatus);
        tbTreatmentOutcome = (Spinner) findViewById(R.id.tbTreatmentOutcome);
        typeOfDrug = (EditText) findViewById(R.id.typeOfDrug);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        resultArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Result.values());
        resultArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tbOutcome.setAdapter(resultArrayAdapter);
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coughing.setAdapter(yesNoArrayAdapter);
        sweating.setAdapter(yesNoArrayAdapter);
        weightLoss.setAdapter(yesNoArrayAdapter);
        fever.setAdapter(yesNoArrayAdapter);
        currentlyOnTreatment.setAdapter(yesNoArrayAdapter);
        tbTreatmentOutcomeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, TbTreatmentOutcome.values());
        tbTreatmentOutcomeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tbTreatmentOutcome.setAdapter(tbTreatmentOutcomeArrayAdapter);
        tbTreatmentStatusArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, TbTreatmentStatus.values());
        tbTreatmentStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tbTreatmentStatus.setAdapter(tbTreatmentStatusArrayAdapter);
        if(itemId != 0L){
            item = TbScreening.getItem(itemId);
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.coughing != null && item.coughing.equals(coughing.getItemAtPosition(i))){
                    coughing.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.sweating != null && item.sweating.equals(sweating.getItemAtPosition(i))){
                    sweating.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.weightLoss != null && item.weightLoss.equals(weightLoss.getItemAtPosition(i))){
                    weightLoss.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.fever != null && item.fever.equals(fever.getItemAtPosition(i))){
                    fever.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.currentlyOnTreatment != null && item.currentlyOnTreatment.equals(currentlyOnTreatment.getItemAtPosition(i))){
                    currentlyOnTreatment.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Result m : Result.values()){
                if(item.tbOutcome != null && item.tbOutcome.equals(tbOutcome.getItemAtPosition(i))){
                    tbOutcome.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(TbTreatmentStatus m : TbTreatmentStatus.values()){
                if(item.tbTreatmentStatus != null && item.tbTreatmentStatus.equals(tbTreatmentStatus.getItemAtPosition(i))){
                    tbTreatmentStatus.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(TbTreatmentOutcome m : TbTreatmentOutcome.values()){
                if(item.tbTreatmentOutcome != null && item.tbTreatmentOutcome.equals(tbTreatmentOutcome.getItemAtPosition(i))){
                    tbTreatmentOutcome.setSelection(i, true);
                    break;
                }
                i++;
            }
            typeOfDrug.setText(item.typeOfDrug);
            person = item.person;
            setSupportActionBar(createToolBar("Update TB Screening History For " + person.nameOfClient));
        }else{
            item = new TbScreening();
            person = Person.getItem(id);
            setSupportActionBar(createToolBar("Add TB Screening History For " + person.nameOfClient));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        item.typeOfDrug = typeOfDrug.getText().toString();
        item.coughing = (YesNo) coughing.getSelectedItem();
        item.sweating = (YesNo) sweating.getSelectedItem();
        item.weightLoss = (YesNo) weightLoss.getSelectedItem();
        item.fever = (YesNo) fever.getSelectedItem();
        item.currentlyOnTreatment = (YesNo) currentlyOnTreatment.getSelectedItem();
        item.tbOutcome = (Result) tbOutcome.getSelectedItem();
        item.tbTreatmentStatus = (TbTreatmentStatus) tbTreatmentStatus.getSelectedItem();
        item.tbTreatmentOutcome = (TbTreatmentOutcome) tbTreatmentOutcome.getSelectedItem();
        item.person = person;
        item.save();
        AppUtil.createShortNotification(this, "Saved successfully!");
        Intent intent = new Intent(this, TbScreeningListActivity.class);
        intent.putExtra(AppUtil.ID, person.getId());
        startActivity(intent);
        finish();
    }
}
