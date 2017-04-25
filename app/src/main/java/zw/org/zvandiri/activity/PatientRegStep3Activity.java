package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.HIVDisclosureLocation;
import zw.org.zvandiri.business.domain.util.TransmissionMode;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;
import java.util.Date;

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
    private Patient holder;
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
        setContentView(R.layout.activity_patient_reg_step3);
        Intent intent = getIntent();
        holder = (Patient) intent.getSerializableExtra("patient");
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
        if(holder != null){
            address.setText(holder.address);
            address1.setText(holder.address1);
            int i = 0;
            for(Facility m : Facility.getAll()){
                if(holder.primaryClinic != null  && holder.primaryClinic.equals(primaryClinic.getItemAtPosition(i))){
                    primaryClinic.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(SupportGroup m : SupportGroup.getAll()){
                if(holder.supportGroup != null  && holder.supportGroup.equals(supportGroup.getItemAtPosition(i))){
                    supportGroup.setSelection(i, true);
                    break;
                }
                i++;
            }
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
        holder.address = address.getText().toString();
        holder.address1 = address1.getText().toString();
        if(primaryClinic.getSelectedItem() != null){
            holder.primaryClinic = (Facility) primaryClinic.getSelectedItem();
        }
        if(supportGroup.getSelectedItem() != null){
            holder.supportGroup = (SupportGroup) supportGroup.getSelectedItem();
        }
        holder.hivStatusKnown = hivStatusKnown;
        holder.transmissionMode = transmissionMode;
        holder.dateTested = dateTested;
        holder.hIVDisclosureLocation = hIVDisclosureLocation;
        holder.disabilityCategorys = disabilityCategorys;
        holder.disability = disability;
        intent.putExtra("patient", holder);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            if(validateLocal()){
                Intent intent = new Intent(PatientRegStep3Activity.this, PatientRegStep4Activity.class);
                holder.address = address.getText().toString();
                holder.address1 = address1.getText().toString();
                if(primaryClinic.getSelectedItem() != null){
                    holder.primaryClinic = (Facility) primaryClinic.getSelectedItem();
                }
                if(supportGroup.getSelectedItem() != null){
                    holder.supportGroup = (SupportGroup) supportGroup.getSelectedItem();
                }
                holder.dateJoined = dateJoined;
                holder.education = education;
                holder.educationLevel = educationLevel;
                holder.referer = referer;
                holder.reasonForNotReachingOLevel = reasonForNotReachingOLevel;
                holder.refererName = refererName;
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
