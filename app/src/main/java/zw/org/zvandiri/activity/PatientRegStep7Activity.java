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
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 4/4/2017.
 */
public class PatientRegStep7Activity extends BaseActivity implements View.OnClickListener {

    private EditText pfirstName;
    private EditText pmobileNumber;
    private EditText plastName;
    private LinearLayout container;
    private Spinner selfPrimaryCareGiver;
    private Spinner pgender;
    private Button next;
    private Spinner relationship;
    private EditText[] fields;
    private String itemID;
    private Patient item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step7);
        pfirstName = (EditText) findViewById(R.id.pfirstName);
        pmobileNumber = (EditText) findViewById(R.id.pmobileNumber);
        plastName = (EditText) findViewById(R.id.plastName);
        pgender = (Spinner) findViewById(R.id.pgender);
        relationship = (Spinner) findViewById(R.id.relationship);
        container = (LinearLayout) findViewById(R.id.container);
        selfPrimaryCareGiver = (Spinner) findViewById(R.id.selfPrimaryCareGiver);
        next = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        item = (Patient) intent.getSerializableExtra("patient");
        Log.d("Patient", AppUtil.createGson().toJson(item));
        ArrayAdapter<Gender> genderArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Gender.values());
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pgender.setAdapter(genderArrayAdapter);
        genderArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Relationship> relationshipArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Relationship.getAll());
        relationshipArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationship.setAdapter(relationshipArrayAdapter);
        relationshipArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<YesNo> yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selfPrimaryCareGiver.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        selfPrimaryCareGiver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (selfPrimaryCareGiver.getSelectedItem().equals(YesNo.NO)) {
                    container.setVisibility(View.VISIBLE);
                } else {
                    container.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (item.selfPrimaryCareGiver != null) {
            pfirstName.setText(item.pfirstName);
            plastName.setText(item.plastName);
            pmobileNumber.setText(item.pmobileNumber != null ? item.pmobileNumber : "");
            int i = 0;
            for (Gender m : Gender.values()) {
                if (item.pgender != null && item.pgender.equals(pgender.getItemAtPosition(i))) {
                    pgender.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for (Relationship m : Relationship.getAll()) {
                if (item.relationship != null && item.relationship.equals(relationship.getItemAtPosition(i))) {
                    relationship.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add Demographic Details Step 7 of 7 "));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onBackPressed() {
        Intent intent = new Intent(PatientRegStep7Activity.this, PatientRegStep3Activity.class);
        intent.putExtra("patient", item);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == next.getId()) {
            Long id = save();
            Intent intent = null;
            if(item.hei != null && item.hei.equals(YesNo.YES)){
                intent = new Intent(PatientRegStep7Activity.this, HeuMotherDetailsActivity.class);
                intent.putExtra(AppUtil.ID, id);
            }else{

            }
            intent = new Intent(this, PatientListActivity.class);
            startActivity(intent);
            finish();

        }
    }

    public Long save() {
        String patientId = UUIDGen.generateUUID();
        item.pfirstName = pfirstName.getText().toString();
        item.pgender = (Gender) pgender.getSelectedItem();
        item.plastName = plastName.getText().toString();
        item.pmobileNumber = pmobileNumber.getText().toString();
        item.id = patientId;
        item.selfPrimaryCareGiver = (YesNo) selfPrimaryCareGiver.getSelectedItem();
        item.pushed = 1;
        if (item.primaryClinicId != null) {
            item.primaryClinic = Facility.getItem(item.primaryClinicId);
        }
        if (item.supportGroupId != null) {
            item.supportGroup = SupportGroup.getItem(item.supportGroupId);
        }
        if (item.mobileOwnerRelationId != null) {
            item.mobileOwnerRelation = Relationship.getItem(item.mobileOwnerRelationId);
        }
        if (item.secondaryMobileownerRelationId != null) {
            item.secondaryMobileownerRelation = Relationship.getItem(item.secondaryMobileownerRelationId);
        }
        if (item.reasonForNotReachingOLevelId != null) {
            item.reasonForNotReachingOLevel = ReasonForNotReachingOLevel.getItem(item.reasonForNotReachingOLevelId);
        }
        if (item.educationId != null) {
            item.education = Education.getItem(item.educationId);
        }
        if (item.educationLevelId != null) {
            item.educationLevel = (EducationLevel.getItem(item.educationLevelId));
        }
        if (item.referrerId != null) {
            item.referer = Referer.getItem(item.referrerId);
        }
        item.save();
        if (item.disabilityCategorysId != null) {
            for (int i = 0; i < item.disabilityCategorysId.size(); i++) {
                PatientDisabilityCategoryContract contract = new PatientDisabilityCategoryContract();
                contract.disabilityCategory = DisabilityCategory.getItem(item.disabilityCategorysId.get(i));
                contract.patient = Patient.findById(patientId);
                contract.id = UUIDGen.generateUUID();
                contract.save();
            }
        }
        return item.getId();

    }
}
