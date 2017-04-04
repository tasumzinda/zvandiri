package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.HIVDisclosureLocation;
import zw.org.zvandiri.business.domain.util.TransmissionMode;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.Calendar;

/**
 * Created by User on 4/3/2017.
 */
public class PatientRegStep5Activity extends BaseActivity implements View.OnClickListener{

    private Spinner hivStatusKnown;
    private Spinner transmissionMode;
    private TextView transmissionLabel;
    private EditText dateTested;
    private TextView locationLabel;
    private Spinner hIVDisclosureLocation;
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
    private String dateJoined;
    private String education;
    private String educationLevel;
    private String referer;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step5);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        dateJoined = intent.getStringExtra("dateJoined");
        education = intent.getStringExtra("education");
        educationLevel = intent.getStringExtra("educationLevel");
        referer = intent.getStringExtra("referer");
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
        dateTested = (EditText) findViewById(R.id.dateTested);
        hivStatusKnown = (Spinner) findViewById(R.id.hivStatusKnown);
        transmissionMode = (Spinner) findViewById(R.id.transmissionMode);
        hIVDisclosureLocation = (Spinner) findViewById(R.id.hIVDisclosureLocation);
        transmissionLabel = (TextView) findViewById(R.id.transmissionLabel);
        locationLabel = (TextView) findViewById(R.id.locationLabel);
        ArrayAdapter<YesNo> hivStatusKnownArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        hivStatusKnownArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivStatusKnown.setAdapter(hivStatusKnownArrayAdapter);
        hivStatusKnownArrayAdapter.notifyDataSetChanged();
        hivStatusKnown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(hivStatusKnown.getSelectedItem().equals(YesNo.YES)){
                    transmissionMode.setVisibility(View.VISIBLE);
                    ArrayAdapter<TransmissionMode> transmissionModeArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, TransmissionMode.values());
                    transmissionModeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    transmissionMode.setAdapter(transmissionModeArrayAdapter);
                    transmissionModeArrayAdapter.notifyDataSetChanged();
                    transmissionLabel.setVisibility(View.VISIBLE);
                    dateTested.setVisibility(View.VISIBLE);
                    locationLabel.setVisibility(View.VISIBLE);
                    hIVDisclosureLocation.setVisibility(View.VISIBLE);
                    ArrayAdapter<HIVDisclosureLocation> hIVDisclosureLocationArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, HIVDisclosureLocation.values());
                    hIVDisclosureLocationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hIVDisclosureLocation.setAdapter(hIVDisclosureLocationArrayAdapter);
                    hIVDisclosureLocationArrayAdapter.notifyDataSetChanged();
                }else{
                    transmissionMode.setVisibility(View.GONE);
                    transmissionLabel.setVisibility(View.GONE);
                    dateTested.setVisibility(View.GONE);
                    locationLabel.setVisibility(View.GONE);
                    hIVDisclosureLocation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateTested);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateTested.setOnClickListener(this);
        if(itemID != null){
            item = Patient.findById(itemID);
            updateLabel(item.dateTested, dateTested);
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.hivStatusKnown != null  && item.hivStatusKnown.equals(hivStatusKnown.getItemAtPosition(i))){
                    hivStatusKnown.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(TransmissionMode m : TransmissionMode.values()){
                if(item.transmissionMode != null  && item.transmissionMode.equals(transmissionMode.getItemAtPosition(i))){
                    transmissionMode.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(HIVDisclosureLocation m : HIVDisclosureLocation.values()){
                if(item.hIVDisclosureLocation != null  && item.hIVDisclosureLocation.equals(hIVDisclosureLocation.getItemAtPosition(i))){
                    hIVDisclosureLocation.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add HIV and Health Details Step 5 of 7 "));
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
        Intent intent = new Intent(PatientRegStep5Activity.this, PatientRegStep1Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == dateTested.getId()){
            dialog.show();
        }
        if(view.getId() == next.getId()){
            Intent intent = new Intent(PatientRegStep5Activity.this, PatientRegStep5ContActivity.class);
            intent.putExtra(AppUtil.DETAILS_ID, itemID);
            intent.putExtra("dateOfBirth", dateOfBirth);
            intent.putExtra("firstName", firstName);
            intent.putExtra("lastName", lastName);
            intent.putExtra("middleName", middleName);
            intent.putExtra("gender", gender);
            intent.putExtra("email", email);
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
            intent.putExtra("dateJoined", dateJoined);
            intent.putExtra("education", education);
            intent.putExtra("educationLevel", educationLevel);
            intent.putExtra("referer", referer);
            intent.putExtra("hivStatusKnown", ((YesNo) hivStatusKnown.getSelectedItem()).getCode());
            intent.putExtra("transmissionMode", ((TransmissionMode) transmissionMode.getSelectedItem()).getCode());
            intent.putExtra("dateTested", dateTested.getText().toString());
            intent.putExtra("hIVDisclosureLocation", ((HIVDisclosureLocation) hIVDisclosureLocation.getSelectedItem()).getCode());
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
