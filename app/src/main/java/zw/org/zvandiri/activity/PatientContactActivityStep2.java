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
    private String itemID;
    private String id;
    private String name;
    private String contactDate;
    /*private String subjective;
    private String objective;
    private String plan;*/
    private String location;
    private String externalReferral;
    private String internalReferral;
    private Integer reason;
    private Integer followUp;
    private String position;
    private Button next;
    private Contact c;
    private ArrayAdapter<Stable> stableArrayAdapter;
    private ArrayAdapter<Enhanced> enhancedArrayAdapter;
    private ArrayList<String> list;
    private String lastClinicAppointmentDate;
    private Integer attendedClinicAppointment;
    private Spinner actionTaken;
    private ArrayAdapter<zw.org.zvandiri.business.domain.ActionTaken> actionTakenArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_contact_activity_step2);
        Intent intent = getIntent();
        lastClinicAppointmentDate = intent.getStringExtra("lastClinicAppointmentDate");
        attendedClinicAppointment = intent.getIntExtra("attendedClinicAppointment", 0);
        id = intent.getStringExtra(AppUtil.ID);
        Log.d("ID", id);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        contactDate = intent.getStringExtra("contactDate");
        /*subjective = intent.getStringExtra("subjective");
        objective = intent.getStringExtra("objective");
        plan = intent.getStringExtra("plan");*/
        location = intent.getStringExtra("location");
        externalReferral = intent.getStringExtra("externalReferral");
        internalReferral = intent.getStringExtra("internalReferral");
        reason = intent.getIntExtra("reason", 1);
        followUp = intent.getIntExtra("followUp", 1);
        position = intent.getStringExtra("position");
        careLevel = (Spinner) findViewById(R.id.careLevel);
        stable = (ListView) findViewById(R.id.stable);
        enhanced = (ListView) findViewById(R.id.enhanced);
        stableLabel = (TextView) findViewById(R.id.stableLabel);
        enhancedLabel = (TextView) findViewById(R.id.enhancedLabel);
        actionTaken = (Spinner) findViewById(R.id.actionTaken);
        list = new ArrayList<>();
        next = (Button) findViewById(R.id.btn_next);
        next.setOnClickListener(this);
        ArrayAdapter<CareLevel> careLevelArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CareLevel.values());
        careLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        careLevel.setAdapter(careLevelArrayAdapter);
        careLevelArrayAdapter.notifyDataSetChanged();
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
        stable.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        stable.setItemsCanFocus(false);
        enhanced.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        enhanced.setItemsCanFocus(false);
        actionTakenArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, zw.org.zvandiri.business.domain.ActionTaken.getAll());
        actionTakenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionTaken.setAdapter(actionTakenArrayAdapter);
        actionTakenArrayAdapter.notifyDataSetChanged();
        if(itemID != null){
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
            int stableCount = stableArrayAdapter.getCount();
            for(i = 0; i < stableCount; i++){
                Stable current = stableArrayAdapter.getItem(i);
                if(stables.contains(current)){
                    stable.setItemChecked(i, true);
                }
            }
            ArrayList<Enhanced> enhanceds = (ArrayList<Enhanced>) Enhanced.findByContact(Contact.findById(itemID));
            int enhancedCount = enhancedArrayAdapter.getCount();
            for(i = 0; i < enhancedCount; i++){
                Enhanced current = enhancedArrayAdapter.getItem(i);
                if(enhanceds.contains(current)){
                    enhanced.setItemChecked(i, true);
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
        }else{
            c = new Contact();
            setSupportActionBar(createToolBar("Add Contact - Step 2"));
        }

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
        Intent intent = new Intent(PatientContactActivityStep2.this, PatientContactActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
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
            c.isNew = false;
        }else{
            c.id = contactId;
            c.dateCreated = new Date();
            c.isNew = true;
        }
        c.careLevel = (CareLevel) careLevel.getSelectedItem();
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
        c.patient =Patient.getById(id);
        //c.plan = plan;
        c.position = Position.getItem(position);
        c.reason = Reason.get(reason);
        //c.subjective = subjective;
        c.pushed = false;
        c.attendedClinicAppointment = YesNo.get(attendedClinicAppointment);
        c.actionTaken = (ActionTaken) actionTaken.getSelectedItem();
        if(checkDateFormat(lastClinicAppointmentDate)){
            c.lastClinicAppointmentDate = DateUtil.getDateFromString(lastClinicAppointmentDate);
        }
        c.save();
        if(itemID != null){
            /*for(ContactAssessmentContract c : ContactAssessmentContract.findByContact(Contact.findById(itemID))){
                c.delete();
            }*/
            deleteCareLevelSelections();
        }
        /*for(int i = 0; i < assessments.size(); i++){
            ContactAssessmentContract contract = new ContactAssessmentContract();
            contract.assessment = Assessment.getItem(assessments.get(i));
            if(itemID != null){
                contract.contact = Contact.findById(itemID);
            }else{
                contract.contact = Contact.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }*/
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
                contract.stable = Stable.getItem( getStables().get(i));
                if(itemID != null){
                    contract.contact = Contact.findById(itemID);
                }else{
                    contract.contact = Contact.findById(contactId);
                }
                contract.id = UUIDGen.generateUUID();
                contract.save();
            }
        }
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent = new Intent(PatientContactActivityStep2.this, PatientContactListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        Log.d("name", name);
        intent.putExtra(AppUtil.ID, id);
        Log.d("id", id);
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
