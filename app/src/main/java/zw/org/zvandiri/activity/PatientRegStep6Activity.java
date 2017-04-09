package zw.org.zvandiri.activity;

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
public class PatientRegStep6Activity extends BaseActivity implements View.OnClickListener{

    private Spinner consentToPhoto;
    private Spinner consentToMHealth;
    private Spinner cat;
    private Spinner youngMumGroup;
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
    private String email;
    private String reasonForNotReachingOLevel;
    private String refererName;
    private String OINumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step6);
        Intent intent = getIntent();
        OINumber = intent.getStringExtra("OINumber");
        refererName = intent.getStringExtra("refererName");
        reasonForNotReachingOLevel = intent.getStringExtra("reasonForNotReachingOLevel");
        email = intent.getStringExtra("email");
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
        consentToPhoto = (Spinner) findViewById(R.id.consentToPhoto);
        consentToMHealth = (Spinner) findViewById(R.id.consentToMHealth);
        cat = (Spinner) findViewById(R.id.cat);
        youngMumGroup = (Spinner) findViewById(R.id.youngMumGroup);
        ArrayAdapter<YesNo> consentToPhotoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        consentToPhotoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        consentToPhoto.setAdapter(consentToPhotoArrayAdapter);
        consentToPhotoArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<YesNo> consentToMHealthArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        consentToMHealthArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        consentToMHealth.setAdapter(consentToMHealthArrayAdapter);
        consentToMHealthArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<YesNo> catArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        catArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catArrayAdapter);
        catArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<YesNo> youngMumGroupArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        youngMumGroupArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        youngMumGroup.setAdapter(youngMumGroupArrayAdapter);
        youngMumGroupArrayAdapter.notifyDataSetChanged();
        if(itemID != null){
            item = Patient.findById(itemID);
            int i = 0;
            /*for(YesNo m : YesNo.values()){
                if(item.disability != null  && item.disability.equals(disability.getItemAtPosition(i))){
                    disability.setSelection(i, true);
                    break;
                }
                i++;
            }
            ArrayList<DisabilityCategory> disabilityCategorysList = (ArrayList<DisabilityCategory>) DisabilityCategory.findByPatient(Patient.findById(itemID));
            int disabilityCategorysCount = disabilityCategorysArrayAdapter.getCount();
            for(i = 0; i < disabilityCategorysCount; i++){
                DisabilityCategory current = disabilityCategorysArrayAdapter.getItem(i);
                if(disabilityCategorysList.contains(current)){
                    disabilityCategorys.setItemChecked(i, true);
                }
            }*/
        }else{
            item = new Patient();
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add Zvandiri Details Final"));
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
        Intent intent = new Intent(PatientRegStep6Activity.this, PatientRegStep1Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            Intent intent = new Intent(PatientRegStep6Activity.this, PatientListActivity.class);
            save();
            /*intent.putExtra(AppUtil.DETAILS_ID, itemID);
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
            intent.putExtra("hivStatusKnown", hivStatusKnown);
            intent.putExtra("transmissionMode", transmissionMode);
            intent.putExtra("dateTested", dateTested);
            intent.putExtra("hIVDisclosureLocation", hIVDisclosureLocation);
            intent.putExtra("disability", disability);
            intent.putExtra("disabilityCategorys", disabilityCategorys);
            intent.putExtra("consentToPhoto", ((YesNo) consentToPhoto.getSelectedItem()).getCode());
            intent.putExtra("consentToMHealth", ((YesNo) consentToMHealth.getSelectedItem()).getCode());
            intent.putExtra("cat", ((YesNo) cat.getSelectedItem()).getCode());
            intent.putExtra("youngMumGroup", ((YesNo) youngMumGroup.getSelectedItem()).getCode());
            intent.putExtra("refererName", refererName);
            intent.putExtra("reasonForNotReachingOLevel", reasonForNotReachingOLevel);
            intent.putExtra("OINumber", OINumber);*/
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
        item.cat = (YesNo) cat.getSelectedItem();
        item.consentToMHealth = (YesNo) consentToMHealth.getSelectedItem();
        item.consentToPhoto = (YesNo) consentToPhoto.getSelectedItem();
        item.oINumber = OINumber;
        if( checkDateFormat(dateJoined)){
            item.dateJoined = DateUtil.getDateFromString(dateJoined);
        }
        if( checkDateFormat(dateTested)){
            item.dateTested = DateUtil.getDateFromString(dateTested);
        }
        item.dateOfBirth = DateUtil.getDateFromString(dateOfBirth);
        item.disability = YesNo.get(disability);
        if(education != null && ! education.isEmpty()){
            item.education = Education.getItem(education);
        }
        if(educationLevel != null && ! educationLevel.isEmpty()){
            item.educationLevel = EducationLevel.getItem(educationLevel);
        }
        item.email = email;
        item.firstName = firstName;
        item.gender = Gender.get(gender);
        item.hIVDisclosureLocation = HIVDisclosureLocation.get(hIVDisclosureLocation);
        item.hivStatusKnown = YesNo.get(hivStatusKnown);
        item.lastName = lastName;
        item.middleName = middleName;
        item.mobileNumber = mobileNumber;
        item.mobileOwner = YesNo.get(mobileOwner);
        if(mobileOwnerRelation != null && ! mobileOwnerRelation.isEmpty()){
            item.mobileOwnerRelation = Relationship.getItem(mobileOwnerRelation);
        }
        item.ownerName = ownerName;
        item.ownSecondaryMobile = YesNo.get(ownSecondaryMobile);
        if(primaryClinic != null && ! primaryClinic.isEmpty()){
            item.primaryClinic = Facility.getItem(primaryClinic);
        }
        if(referer != null && ! referer.isEmpty()){
            item.referer = Referer.getItem(referer);
        }
        item.secondaryMobileNumber = secondaryMobileNumber;
        item.secondaryMobileOwnerName = secondaryMobileOwnerName;
        if(secondaryMobileownerRelation != null && ! secondaryMobileownerRelation.isEmpty()){
            item.secondaryMobileownerRelation = Relationship.getItem(secondaryMobileownerRelation);
        }
        if(supportGroup != null && ! supportGroup.isEmpty()){
            item.supportGroup = SupportGroup.getItem(supportGroup);
        }
        item.transmissionMode = TransmissionMode.get(transmissionMode);
        item.youngMumGroup = (YesNo) youngMumGroup.getSelectedItem();
        item.refererName = refererName;
        if(reasonForNotReachingOLevel != null && ! reasonForNotReachingOLevel.isEmpty()){
            item.reasonForNotReachingOLevel = ReasonForNotReachingOLevel.getItem(reasonForNotReachingOLevel);
        }
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
