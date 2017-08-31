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
import java.util.List;

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
    private String mobileOwnerRelationId;
    private String secondaryMobileownerRelationId;
    private String email;
    private String address;
    private String address1;
    private String primaryClinicId;
    private String supportGroupId;
    private Date dateJoined;
    private String educationId;
    private String educationLevelId;
    private String refererId;
    private String reasonForNotReachingOLevelId;
    private String refererName;
    private YesNo hivStatusKnown;
    private TransmissionMode transmissionMode;
    private Date dateTested;
    private HIVDisclosureLocation hIVDisclosureLocation;
    private ArrayList<String> disabilityCategorysId;
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
            mobileOwnerRelationId = holder.mobileOwnerRelationId;
            secondaryMobileownerRelationId = holder.secondaryMobileownerRelationId;
            email = holder.email;
            address = holder.address;
            address1 = holder.address1;
            primaryClinicId = holder.primaryClinicId;
            supportGroupId = holder.supportGroupId;
            dateJoined = holder.dateJoined;
            educationId = holder.educationId;
            educationLevelId = holder.educationLevelId;
            refererId = holder.referrerId;
            reasonForNotReachingOLevelId = holder.reasonForNotReachingOLevelId;
            refererName = holder.refererName;
            hivStatusKnown = holder.hivStatusKnown;
            transmissionMode = holder.transmissionMode;
            dateTested = holder.dateTested;
            hIVDisclosureLocation = holder.hIVDisclosureLocation;
            disabilityCategorysId =  holder.disabilityCategorysId;
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
                    holder.mobileOwnerRelationId = mobileOwnerRelationId;
                    holder.secondaryMobileownerRelationId = secondaryMobileownerRelationId;
                    holder.email = email;
                    holder.address = address;
                    holder.address1 = address1;
                    holder.primaryClinicId = primaryClinicId;
                    holder.supportGroupId = supportGroupId;
                    holder.dateJoined = dateJoined;
                    holder.educationId = educationId;
                    holder.educationLevelId = educationLevelId;
                    holder.referrerId = refererId;
                    holder.reasonForNotReachingOLevelId = reasonForNotReachingOLevelId;
                    holder.refererName = refererName;
                    holder.hivStatusKnown = hivStatusKnown;
                    holder.transmissionMode = transmissionMode;
                    holder.dateTested = dateTested;
                    holder.hIVDisclosureLocation = hIVDisclosureLocation;
                    holder.disabilityCategorysId = disabilityCategorysId;
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
        String birth = dateOfBirth.getText().toString();
        Date today = new Date();
        if( ! checkDateFormat(birth)){
            dateOfBirth.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else  if(checkDateFormat(birth) && DateUtil.getDateFromString(birth).after(today)){
            dateOfBirth.setError(getResources().getString(R.string.date_aftertoday));
            isValid = false;
        }else if(checkDateFormat(birth) && ! DateUtil.getDateFromString(birth).after(today) && DateUtil.getDateFromString(birth).before(DateUtil.getDateFromAge(30))){
            dateOfBirth.setError(getResources().getString(R.string.date_too_early));
            isValid = false;
        }else{
            dateOfBirth.setError(null);
        }


        String name = firstName.getText().toString();
        if( ! name.isEmpty()){
            if( ! validateStrings(name)){
                firstName.setError(getResources().getString(R.string.name_format_error));
                isValid = false;
            }else{
                firstName.setError(null);
            }
        }
        name = lastName.getText().toString();
        if( ! name.isEmpty()){
            if( ! validateStrings(name)){
                lastName.setError(getResources().getString(R.string.name_format_error));
                isValid = false;
            }else{
                lastName.setError(null);
            }
        }
        name = middleName.getText().toString();
        if( ! name.isEmpty()){
            if( ! validateStrings(name)){
                middleName.setError(getResources().getString(R.string.name_format_error));
                isValid = false;
            }else{
                middleName.setError(null);
            }
        }
        return isValid;
    }

}
