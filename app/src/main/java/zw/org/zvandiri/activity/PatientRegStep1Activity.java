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
import zw.org.zvandiri.business.domain.util.HIVDisclosureLocation;
import zw.org.zvandiri.business.domain.util.TransmissionMode;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
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
    private EditText oiNumber;
    private Patient holder;
    private String mobileNumber;
    private String ownerName;
    private String secondaryMobileNumber;
    private String secondaryMobileOwnerName;
    private YesNo mobileOwner;
    private YesNo ownSecondaryMobile;
    private Relationship mobileOwnerRelation;
    private Relationship secondaryMobileownerRelation;
    private String email;
    private String address;
    private String address1;
    private String primaryClinicId;
    private String supportGroupId;
    private Date dateJoined;
    private Education education;
    private EducationLevel educationLevel;
    private Referer referer;
    private ReasonForNotReachingOLevel reasonForNotReachingOLevel;
    private String refererName;
    private YesNo hivStatusKnown;
    private TransmissionMode transmissionMode;
    private Date dateTested;
    private HIVDisclosureLocation hIVDisclosureLocation;
    private ArrayList<DisabilityCategory> disabilityCategorys;
    private YesNo disability;
    YesNo cat;
    YesNo consentToMHealth;
    YesNo consentToPhoto;
    YesNo youngMumGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step1);
        firstName = (EditText) findViewById(R.id.firstName);
        middleName = (EditText) findViewById(R.id.middleName);
        lastName = (EditText) findViewById(R.id.lastName);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        gender = (Spinner) findViewById(R.id.gender);
        oiNumber = (EditText) findViewById(R.id.OINumber);
        Intent intent = getIntent();
        holder = (Patient) intent.getSerializableExtra("patient");
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
        if(holder != null){
            updateLabel(holder.dateOfBirth, dateOfBirth);
            firstName.setText(holder.firstName);
            lastName.setText(holder.lastName);
            middleName.setText(holder.middleName);
            oiNumber.setText(holder.oINumber);
            int i = 0;
            for(Gender m : Gender.values()){
                if(holder.gender != null  && holder.gender.equals(gender.getItemAtPosition(i))){
                    gender.setSelection(i, true);
                    break;
                }
                i++;
            }
            mobileNumber = holder.mobileNumber;
            ownerName = holder.ownerName;
            secondaryMobileNumber = holder.secondaryMobileNumber;
            secondaryMobileOwnerName = holder.secondaryMobileOwnerName;
            mobileOwner = holder.mobileOwner;
            ownSecondaryMobile = holder.ownSecondaryMobile;
            mobileOwnerRelation = holder.mobileOwnerRelation;
            secondaryMobileownerRelation = holder.secondaryMobileownerRelation;
            email = holder.email;
            address = holder.address;
            address1 = holder.address1;
            primaryClinicId = holder.primaryClinicId;
            supportGroupId = holder.supportGroupId;
            dateJoined = holder.dateJoined;
            education = holder.education;
            educationLevel = holder.educationLevel;
            referer = holder.referer;
            reasonForNotReachingOLevel = holder.reasonForNotReachingOLevel;
            refererName = holder.refererName;
            hivStatusKnown = holder.hivStatusKnown;
            transmissionMode = holder.transmissionMode;
            dateTested = holder.dateTested;
            hIVDisclosureLocation = holder.hIVDisclosureLocation;
            disabilityCategorys = (ArrayList<DisabilityCategory>) holder.disabilityCategorys;
            disability = holder.disability;
            cat = holder.cat;
            consentToMHealth = holder.consentToMHealth;
            consentToPhoto = holder.consentToPhoto;
            youngMumGroup = holder.youngMumGroup;
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
                    holder = new Patient();
                    holder.dateOfBirth = DateUtil.getDateFromString(dateOfBirth.getText().toString());
                    holder.firstName = firstName.getText().toString();
                    holder.lastName = lastName.getText().toString();
                    holder.middleName = middleName.getText().toString();
                    holder.gender = (Gender) gender.getSelectedItem();
                    holder.oINumber = oiNumber.getText().toString();
                    holder.mobileNumber = mobileNumber;
                    holder.ownerName = ownerName;
                    holder.secondaryMobileNumber = secondaryMobileNumber;
                    holder.secondaryMobileOwnerName = secondaryMobileOwnerName;
                    holder.mobileOwner = mobileOwner;
                    holder.ownSecondaryMobile = ownSecondaryMobile;
                    holder.mobileOwnerRelation = mobileOwnerRelation;
                    holder.secondaryMobileownerRelation = secondaryMobileownerRelation;
                    holder.email = email;
                    holder.address = address;
                    holder.address1 = address1;
                    holder.primaryClinicId = primaryClinicId;
                    holder.supportGroupId = supportGroupId;
                    holder.dateJoined = dateJoined;
                    holder.education = education;
                    holder.educationLevel = educationLevel;
                    holder.referer = referer;
                    holder.reasonForNotReachingOLevel = reasonForNotReachingOLevel;
                    holder.refererName = refererName;
                    holder.hivStatusKnown = hivStatusKnown;
                    holder.transmissionMode = transmissionMode;
                    holder.dateTested = dateTested;
                    holder.hIVDisclosureLocation = hIVDisclosureLocation;
                    holder.disabilityCategorys = disabilityCategorys;
                    holder.disability = disability;
                    holder.cat = cat;
                    holder.consentToMHealth = consentToMHealth;
                    holder.consentToPhoto = consentToPhoto;
                    holder.youngMumGroup = youngMumGroup;
                    intent.putExtra("patient", holder);
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
        if(checkDateFormat(dateOfBirth.getText().toString())){
            if(DateUtil.getDateFromString(dateOfBirth.getText().toString()).after(today)){
                dateOfBirth.setError(this.getString(R.string.date_aftertoday));
                isValid = false;
            }else{
                dateOfBirth.setError(null);
            }
        }
        return isValid;
    }
}
