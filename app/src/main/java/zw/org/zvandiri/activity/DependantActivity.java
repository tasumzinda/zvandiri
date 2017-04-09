package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Dependent;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.HIVStatus;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;

public class DependantActivity extends BaseActivity implements View.OnClickListener {

    EditText firstName;
    EditText lastName;
    EditText dateOfBirth;
    Spinner gender;
    Spinner hivStatus;
    Button save;
    String id;
    String name;
    DatePickerDialog dialog;
    private Dependent item;
    private String itemID;
    private EditText[] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependant);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        gender = (Spinner) findViewById(R.id.gender);
        hivStatus = (Spinner) findViewById(R.id.hivStatus);
        save = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        fields = new EditText[] {firstName, lastName, dateOfBirth};
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateOfBirth);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        ArrayAdapter<Gender> genderArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Gender.values());
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderArrayAdapter);
        genderArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<HIVStatus> hivStatusArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, HIVStatus.values());
        hivStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivStatus.setAdapter(hivStatusArrayAdapter);
        hivStatusArrayAdapter.notifyDataSetChanged();
        dateOfBirth.setOnClickListener(this);
        save.setOnClickListener(this);
        if(itemID != null){
            item = Dependent.getItem(itemID);
            firstName.setText(item.firstName);
            lastName.setText(item.lastName);
            updateLabel(item.dateOfBirth, dateOfBirth);
            int i = 0;
            for(Gender m : Gender.values()){
                if(item.gender != null && item.gender.equals(gender.getItemAtPosition(i))){
                    gender.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(HIVStatus m : HIVStatus.values()){
                if(item.hivStatus != null && item.hivStatus.equals(hivStatus.getItemAtPosition(i))){
                    hivStatus.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Dependant Details"));
        }else{
            item = new Dependent();
            setSupportActionBar(createToolBar("Add Dependant"));
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

    public void onBackPressed(){
        Intent intent = new Intent(DependantActivity.this, DependantListActivity.class);
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
        if(view.getId() == dateOfBirth.getId()){
            dialog.show();
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
                item.dateOfBirth = DateUtil.getDateFromString(dateOfBirth.getText().toString());
                item.firstName = firstName.getText().toString();
                item.gender = (Gender) gender.getSelectedItem();
                item.hivStatus = (HIVStatus) hivStatus.getSelectedItem();
                item.lastName = lastName.getText().toString();
                Patient p = Patient.findById(id);
                item.patient = p;
                item.pushed = false;
                item.save();
                p.pushed = 1;
                p.save();
                AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
                Intent intent = new Intent(DependantActivity.this, DependantListActivity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(dateOfBirth.getText().toString())){
            dateOfBirth.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            dateOfBirth.setError(null);
        }
        return isValid;
    }
}
