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
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 4/4/2017.
 */
public class PatientRegStep7Activity extends BaseActivity implements View.OnClickListener{

    private EditText pfirstName;
    private EditText pmobileNumber;
    private EditText plastName;
    private Spinner pgender;
    private Button next;
    private Spinner relationship;
    private EditText[] fields;
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
    private String dateJoined;
    private String education;
    private String educationLevel;
    private String referer;
    private Integer hivStatusKnown;
    private Integer transmissionMode;
    private String dateTested;
    private Integer hIVDisclosureLocation;
    private ArrayList<String> disabilityCategorys;
    private Integer disability;
    private Integer consentToPhoto;
    private Integer consentToMHealth;
    private Integer cat;
    private Integer youngMumGroup;
    private String email;
    private String reasonForNotReachingOLevel;
    private String refererName;
    private String OINumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step7);
        pfirstName = (EditText) findViewById(R.id.pfirstName);
        pmobileNumber = (EditText) findViewById(R.id.pmobileNumber);
        plastName = (EditText) findViewById(R.id.plastName);
        pgender = (Spinner) findViewById(R.id.pgender);
        relationship = (Spinner) findViewById(R.id.relationship);
        Intent intent = getIntent();
        OINumber = intent.getStringExtra("OINumber");
        refererName = intent.getStringExtra("refererName");
        reasonForNotReachingOLevel = intent.getStringExtra("reasonForNotReachingOLevel");
        email = intent.getStringExtra("email");
        consentToPhoto = intent.getIntExtra("consentToPhoto", 0);
        consentToMHealth = intent.getIntExtra("consentToMHealth", 0);
        cat = intent.getIntExtra("cat", 0);
        youngMumGroup = intent.getIntExtra("youngMumGroup", 0);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        next = (Button) findViewById(R.id.btn_save);
        disabilityCategorys = intent.getStringArrayListExtra("disabilityCategorys");
        disability = intent.getIntExtra("disability", 0);
        hivStatusKnown = intent.getIntExtra("hivStatusKnown", 0);
        transmissionMode = intent.getIntExtra("transmissionMode", 0);
        hIVDisclosureLocation = intent.getIntExtra("hIVDisclosureLocation", 0);
        dateTested = intent.getStringExtra("dateTested");
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
        mobileNumber = intent.getStringExtra("mobileNumber");
        ownerName = intent.getStringExtra("ownerName");
        secondaryMobileNumber = intent.getStringExtra("secondaryMobileNumber");
        secondaryMobileOwnerName = intent.getStringExtra("secondaryMobileOwnerName");
        mobileOwner = intent.getIntExtra("mobileOwner", 0);
        ownSecondaryMobile = intent.getIntExtra("ownSecondaryMobile", 0);
        mobileOwnerRelation = intent.getStringExtra("mobileOwnerRelation");
        secondaryMobileownerRelation = intent.getStringExtra("secondaryMobileownerRelation");
        ArrayAdapter<Gender> genderArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Gender.values());
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pgender.setAdapter(genderArrayAdapter);
        genderArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Relationship> relationshipArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Relationship.getAll());
        relationshipArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationship.setAdapter(relationshipArrayAdapter);
        relationshipArrayAdapter.notifyDataSetChanged();
        if(itemID != null){
            item = Patient.findById(itemID);
            pfirstName.setText(item.pfirstName);
            plastName.setText(item.plastName);
            pmobileNumber.setText(item.pmobileNumber != null ? item.pmobileNumber : "");
            int i = 0;
            for(Gender m : Gender.values()){
                if(item.pgender != null  && item.pgender.equals(pgender.getItemAtPosition(i))){
                    pgender.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Relationship m : Relationship.getAll()){
                if(item.relationship != null  && item.relationship.equals(relationship.getItemAtPosition(i))){
                    relationship.setSelection(i, true);
                    break;
                }
                i++;
            }
        }else{
            item = new Patient();
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
        Intent intent = new Intent(PatientRegStep7Activity.this, PatientRegStep6Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            //if(validate(fields)){
            save();
                    Intent intent = new Intent(PatientRegStep7Activity.this, PatientListActivity.class);
                    intent.putExtra(AppUtil.DETAILS_ID, itemID);
                    startActivity(intent);
                    finish();

            //}

        }
    }

    public void save(){
        String patientId = UUIDGen.generateUUID();
        if(itemID != null){
            item.id = itemID;
            item.dateModified = new Date();
        }else{
            item.id = patientId;
            item.dateCreated = new Date();
        }
        item.pushed = 1;
        item.address = address;
        item.address1 = address1;
        item.cat = YesNo.get(cat);
        item.consentToMHealth = YesNo.get(consentToMHealth);
        item.consentToPhoto = YesNo.get(consentToPhoto);
        item.oINumber = OINumber;
        /*if( ! dateJoined.isEmpty()){
            item.dateJoined = DateUtil.getDateFromString(dateJoined);
        }
        if( ! dateTested.isEmpty()){
            item.dateTested = DateUtil.getDateFromString(dateTested);
        }*/
        item.dateOfBirth = DateUtil.getDateFromString(dateOfBirth);
        item.disability = YesNo.get(disability);
        item.education = Education.getItem(education);
        item.educationLevel = EducationLevel.getItem(educationLevel);
        item.email = email;
        item.firstName = firstName;
        item.gender = Gender.get(gender);
        item.hIVDisclosureLocation = HIVDisclosureLocation.get(hIVDisclosureLocation);
        item.hivStatusKnown = YesNo.get(hivStatusKnown);
        item.lastName = lastName;
        item.middleName = middleName;
        item.mobileNumber = mobileNumber;
        item.mobileOwner = YesNo.get(mobileOwner);
        item.mobileOwnerRelation = Relationship.getItem(mobileOwnerRelation);
        item.ownerName = ownerName;
        item.ownSecondaryMobile = YesNo.get(ownSecondaryMobile);
        item.pfirstName = pfirstName.getText().toString();
        item.pgender = (Gender) pgender.getSelectedItem();
        item.plastName = plastName.getText().toString();
        item.pmobileNumber = pmobileNumber.getText().toString();
        item.primaryClinic = Facility.getItem(primaryClinic);
        item.referer = Referer.getItem(referer);
        item.relationship = (Relationship) relationship.getSelectedItem();
        item.secondaryMobileNumber = secondaryMobileNumber;
        item.secondaryMobileOwnerName = secondaryMobileOwnerName;
        item.secondaryMobileownerRelation = Relationship.getItem(secondaryMobileownerRelation);
        item.supportGroup = SupportGroup.getItem(supportGroup);
        item.transmissionMode = TransmissionMode.get(transmissionMode);
        item.youngMumGroup = YesNo.get(youngMumGroup);
        item.refererName = refererName;
        /*if(! reasonForNotReachingOLevel.isEmpty()){
            item.reasonForNotReachingOLevel = ReasonForNotReachingOLevel.getItem(reasonForNotReachingOLevel);
        }*/
        item.save();
        if(itemID != null){
            for(PatientDisabilityCategoryContract c : PatientDisabilityCategoryContract.findByPatient(Patient.findById(itemID))){
                c.delete();
            }
        }
        for(int i = 0; i < disabilityCategorys.size(); i++){
            PatientDisabilityCategoryContract contract = new PatientDisabilityCategoryContract();
            contract.disabilityCategory = DisabilityCategory.getItem(disabilityCategorys.get(i));
            if(itemID != null){
                contract.patient = Patient.findById(itemID);
            }else{
                contract.patient = Patient.findById(patientId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
    }

}
