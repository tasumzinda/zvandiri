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
import zw.org.zvandiri.toolbox.Log;

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
    private TextView youngMumGroupLabel;
    private Patient holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step6);
        Intent intent = getIntent();
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        holder = (Patient) intent.getSerializableExtra("patient");
        next = (Button) findViewById(R.id.btn_save);
        consentToPhoto = (Spinner) findViewById(R.id.consentToPhoto);
        consentToMHealth = (Spinner) findViewById(R.id.consentToMHealth);
        cat = (Spinner) findViewById(R.id.cat);
        youngMumGroup = (Spinner) findViewById(R.id.youngMumGroup);
        youngMumGroupLabel = (TextView) findViewById(R.id.label);
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
        Gender g = holder.gender;
        if(g.equals(Gender.FEMALE)){
            youngMumGroup.setVisibility(View.VISIBLE);
            youngMumGroupLabel.setVisibility(View.VISIBLE);
        }else{
            youngMumGroup.setVisibility(View.GONE);
            youngMumGroupLabel.setVisibility(View.GONE);
        }
        youngMumGroupArrayAdapter.notifyDataSetChanged();
        if(holder.consentToPhoto != null){
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.consentToPhoto != null && holder.consentToPhoto.equals(consentToPhoto.getItemAtPosition(i))){
                    consentToPhoto.setSelection(i);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.consentToMHealth != null && holder.consentToMHealth.equals(consentToMHealth.getItemAtPosition(i))){
                    consentToMHealth.setSelection(i);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.cat != null && holder.cat.equals(cat.getItemAtPosition(i))){
                    cat.setSelection(i);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.youngMumGroup != null && holder.youngMumGroup.equals(youngMumGroup.getItemAtPosition(i))){
                    youngMumGroup.setSelection(i);
                    break;
                }
                i++;
            }

        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Final"));
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
        Intent intent = new Intent(PatientRegStep6Activity.this, PatientRegStep5ContActivity.class);
        holder.cat = (YesNo) cat.getSelectedItem();
        holder.consentToMHealth = (YesNo) consentToMHealth.getSelectedItem();
        holder.consentToPhoto = (YesNo) consentToPhoto.getSelectedItem();
        if(youngMumGroup.getVisibility() == View.VISIBLE){
            holder.youngMumGroup = (YesNo) youngMumGroup.getSelectedItem();
        }
        intent.putExtra("patient", holder);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            if(validateLocal()){
                Intent intent = new Intent(PatientRegStep6Activity.this, PatientListActivity.class);
                save();
                startActivity(intent);
                finish();
            }

        }
    }

    private boolean validateLocal(){
        boolean isValid = true;
        if(youngMumGroup.getSelectedItem() != null && youngMumGroup.getSelectedItem().equals(YesNo.YES) && holder.dateOfBirth != null && DateUtil.getAge(holder.dateOfBirth) <= 10){
            AppUtil.createShortNotification(this, getResources().getString(R.string.female_too_young));
            isValid = false;
        }
        if(cat.getSelectedItem() != null && cat.getSelectedItem().equals(YesNo.YES) && holder.dateOfBirth != null && DateUtil.getAge(holder.dateOfBirth) <= 10){
            AppUtil.createShortNotification(this, getResources().getString(R.string.cat_too_young));
            isValid = false;
        }
        return isValid;
    }

    public void save(){
        String patientId = UUIDGen.generateUUID();
        holder.id = patientId;
        holder.cat = (YesNo) cat.getSelectedItem();
        holder.consentToMHealth = (YesNo) consentToMHealth.getSelectedItem();
        holder.consentToPhoto = (YesNo) consentToPhoto.getSelectedItem();
        holder.pushed = 1;
        if(youngMumGroup.getVisibility() == View.VISIBLE){
            holder.youngMumGroup = (YesNo) youngMumGroup.getSelectedItem();
        }
        if(holder.primaryClinicId != null){
            holder.primaryClinic = Facility.getItem(holder.primaryClinicId);
        }
        if(holder.supportGroupId != null){
            holder.supportGroup = SupportGroup.getItem(holder.supportGroupId);
        }
        if(holder.mobileOwnerRelationId != null){
            holder.mobileOwnerRelation = Relationship.getItem(holder.mobileOwnerRelationId);
        }
        if(holder.secondaryMobileownerRelationId != null){
            holder.secondaryMobileownerRelation = Relationship.getItem(holder.secondaryMobileownerRelationId);
        }
        if(holder.reasonForNotReachingOLevelId != null){
            holder.reasonForNotReachingOLevel = ReasonForNotReachingOLevel.getItem(holder.reasonForNotReachingOLevelId);
        }
        if(holder.educationId != null){
            holder.education = Education.getItem(holder.educationId);
        }
        if(holder.educationLevelId != null){
            holder.educationLevel = (EducationLevel.getItem(holder.educationLevelId));
        }
        if(holder.referrerId != null){
            holder.referer = Referer.getItem(holder.referrerId);
        }
        holder.save();
        if(holder.disabilityCategorysId != null){
            for(int i = 0; i < holder.disabilityCategorysId.size(); i++){
                PatientDisabilityCategoryContract contract = new PatientDisabilityCategoryContract();
                contract.disabilityCategory = DisabilityCategory.getItem(holder.disabilityCategorysId.get(i));
                contract.patient = Patient.findById(patientId);
                contract.id = UUIDGen.generateUUID();
                contract.save();
            }
        }
        for(DisabilityCategory i : DisabilityCategory.findByPatient(Patient.findById(patientId))){
            Log.d("Diasbility category", AppUtil.createGson().toJson(i));
        }

    }
}
