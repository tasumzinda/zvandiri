package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.ContactActionTakenContract;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.ActionTaken;
import zw.org.zvandiri.business.domain.ContactStableContract;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.ArrayList;
import java.util.Date;

public class PatientContactActivityFinal extends BaseActivity implements View.OnClickListener{

    private Spinner actionTaken;
    private String itemID;
    private String id;
    private String name;
    private String contactDate;
    private String subjective;
    private String objective;
    private String plan;
    private String location;
    private String externalReferral;
    private String internalReferral;
    private Integer reason;
    private Integer followUp;
    private Integer careLevel;
    private ArrayList<String> assessments;
    private ArrayList<String> enhanceds;
    private ArrayList<String> stables;
    private String position;
    private Button save;
    private Contact c;
    private ArrayAdapter<zw.org.zvandiri.business.domain.ActionTaken> actionTakenArrayAdapter;
    private String lastClinicAppointmentDate;
    private Integer attendedClinicAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_contact_activity_final);
        Intent intent = getIntent();
        lastClinicAppointmentDate = intent.getStringExtra("lastClinicAppointmentDate");
        attendedClinicAppointment = intent.getIntExtra("attendedClinicAppointment", 0);
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        contactDate = intent.getStringExtra("contactDate");
        subjective = intent.getStringExtra("subjective");
        objective = intent.getStringExtra("objective");
        plan = intent.getStringExtra("plan");
        location = intent.getStringExtra("location");
        externalReferral = intent.getStringExtra("externalReferral");
        internalReferral = intent.getStringExtra("internalReferral");
        reason = intent.getIntExtra("reason", 1);
        followUp = intent.getIntExtra("followUp", 1);
        position = intent.getStringExtra("position");
        careLevel = intent.getIntExtra("careLevel", 1);
        assessments = intent.getStringArrayListExtra("assessments");
        enhanceds = intent.getStringArrayListExtra("enhanceds");
        stables = intent.getStringArrayListExtra("stables");
        actionTaken = (Spinner) findViewById(R.id.actionTaken);
        save = (Button) findViewById(R.id.btn_save);
        actionTakenArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, zw.org.zvandiri.business.domain.ActionTaken.getAll());
        actionTakenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionTaken.setAdapter(actionTakenArrayAdapter);
        actionTakenArrayAdapter.notifyDataSetChanged();
        save.setOnClickListener(this);
        if(itemID != null){
            c = Contact.findById(itemID);
            int i = 0;
            for (ActionTaken m : ActionTaken.getAll()) {
                if (c.actionTaken != null && c.actionTaken.equals(actionTaken.getItemAtPosition(i))) {
                    actionTaken.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Contact - Final"));
        }else{
            c = new Contact();
            setSupportActionBar(createToolBar("Add Contact - Final"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void save(){
        String contactId = UUIDGen.generateUUID();
        if(itemID != null){
            c.id = itemID;
            c.dateModified = new Date();
            c.isNew = false;
        }else{
            c.id = contactId;
            c.dateCreated = new Date();
            c.isNew = true;
        }
        c.careLevel = CareLevel.get(careLevel);
        c.followUp = FollowUp.get(followUp);
        if(internalReferral != null){
            c.internalReferral = InternalReferral.getItem(internalReferral);
        }
        if(contactDate != null){
            c.contactDate = DateUtil.getDateFromString(contactDate);
        }
        if(externalReferral != null){
            c.externalReferral = ExternalReferral.getItem(externalReferral);
        }
        c.location = Location.getItem(location);
        c.objective = objective;
        Patient p = Patient.findById(id);
        c.patient = p;
        c.plan = plan;
        c.position = Position.getItem(position);
        c.reason = Reason.get(reason);
        c.subjective = subjective;
        c.pushed = false;
        c.attendedClinicAppointment = YesNo.get(attendedClinicAppointment);
        c.actionTaken = (ActionTaken) actionTaken.getSelectedItem();
       /* if(lastClinicAppointmentDate != null){
            c.lastClinicAppointmentDate = DateUtil.getDateFromString(lastClinicAppointmentDate);
        }*/
       if(checkDateFormat(lastClinicAppointmentDate)){
           c.lastClinicAppointmentDate = DateUtil.getDateFromString(lastClinicAppointmentDate);
       }
        c.save();
        if(itemID != null){
            for(ContactAssessmentContract c : ContactAssessmentContract.findByContact(Contact.findById(itemID))){
                c.delete();
            }
            deleteCareLevelSelections();
        }
        for(int i = 0; i < assessments.size(); i++){
            ContactAssessmentContract contract = new ContactAssessmentContract();
            contract.assessment = Assessment.getItem(assessments.get(i));
            if(itemID != null){
                contract.contact = Contact.findById(itemID);
            }else{
                contract.contact = Contact.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        if(CareLevel.get(careLevel).equals(CareLevel.ENHANCED)){
            for(int i = 0; i < enhanceds.size(); i++){
                ContactEnhancedContract contract = new ContactEnhancedContract();
                contract.enhanced = Enhanced.getItem(enhanceds.get(i));
                if(itemID != null){
                    contract.contact = Contact.findById(itemID);
                }else{
                    contract.contact = Contact.findById(contactId);
                }
                contract.id = UUIDGen.generateUUID();
                contract.save();
            }
        }else if(CareLevel.get(careLevel).equals(CareLevel.STABLE)){
            for(int i = 0; i <  stables.size(); i++){
                ContactStableContract contract = new ContactStableContract();
                contract.stable = Stable.getItem( stables.get(i));
                if(itemID != null){
                    contract.contact = Contact.findById(itemID);
                }else{
                    contract.contact = Contact.findById(contactId);
                }
                contract.id = UUIDGen.generateUUID();
                contract.save();
            }
        }
        p.pushed = 1;
        p.save();
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent = new Intent(PatientContactActivityFinal.this, PatientContactListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(PatientContactActivityFinal.this, PatientContactActivityStep3.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == save.getId()){
            save();
        }
    }

    public void deleteCareLevelSelections(){
        for(ContactStableContract c : ContactStableContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
            Log.d("Deleted stables", c.stable.name);
        }
        for(ContactEnhancedContract c: ContactEnhancedContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
            Log.d("Deleted enhanceds", c.enhanced.name);
        }
    }
}
