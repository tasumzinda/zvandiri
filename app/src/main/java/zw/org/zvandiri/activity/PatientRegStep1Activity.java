package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 4/3/2017.
 */
public class PatientRegStep1Activity  extends BaseActivity implements View.OnClickListener{

    private EditText firstName;
    private EditText middleName;
    private EditText lastName;
    private Spinner gender;
    private EditText dateOfBirth;
    private Button next;
    private DatePickerDialog dialog;
    private EditText[] fields;
    private String itemID;
    private Patient item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step1);
        firstName = (EditText) findViewById(R.id.firstName);
        middleName = (EditText) findViewById(R.id.middleName);
        lastName = (EditText) findViewById(R.id.lastName);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        gender = (Spinner) findViewById(R.id.gender);
        Intent intent = getIntent();
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        fields = new EditText[] {firstName, lastName, dateOfBirth};
        next = (Button) findViewById(R.id.btn_save);
        ArrayAdapter<Gender> genderArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Gender.values());
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderArrayAdapter);
        genderArrayAdapter.notifyDataSetChanged();
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateOfBirth);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateOfBirth.setOnClickListener(this);
        if(itemID != null){
            item = Patient.findById(itemID);
            updateLabel(item.dateOfBirth, dateOfBirth);
            firstName.setText(item.firstName);
            lastName.setText(item.lastName);
            middleName.setText(item.middleName != null ? item.middleName : "");
            int i = 0;
            for(Gender m : Gender.values()){
                if(item.gender != null  && item.gender.equals(gender.getItemAtPosition(i))){
                    gender.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add Demographic Details Step 1 of 7 "));
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
        Intent intent = new Intent(PatientRegStep1Activity.this, PatientListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == dateOfBirth.getId()){
            dialog.show();
        }
        if(view.getId() == next.getId()){
            if(validate(fields)){
                if(validateLocal()){
                    Intent intent = new Intent(PatientRegStep1Activity.this, PatientRegStep2Activity.class);
                    intent.putExtra(AppUtil.DETAILS_ID, itemID);
                    intent.putExtra("dateOfBirth", dateOfBirth.getText().toString());
                    intent.putExtra("firstName", firstName.getText().toString());
                    intent.putExtra("lastName", lastName.getText().toString());
                    intent.putExtra("middleName", middleName.getText().toString());
                    intent.putExtra("gender", ((Gender) gender.getSelectedItem()).getCode());
                    startActivity(intent);
                    finish();
                }

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
        Date today = new Date();
        if(DateUtil.getDateFromString(dateOfBirth.getText().toString()).after(today)){
            dateOfBirth.setError(this.getString(R.string.date_aftertoday));
            isValid = false;
        }else{
            dateOfBirth.setError(null);
        }
        return isValid;
    }
}
