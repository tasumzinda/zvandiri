package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.PatientChangeEvent;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

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
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add Zvandiri Details Step 6 of 7  "));
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
            Intent intent = new Intent(PatientRegStep6Activity.this, PatientRegStep7Activity.class);
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
            intent.putExtra("OINumber", OINumber);
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
