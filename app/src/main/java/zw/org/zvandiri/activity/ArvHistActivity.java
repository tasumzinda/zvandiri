package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.ArvMedicine;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArvHistActivity extends BaseActivity implements View.OnClickListener {

    EditText startDate;
    EditText endDate;
    Spinner arvMedicine;
    Button button;
    DatePickerDialog startDateDialog;
    DatePickerDialog endDateDialog;
    String id;
    String name;
    private ArvHist arvHist;
    String arvHistID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arv_hist);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        arvMedicine = (Spinner) findViewById(R.id.arvMedicine);
        button = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        Log.d("Id", id);
        name = intent.getStringExtra(AppUtil.NAME);
        arvHistID = intent.getStringExtra(AppUtil.DETAILS_ID);
        startDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), startDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        endDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), endDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        button.setOnClickListener(this);
        ArrayAdapter<ArvMedicine> arvMedicineArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ArvMedicine.getAll());
        arvMedicineArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arvMedicine.setAdapter(arvMedicineArrayAdapter);
        arvMedicineArrayAdapter.notifyDataSetChanged();
        if(arvHistID != null){
            arvHist = ArvHist.getItem(arvHistID);
            updateLabel(arvHist.startDate, startDate);
            updateLabel(arvHist.endDate, endDate);
            int i = 0;
            for(ArvMedicine item: ArvMedicine.getAll()){
                if(arvHist.arvMedicine != null && arvHist.arvMedicine.equals(arvMedicine.getItemAtPosition(i))){
                    arvMedicine.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Arv History for " + name));
        }else{
            arvHist = new ArvHist();
            setSupportActionBar(createToolBar("Add Arv History for " + name));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == button.getId()){
            save();
        }
        if(view.getId() == startDate.getId()){
            startDateDialog.show();
        }
        if(view.getId() == endDate.getId()){
            endDateDialog.show();
        }
    }

    public boolean validate(){
        boolean isValid = true;
        if(checkDateFormat(startDate.getText().toString()) == false){
            startDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            startDate.setError(null);
        }
        if(checkDateFormat(endDate.getText().toString()) == false){
            endDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            endDate.setError(null);
        }
        if( checkDateFormat(startDate.getText().toString()) == true &&  checkDateFormat(endDate.getText().toString()) == true){
            Date start = DateUtil.getDateFromString(startDate.getText().toString());
            Date end = DateUtil.getDateFromString(endDate.getText().toString());
            if(start.after(end)){
                endDate.setError(getResources().getString(R.string.date_mismatch_error));
                isValid = false;
            }else{
                endDate.setError(null);
            }
        }

        return isValid;
    }

    public void save(){
        if(validate()){
            arvHist.endDate = DateUtil.getDateFromString(endDate.getText().toString());
            arvHist.startDate = DateUtil.getDateFromString(startDate.getText().toString());
            arvHist.arvMedicine = (ArvMedicine) arvMedicine.getSelectedItem();
            Patient p = Patient.findById(id);
            arvHist.patient = p;
            if(arvHistID != null){
                arvHist.id = arvHistID;
                arvHist.dateModified = new Date();
                arvHist.isNew = false;
            }else{
                arvHist.id = UUIDGen.generateUUID();
                arvHist.dateCreated = new Date();
                arvHist.isNew = true;
            }
            arvHist.pushed = false;
            arvHist.save();
            p.pushed = false;
            p.save();
            Intent intent = new Intent(ArvHistActivity.this, ArvHistListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ArvHistActivity.this, ArvHistListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
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
}
