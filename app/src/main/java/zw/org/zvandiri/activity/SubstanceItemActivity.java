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
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Substance;
import zw.org.zvandiri.business.domain.SubstanceItem;
import zw.org.zvandiri.business.domain.util.DrugIntervention;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SubstanceItemActivity extends BaseActivity implements View.OnClickListener {

    Spinner substance;
    Spinner current;
    Spinner past;
    EditText typeAmount;
    EditText startDate;
    EditText endDate;
    Spinner drugIntervention;
    Button save;
    String id;
    String name;
    private SubstanceItem item;
    private String itemID;
    private EditText[] fields;

    DatePickerDialog startDateDialog;
    DatePickerDialog endDateDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substance_item);
        substance = (Spinner) findViewById(R.id.substance);
        current = (Spinner) findViewById(R.id.current);
        past = (Spinner) findViewById(R.id.past);
        typeAmount = (EditText) findViewById(R.id.typeAmount);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        drugIntervention = (Spinner) findViewById(R.id.drugIntervention);
        save = (Button) findViewById(R.id.btn_save);
        fields = new EditText[] {startDate};
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<Substance> substanceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Substance.getAll());
        substanceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        substance.setAdapter(substanceArrayAdapter);
        substanceArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<DrugIntervention> drugInterventionArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, DrugIntervention.values());
        drugInterventionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drugIntervention.setAdapter(drugInterventionArrayAdapter);
        drugInterventionArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<YesNo> yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        past.setAdapter(yesNoArrayAdapter);
        current.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
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
        save.setOnClickListener(this);
        if(itemID != null){
            item = SubstanceItem.getItem(itemID);
            updateLabel(item.startDate, startDate);
            typeAmount.setText(item.typeAmount);
            if(item.endDate != null){
                updateLabel(item.endDate, endDate);
            }
            int i = 0;
            for(Substance m : Substance.getAll()){
                if(item.substance != null && item.substance.equals(substance.getItemAtPosition(i))){
                    substance.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.current != null && item.current.equals(current.getItemAtPosition(i))){
                    current.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.past != null && item.past.equals(past.getItemAtPosition(i))){
                    past.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(DrugIntervention m : DrugIntervention.values()){
                if(item.drugIntervention != null && item.drugIntervention.equals(drugIntervention.getItemAtPosition(i))){
                    drugIntervention.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Substance Items"));
        }else{
            item = new SubstanceItem();
            setSupportActionBar(createToolBar("Add Substance Items"));
        }
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SubstanceItemActivity.this, SubstanceItemListActivity.class);
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
        if(view.getId() == startDate.getId()){
            startDateDialog.show();
        }
        if(view.getId() == endDate.getId()){
            endDateDialog.show();
        }
    }

    public void save(){
        if(validate(fields)){
            if(validateLocal()){
                if(itemID != null){
                    item.id = itemID;
                    item.dateModified = new Date();
                    item.isNew = false;
                }else{
                    item.id = UUIDGen.generateUUID();
                    item.dateCreated = new Date();
                    item.isNew = true;
                }
                item.current = (YesNo) current.getSelectedItem();
                item.drugIntervention = (DrugIntervention) drugIntervention.getSelectedItem();
                if( ! endDate.getText().toString().isEmpty()){
                    item.endDate = DateUtil.getDateFromString(endDate.getText().toString());
                }
                item.past = (YesNo) past.getSelectedItem();
                Patient p = Patient.findById(id);
                item.patient = p;
                item.startDate = DateUtil.getDateFromString(startDate.getText().toString());
                item.substance = (Substance) substance.getSelectedItem();
                item.typeAmount = typeAmount.getText().toString();
                typeAmount.setText(String.valueOf(item.typeAmount));
                item.pushed = false;
                item.save();
                p.pushed = 1;
                p.save();
                AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
                Intent intent = new Intent(SubstanceItemActivity.this, SubstanceItemListActivity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }
        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(startDate.getText().toString())){
            startDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            startDate.setError(null);
        }
        if( ! endDate.getText().toString().isEmpty()){
            if( ! checkDateFormat(endDate.getText().toString())){
                endDate.setError(getResources().getString(R.string.date_format_error));
                isValid = false;
            }else{
                endDate.setError(null);
            }
        }

        if( checkDateFormat(startDate.getText().toString()) && checkDateFormat(endDate.getText().toString())){
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
}
