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
import zw.org.zvandiri.business.util.AppUtil;

import java.util.Calendar;

/**
 * Created by User on 4/3/2017.
 */
public class PatientRegStep4Activity extends BaseActivity implements View.OnClickListener{

    private Spinner education;
    private Spinner educationLevel;
    private EditText dateJoined;
    private Spinner referer;
    private Button next;
    private String itemID;
    private Patient item;
    private String dateOfBirth;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer gender;
    private String mobileNumber;
    private String ownerName;
    private String secondaryMobileNumber;
    private String secondaryMobileOwnerName;
    private Integer mobileOwner;
    private Integer ownSecondaryMobile;
    private String mobileOwnerRelation;
    private String secondaryMobileownerRelation;
    private String address;
    private String address1;
    private String primaryClinic;
    private String supportGroup;
    private DatePickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step4);
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        address1 = intent.getStringExtra("address1");
        primaryClinic = intent.getStringExtra("primaryClinic");
        supportGroup = intent.getStringExtra("supportGroup");
        firstName = intent.getStringExtra("firstName");
        middleName = intent.getStringExtra("middleName");
        lastName = intent.getStringExtra("lastName");
        dateOfBirth = intent.getStringExtra("dateOfBirth");
        gender = intent.getIntExtra("gender", 0);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        mobileNumber = intent.getStringExtra("mobileNumber");
        ownerName = intent.getStringExtra("ownerName");
        secondaryMobileNumber = intent.getStringExtra("secondaryMobileNumber");
        secondaryMobileOwnerName = intent.getStringExtra("secondaryMobileOwnerName");
        mobileOwner = intent.getIntExtra("mobileOwner", 0);
        ownSecondaryMobile = intent.getIntExtra("ownSecondaryMobile", 0);
        mobileOwnerRelation = intent.getStringExtra("mobileOwnerRelation");
        secondaryMobileownerRelation = intent.getStringExtra("secondaryMobileownerRelation");
        next = (Button) findViewById(R.id.btn_save);
        dateJoined = (EditText) findViewById(R.id.dateJoined);
        education = (Spinner) findViewById(R.id.education);
        educationLevel = (Spinner) findViewById(R.id.educationLevel);
        referer = (Spinner) findViewById(R.id.referer);
        ArrayAdapter<Education> educationArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Education.getAll());
        educationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setAdapter(educationArrayAdapter);
        educationArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<EducationLevel> educationLevelArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, EducationLevel.getAll());
        educationLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        educationLevel.setAdapter(educationLevelArrayAdapter);
        educationLevelArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Referer> refererArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Referer.getAll());
        refererArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        referer.setAdapter(refererArrayAdapter);
        refererArrayAdapter.notifyDataSetChanged();
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateJoined);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateJoined.setOnClickListener(this);
        if(itemID != null){
            item = Patient.findById(itemID);
            updateLabel(item.dateJoined, dateJoined);
            int i = 0;
            for(Education m : Education.getAll()){
                if(item.education != null  && item.education.equals(education.getItemAtPosition(i))){
                    education.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(EducationLevel m : EducationLevel.getAll()){
                if(item.educationLevel != null  && item.educationLevel.equals(educationLevel.getItemAtPosition(i))){
                    educationLevel.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Referer m : Referer.getAll()){
                if(item.referer != null  && item.referer.equals(referer.getItemAtPosition(i))){
                    referer.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add Education and Other Details Step 4 of 7 "));
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
        Intent intent = new Intent(PatientRegStep4Activity.this, PatientRegStep1Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            Intent intent = new Intent(PatientRegStep4Activity.this, PatientContactActivityStep2.class);
            intent.putExtra(AppUtil.DETAILS_ID, itemID);
            intent.putExtra("dateOfBirth", dateOfBirth);
            intent.putExtra("firstName", firstName);
            intent.putExtra("lastName", lastName);
            intent.putExtra("middleName", middleName);
            intent.putExtra("gender", gender);
            intent.putExtra("mobileNumber", mobileNumber);
            intent.putExtra("ownerName", ownerName);
            intent.putExtra("secondaryMobileNumber", secondaryMobileNumber);
            intent.putExtra("secondaryMobileOwnerName", secondaryMobileOwnerName);
            intent.putExtra("mobileOwner", mobileOwner);
            intent.putExtra("ownSecondaryMobile", ownSecondaryMobile);
            intent.putExtra("mobileOwnerRelation", mobileOwnerRelation);
            intent.putExtra("secondaryMobileownerRelation", secondaryMobileownerRelation);
            intent.putExtra("address", address);
            intent.putExtra("address1", address1);
            intent.putExtra("primaryClinic", primaryClinic);
            intent.putExtra("supportGroup", supportGroup);
            intent.putExtra("dateJoined", dateJoined.getText().toString());
            intent.putExtra("education", ((Education) education.getSelectedItem()).id);
            intent.putExtra("educationLevel", ((EducationLevel) educationLevel.getSelectedItem()).id);
            intent.putExtra("referer", ((Referer) referer.getSelectedItem()).id);
            startActivity(intent);
            finish();
        }
    }

    /*public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(dateOfBirth.getText().toString())){
            dateOfBirth.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            dateOfBirth.setError(null);
        }
        return isValid;
    }*/
}
