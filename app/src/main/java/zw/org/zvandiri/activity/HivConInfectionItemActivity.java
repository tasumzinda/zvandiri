package zw.org.zvandiri.activity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.HivCoInfection;
import zw.org.zvandiri.business.domain.HivConInfectionItem;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;

public class HivConInfectionItemActivity extends BaseActivity implements View.OnClickListener {

    Spinner hivCoInfection;
    EditText infectionDate;
    EditText medication;
    EditText resolution;
    Button save;
    String id;
    String name;
    DatePickerDialog dialog;
    private HivConInfectionItem item;
    private String itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiv_con_infection_item);
        hivCoInfection = (Spinner) findViewById(R.id.hivCoInfection);
        infectionDate = (EditText) findViewById(R.id.infectionDate);
        medication = (EditText) findViewById(R.id.medication);
        resolution = (EditText) findViewById(R.id.resolution);
        save = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name  = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<HivCoInfection> hivCoInfectionArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, HivCoInfection.getAll());
        hivCoInfectionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivCoInfection.setAdapter(hivCoInfectionArrayAdapter);
        hivCoInfectionArrayAdapter.notifyDataSetChanged();
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
        if(itemID != null){
            item = HivConInfectionItem.getItem(itemID);
            updateLabel(item.infectionDate, infectionDate);
            medication.setText(item.medication);
            resolution.setText(item.resolution);
            int i = 0;
            for(HivCoInfection m : HivCoInfection.getAll()){
                if(item.hivCoInfection != null && item.hivCoInfection.equals(hivCoInfection.getItemAtPosition(i))){
                    hivCoInfection.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Hiv Con-Infection Item"));
        }else{
            item = new HivConInfectionItem();
            setSupportActionBar(createToolBar("Add Hiv Con-Infection Item"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(HivConInfectionItemActivity.this, PatientActivity.class);
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

        if(view.getId() == infectionDate.getId()){
            dialog.show();
        }
    }

    public boolean validate(){
        boolean isValid = true;
        if(infectionDate.getText().toString().isEmpty()){
            infectionDate.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            infectionDate.setError(null);
        }
        if(medication.getText().toString().isEmpty()){
            medication.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            medication.setError(null);
        }
        if(resolution.getText().toString().isEmpty()){
            resolution.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            resolution.setError(null);
        }
        return isValid;
    }

   public void save(){
       if(validate()){
           if(validateLocal()){
               if (itemID != null){
                   item.id = itemID;
                   item.dateModified = new Date();
                   item.isNew = false;
               }else{
                   item.dateCreated = new Date();
                   item.id = UUIDGen.generateUUID();
                   item.isNew = true;
               }
               item.hivCoInfection = (HivCoInfection) hivCoInfection.getSelectedItem();
               item.infectionDate = DateUtil.getDateFromString(infectionDate.getText().toString());
               item.medication = medication.getText().toString();
               item.resolution = resolution.getText().toString();
               Patient p = Patient.findById(id);
               item.patient = p;
               item.pushed = false;
               item.save();
               p.pushed = 1;
               p.save();
               Intent intent = new Intent(HivConInfectionItemActivity.this, HivConInfectionItemListActivity.class);
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
