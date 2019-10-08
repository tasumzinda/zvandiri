package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.CareLevel;
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.domain.util.Reason;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.ArrayList;
import java.util.Date;

public class PatientContactActivityStep2 extends BaseActivity implements View.OnClickListener {


    private Spinner careLevel;
    private ListView stable;
    private ListView enhanced;
    private TextView stableLabel;
    private TextView enhancedLabel;
    private ListView assessment;
    private String itemID;
    private String id;
    private String name;
    private Button next;
    private Contact c;
    private ArrayAdapter<Stable> stableArrayAdapter;
    private ArrayAdapter<Enhanced> enhancedArrayAdapter;
    private ArrayAdapter<Assessment> assessmentArrayAdapter;
    private Spinner actionTaken;
    private ArrayAdapter<zw.org.zvandiri.business.domain.ActionTaken> actionTakenArrayAdapter;
    private Contact holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_contact_activity_step2);
        Intent intent = getIntent();
        holder = (Contact) intent.getSerializableExtra("contact");
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        careLevel = (Spinner) findViewById(R.id.careLevel);
        stable = (ListView) findViewById(R.id.stable);
        enhanced = (ListView) findViewById(R.id.enhanced);
        assessment = (ListView) findViewById(R.id.assessment);
        stableLabel = (TextView) findViewById(R.id.stableLabel);
        enhancedLabel = (TextView) findViewById(R.id.enhancedLabel);
        actionTaken = (Spinner) findViewById(R.id.actionTaken);
        next = (Button) findViewById(R.id.btn_next);
        next.setOnClickListener(this);
        ArrayAdapter<CareLevel> careLevelArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CareLevel.values());
        careLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        careLevel.setAdapter(careLevelArrayAdapter);
        careLevelArrayAdapter.notifyDataSetChanged();
        assessmentArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Assessment.getAll());
        assessment.setAdapter(assessmentArrayAdapter);
        assessmentArrayAdapter.notifyDataSetChanged();
        stableArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Stable.getAll());
        stable.setAdapter(stableArrayAdapter);
        stableArrayAdapter.notifyDataSetChanged();
        enhancedArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Enhanced.getAll());
        enhanced.setAdapter(enhancedArrayAdapter);
        enhancedArrayAdapter.notifyDataSetChanged();
        careLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (careLevel.getSelectedItem().equals(CareLevel.STABLE)) {
                    stable.setVisibility(View.VISIBLE);
                    stableLabel.setVisibility(View.VISIBLE);
                    enhanced.setVisibility(View.GONE);
                    enhancedLabel.setVisibility(View.GONE);
                } else if (careLevel.getSelectedItem().equals(CareLevel.ENHANCED)) {
                    enhanced.setVisibility(View.VISIBLE);
                    enhancedLabel.setVisibility(View.VISIBLE);
                    stable.setVisibility(View.GONE);
                    stableLabel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        assessment.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assessment.setItemsCanFocus(false);
        stable.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        stable.setItemsCanFocus(false);
        enhanced.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        enhanced.setItemsCanFocus(false);
        actionTakenArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, zw.org.zvandiri.business.domain.ActionTaken.getAll());
        actionTakenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionTaken.setAdapter(actionTakenArrayAdapter);
        actionTakenArrayAdapter.notifyDataSetChanged();
        if(itemID != null && ! itemID.isEmpty()){
            c = Contact.findById(itemID);
            int i = 0;
            for (CareLevel m : CareLevel.values()) {
                if (c.careLevel != null && c.careLevel.equals(careLevel.getItemAtPosition(i))) {
                    careLevel.setSelection(i, true);
                    break;
                }
                i++;
            }
            ArrayList<Stable> stables = (ArrayList<Stable>) Stable.findByContact(Contact.findById(itemID));
            int count = stableArrayAdapter.getCount();
            for(i = 0; i < count; i++){
                Stable current = stableArrayAdapter.getItem(i);
                if(stables.contains(current)){
                    stable.setItemChecked(i, true);
                }
            }
            ArrayList<Enhanced> enhanceds = (ArrayList<Enhanced>) Enhanced.findByContact(Contact.findById(itemID));
            count = enhancedArrayAdapter.getCount();
            for(i = 0; i < count; i++){
                Enhanced current = enhancedArrayAdapter.getItem(i);
                if(enhanceds.contains(current)){
                    enhanced.setItemChecked(i, true);
                }
            }
            ArrayList<Assessment> assessments = (ArrayList<Assessment>) Assessment.findByContact(Contact.findById(itemID));
            count = assessmentArrayAdapter.getCount();
            for(i = 0; i < count; i++){
                Assessment current = assessmentArrayAdapter.getItem(i);
                if(assessments.contains(current)){
                    assessment.setItemChecked(i, true);
                }
            }
            i = 0;
            for (ActionTaken m : ActionTaken.getAll()) {
                if (c.actionTaken != null && c.actionTaken.equals(actionTaken.getItemAtPosition(i))) {
                    actionTaken.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Contact - Step 2"));
        }else if(holder.careLevel != null){
            int i = 0;
            for (CareLevel m : CareLevel.values()) {
                if (holder.careLevel != null && holder.careLevel.equals(careLevel.getItemAtPosition(i))) {
                    careLevel.setSelection(i, true);
                    break;
                }
                i++;
            }
            if(holder.stableId != null){
                ArrayList<String> list = (ArrayList<String>) holder.stableId;
                int stableCount = stableArrayAdapter.getCount();
                for(i = 0; i < stableCount; i++){
                    Stable current = stableArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        stable.setItemChecked(i, true);
                    }
                }
            }


            if(holder.enhancedId != null){
                ArrayList<String> list = (ArrayList<String>) holder.enhancedId;
                int enhancedCount = enhancedArrayAdapter.getCount();
                for(i = 0; i < enhancedCount; i++){
                    Enhanced current = enhancedArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        enhanced.setItemChecked(i, true);
                    }
                }
            }

            if(holder.assessmentId != null){
                ArrayList<String> list = (ArrayList<String>) holder.assessmentId;
                int count = assessmentArrayAdapter.getCount();
                for(i = 0; i < count; i++){
                    Assessment current = assessmentArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        assessment.setItemChecked(i, true);
                    }
                }
            }



            i = 0;
            for (ActionTaken m : ActionTaken.getAll()) {
                if (holder.actionTakenId != null && holder.actionTakenId.equals(((ActionTaken) actionTaken.getItemAtPosition(i)).id)) {
                    actionTaken.setSelection(i, true);
                    break;
                }
                i++;
            }
            c = new Contact();
            setSupportActionBar(createToolBar("Add Contact - Step 2"));
        }else{
            c = new Contact();
            setSupportActionBar(createToolBar("Add Contact - Step 2"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onBackPressed(){
        Intent intent = new Intent(PatientContactActivityStep2.this, PatientContactActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        holder.careLevel = (CareLevel) careLevel.getSelectedItem();
        holder.actionTakenId = ((ActionTaken) actionTaken.getSelectedItem()).id;
        if(careLevel.getSelectedItem().equals(CareLevel.ENHANCED)){
            holder.enhancedId = getEnhanceds();
        }else{
            holder.stableId = getStables();
        }
        intent.putExtra("contact", holder);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            save();
        }
    }

    public void save(){
        String contactId = UUIDGen.generateUUID();
        if(itemID != null){
            c.id = itemID;
            c.dateModified = new Date();
            c.isNew = 1;
        }else{
            c.id = contactId;
            c.dateCreated = new Date();
            c.isNew = 1;
        }
        c.careLevel = (CareLevel) careLevel.getSelectedItem();
        c.followUp = holder.followUp;
        if(holder.internalReferralId != null){
            c.internalReferral = InternalReferral.getItem(holder.internalReferralId);
        }
        c.contactDate = holder.contactDate;
        if(holder.externalReferralId != null){
            c.externalReferral = ExternalReferral.getItem(holder.externalReferralId);
        }
        if(holder.positionId != null){
            c.position = Position.getItem(holder.positionId);
        }
        c.reason = holder.reason;
        c.lastClinicAppointmentDate = holder.lastClinicAppointmentDate;
        if(holder.locationId != null){
            c.location = Location.getItem(holder.locationId);
        }
        c.patient =Patient.getById(id);
        c.pushed = 0;
        c.actionTaken = (ActionTaken) actionTaken.getSelectedItem();
        c.attendedClinicAppointment = holder.attendedClinicAppointment;
        c.save();
        if(itemID != null){
            deleteMultipleSelections();
        }
        if(careLevel.getSelectedItem().equals(CareLevel.ENHANCED)){
            for(int i = 0; i < getEnhanceds().size(); i++){
                ContactEnhancedContract contract = new ContactEnhancedContract();
                contract.enhanced = Enhanced.getItem(getEnhanceds().get(i));
                if(itemID != null){
                    contract.contact = Contact.findById(itemID);
                }else{
                    contract.contact = Contact.findById(contactId);
                }
                contract.id = UUIDGen.generateUUID();
                contract.save();
            }
        }else if(careLevel.getSelectedItem().equals(CareLevel.STABLE)){
            for(int i = 0; i <  getStables().size(); i++){
                ContactStableContract contract = new ContactStableContract();
                contract.stable = Stable.getItem(getStables().get(i));
                if(itemID != null){
                    contract.contact = Contact.findById(itemID);
                }else{
                    contract.contact = Contact.findById(contactId);
                }
                contract.id = UUIDGen.generateUUID();
                contract.save();
            }
        }
        for(int i = 0; i <  getAssessments().size(); i++){
            ContactAssessmentContract contract = new ContactAssessmentContract();
            contract.assessment = Assessment.getItem(getAssessments().get(i));
            if(itemID != null){
                contract.contact = Contact.findById(itemID);
            }else{
                contract.contact = Contact.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent;
        if(c.actionTaken.equals(ActionTaken.findByName("External Referral"))){
            intent = new Intent(PatientContactActivityStep2.this, PatientReferralListActivity.class);
        }else{
            intent = new Intent(PatientContactActivityStep2.this, PatientContactListActivity.class);
        }
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    private ArrayList<String> getEnhanceds(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < enhanced.getCount(); i++){
            if(enhanced.isItemChecked(i)){
                a.add(enhancedArrayAdapter.getItem(i).id);
            }else{
                a.remove(enhancedArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }
    private ArrayList<String> getStables(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < stable.getCount(); i++){
            if(stable.isItemChecked(i)){
                a.add(stableArrayAdapter.getItem(i).id);
            }else{
                a.remove(stableArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }

    private ArrayList<String> getAssessments(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < assessment.getCount(); i++){
            if(assessment.isItemChecked(i)){
                a.add(assessmentArrayAdapter.getItem(i).id);
            }else{
                a.remove(assessmentArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }

    public void deleteMultipleSelections(){
        for(ContactStableContract c : ContactStableContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
        }
        for(ContactEnhancedContract c: ContactEnhancedContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
        }
        for(ContactAssessmentContract c: ContactAssessmentContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
        }
    }
}
