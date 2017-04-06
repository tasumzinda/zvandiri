package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.DisabilityCategory;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.ReasonForNotReachingOLevel;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;

/**
 * Created by User on 4/4/2017.
 */
public class PatientRegStep5ContActivity extends BaseActivity implements View.OnClickListener{

    private Spinner disability;
    private TextView disabilityLabel;
    private ListView disabilityCategorys;
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
    private String email;
    ArrayAdapter<DisabilityCategory> disabilityCategorysArrayAdapter;
    private String reasonForNotReachingOLevel;
    private String refererName;
    private String OINumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step5_cont);
        //Log.d("reason", "1. " + ReasonForNotReachingOLevel.getItem(reasonForNotReachingOLevel).name);
        Intent intent = getIntent();
        OINumber = intent.getStringExtra("OINumber");
        refererName = intent.getStringExtra("refererName");
        reasonForNotReachingOLevel = intent.getStringExtra("reasonForNotReachingOLevel");
        email = intent.getStringExtra("email");
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
        disability = (Spinner) findViewById(R.id.disability);
        disabilityLabel = (TextView) findViewById(R.id.disabilityLabel);
        disabilityCategorys = (ListView) findViewById(R.id.disabilityCategorys);
        ArrayAdapter<YesNo> disabilityArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        disabilityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disability.setAdapter(disabilityArrayAdapter);
        disabilityArrayAdapter.notifyDataSetChanged();
        disabilityCategorys.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        disabilityCategorys.setItemsCanFocus(false);
        disability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(disability.getSelectedItem().equals(YesNo.YES)){
                    disabilityCategorys.setVisibility(View.VISIBLE);
                    disabilityCategorysArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.check_box_item, DisabilityCategory.getAll());
                    disabilityCategorys.setAdapter(disabilityCategorysArrayAdapter);
                    disabilityCategorysArrayAdapter.notifyDataSetChanged();
                    disabilityLabel.setVisibility(View.VISIBLE);
                }else{
                    disabilityLabel.setVisibility(View.GONE);
                    disabilityCategorys.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemID != null){
            item = Patient.findById(itemID);
            int i = 0;
            for(YesNo m : YesNo.values()){
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
        Intent intent = new Intent(PatientRegStep5ContActivity.this, PatientRegStep1Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            Intent intent = new Intent(PatientRegStep5ContActivity.this, PatientRegStep6Activity.class);
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
            intent.putExtra("disability", ((YesNo) disability.getSelectedItem()).getCode());
            intent.putExtra("disabilityCategorys", getDisabilityCategorys());
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

    private ArrayList<String> getDisabilityCategorys(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < disabilityCategorys.getCount(); i++){
            if(disabilityCategorys.isItemChecked(i)){
                a.add(disabilityCategorysArrayAdapter.getItem(i).id);
            }else{
                a.remove(disabilityCategorysArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }
}
