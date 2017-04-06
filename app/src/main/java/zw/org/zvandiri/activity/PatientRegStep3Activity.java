package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;

/**
 * Created by User on 4/3/2017.
 */
public class PatientRegStep3Activity extends BaseActivity implements View.OnClickListener{

    private EditText address;
    private EditText address1;
    private Spinner province;
    private Spinner district;
    private Spinner primaryClinic;
    private Spinner supportGroupDistrict;
    private Spinner supportGroup;
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
    private String email;
    private String OINumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step3);
        Intent intent = getIntent();
        OINumber = intent.getStringExtra("OINumber");
        email = intent.getStringExtra("email");
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
        address = (EditText) findViewById(R.id.address);
        address1 = (EditText) findViewById(R.id.address1);
        province = (Spinner) findViewById(R.id.province);
        district = (Spinner) findViewById(R.id.district);
        primaryClinic = (Spinner) findViewById(R.id.primaryClinic);
        supportGroupDistrict = (Spinner) findViewById(R.id.supportGroupDistrict);
        supportGroup = (Spinner) findViewById(R.id.supportGroup);
        ArrayAdapter<Province> provinceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Province.getAll());
        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(provinceArrayAdapter);
        provinceArrayAdapter.notifyDataSetChanged();
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<District> districtArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, District.getByProvince((Province) province.getSelectedItem()));
                districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                district.setAdapter(districtArrayAdapter);
                districtArrayAdapter.notifyDataSetChanged();
                ArrayAdapter<District> supportGroupDistrictArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item,District.getByProvince((Province) province.getSelectedItem()));
                supportGroupDistrictArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                supportGroupDistrict.setAdapter(supportGroupDistrictArrayAdapter);
                supportGroupDistrictArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<Facility> primaryClinicArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, Facility.getByDistrict((District) district.getSelectedItem()));
                primaryClinicArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                primaryClinic.setAdapter(primaryClinicArrayAdapter);
                primaryClinicArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        supportGroupDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<SupportGroup> supportGroupArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, SupportGroup.getByDistrict((District) supportGroupDistrict.getSelectedItem()));
                supportGroupArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                supportGroup.setAdapter(supportGroupArrayAdapter);
                supportGroupArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemID != null){
            item = Patient.findById(itemID);
            address.setText(item.address);
            address1.setText(item.address1);
            int i = 0;
            for(Facility m : Facility.getAll()){
                if(item.primaryClinic != null  && item.primaryClinic.equals(primaryClinic.getItemAtPosition(i))){
                    primaryClinic.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(SupportGroup m : SupportGroup.getAll()){
                if(item.supportGroup != null  && item.supportGroup.equals(supportGroup.getItemAtPosition(i))){
                    supportGroup.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add Address Details Step 3 of 7 "));
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
        Intent intent = new Intent(PatientRegStep3Activity.this, PatientRegStep2Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            if(validateLocal()){
                Intent intent = new Intent(PatientRegStep3Activity.this, PatientRegStep4Activity.class);
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
                intent.putExtra("address", address.getText().toString());
                intent.putExtra("address1", address1.getText().toString());
                intent.putExtra("primaryClinic", ((Facility) primaryClinic.getSelectedItem()).id);
                intent.putExtra("OINumber", OINumber);
                if(supportGroup.getSelectedItem() != null){
                    intent.putExtra("supportGroup", ((SupportGroup) supportGroup.getSelectedItem()).id);
                }
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if(address.getText().toString().isEmpty()){
            address.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            address.setError(null);
        }
        return isValid;
    }
}
