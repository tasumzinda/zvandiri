package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.CareLevel;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.ArrayList;
import java.util.Date;

public class PatientContactActivityStep2 extends BaseActivity implements View.OnClickListener {


    private Spinner careLevel;
    private ListView stable;
    private ListView enhanced;
    private TextView stableLabel;
    private TextView enhancedLabel;
    private ListView nonClinicalAssessments;
    private ListView clinicalAssessments;
    private ListView servicesOffered;
    private String itemID;
    private String id;
    private String name;
    private Button next;
    private Contact c;
    private ArrayAdapter<Stable> stableArrayAdapter;
    private ArrayAdapter<Enhanced> enhancedArrayAdapter;
    private ArrayAdapter<Assessment> clinicalAssessmentArrayAdapter;
    private ArrayAdapter<Assessment> nonClinicalAssessmentArrayAdapter;
    private ArrayAdapter<ServiceOffered> serviceOfferedArrayAdapter;
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
        clinicalAssessments = (ListView) findViewById(R.id.clinicalAssessments);
        nonClinicalAssessments = (ListView) findViewById(R.id.nonClinicalAssessments);
        servicesOffered = (ListView) findViewById(R.id.servicesOffered);
        stableLabel = (TextView) findViewById(R.id.stableLabel);
        enhancedLabel = (TextView) findViewById(R.id.enhancedLabel);
        actionTaken = (Spinner) findViewById(R.id.actionTaken);
        next = (Button) findViewById(R.id.btn_next);
        next.setOnClickListener(this);
        ArrayAdapter<CareLevel> careLevelArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CareLevel.values());
        careLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        careLevel.setAdapter(careLevelArrayAdapter);
        careLevelArrayAdapter.notifyDataSetChanged();
        clinicalAssessmentArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Assessment.getAssessmentByType("CLINICAL"));
        clinicalAssessments.setAdapter(clinicalAssessmentArrayAdapter);
        clinicalAssessmentArrayAdapter.notifyDataSetChanged();
        nonClinicalAssessmentArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Assessment.getAssessmentByType("NON_CLINICAL"));
        nonClinicalAssessments.setAdapter(nonClinicalAssessmentArrayAdapter);
        nonClinicalAssessmentArrayAdapter.notifyDataSetChanged();
        stableArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Stable.getAll());
        stable.setAdapter(stableArrayAdapter);
        stableArrayAdapter.notifyDataSetChanged();
        enhancedArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Enhanced.getAll());
        enhanced.setAdapter(enhancedArrayAdapter);
        enhancedArrayAdapter.notifyDataSetChanged();
        serviceOfferedArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, ServiceOffered.getAll());
        servicesOffered.setAdapter(serviceOfferedArrayAdapter);
        serviceOfferedArrayAdapter.notifyDataSetChanged();
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
        clinicalAssessments.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        clinicalAssessments.setItemsCanFocus(false);
        nonClinicalAssessments.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        nonClinicalAssessments.setItemsCanFocus(false);
        stable.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        stable.setItemsCanFocus(false);
        enhanced.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        enhanced.setItemsCanFocus(false);
        servicesOffered.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        servicesOffered.setItemsCanFocus(false);
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
            ArrayList<Assessment> assessments = (ArrayList<Assessment>) Assessment.findClinicalByContact(Contact.findById(itemID));
            count = clinicalAssessmentArrayAdapter.getCount();
            for(i = 0; i < count; i++){
                Assessment current = clinicalAssessmentArrayAdapter.getItem(i);
                if(assessments.contains(current)){
                    clinicalAssessments.setItemChecked(i, true);
                }
            }
            assessments = (ArrayList<Assessment>) Assessment.findNonClinicalByContact(Contact.findById(itemID));
            count = nonClinicalAssessmentArrayAdapter.getCount();
            for(i = 0; i < count; i++){
                Assessment current = nonClinicalAssessmentArrayAdapter.getItem(i);
                if(assessments.contains(current)){
                    nonClinicalAssessments.setItemChecked(i, true);
                }
            }
            ArrayList<ServiceOffered> serviceOffereds = (ArrayList<ServiceOffered>) ServiceOffered.findByContact(Contact.findById(itemID));
            count = serviceOfferedArrayAdapter.getCount();
            for(i = 0; i < count; i++){
                ServiceOffered current = serviceOfferedArrayAdapter.getItem(i);
                if(serviceOffereds.contains(current)){
                    servicesOffered.setItemChecked(i, true);
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

            if(holder.clinicalAssessmentId != null){
                ArrayList<String> list = (ArrayList<String>) holder.clinicalAssessmentId;
                int count = clinicalAssessmentArrayAdapter.getCount();
                for(i = 0; i < count; i++){
                    Assessment current = clinicalAssessmentArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        clinicalAssessments.setItemChecked(i, true);
                    }
                }
            }

            if(holder.nonClinicalAssessmentId != null){
                ArrayList<String> list = (ArrayList<String>) holder.nonClinicalAssessmentId;
                int count = nonClinicalAssessmentArrayAdapter.getCount();
                for(i = 0; i < count; i++){
                    Assessment current = nonClinicalAssessmentArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        nonClinicalAssessments.setItemChecked(i, true);
                    }
                }
            }

            if(holder.serviceOfferedId != null){
                ArrayList<String> list = (ArrayList<String>) holder.serviceOfferedId;
                int count = serviceOfferedArrayAdapter.getCount();
                for(i = 0; i < count; i++){
                    ServiceOffered current = serviceOfferedArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        servicesOffered.setItemChecked(i, true);
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
        holder.clinicalAssessmentId = getClinicalAssessments();
        holder.nonClinicalAssessmentId = getNonClinicalAssessments();
        holder.serviceOfferedId = getServicesOffered();
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
        for(int i = 0; i <  getClinicalAssessments().size(); i++){
            ContactClinicalAssessmentContract contract = new ContactClinicalAssessmentContract();
            contract.assessment = Assessment.getItem(getClinicalAssessments().get(i));
            if(itemID != null){
                contract.contact = Contact.findById(itemID);
            }else{
                contract.contact = Contact.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i <  getNonClinicalAssessments().size(); i++){
            ContactNonClinicalAssessmentContract contract = new ContactNonClinicalAssessmentContract();
            contract.assessment = Assessment.getItem(getNonClinicalAssessments().get(i));
            if(itemID != null){
                contract.contact = Contact.findById(itemID);
            }else{
                contract.contact = Contact.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i <  getServicesOffered().size(); i++){
            ContactServiceOfferedContract contract = new ContactServiceOfferedContract();
            contract.serviceOffered = ServiceOffered.getItem(getServicesOffered().get(i));
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

    private ArrayList<String> getClinicalAssessments(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < clinicalAssessments.getCount(); i++){
            if(clinicalAssessments.isItemChecked(i)){
                a.add(clinicalAssessmentArrayAdapter.getItem(i).id);
            }else{
                a.remove(clinicalAssessmentArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }

    private ArrayList<String> getNonClinicalAssessments(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < nonClinicalAssessments.getCount(); i++){
            if(nonClinicalAssessments.isItemChecked(i)){
                a.add(nonClinicalAssessmentArrayAdapter.getItem(i).id);
            }else{
                a.remove(nonClinicalAssessmentArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }

    private ArrayList<String> getServicesOffered(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < servicesOffered.getCount(); i++){
            if(servicesOffered.isItemChecked(i)){
                a.add(serviceOfferedArrayAdapter.getItem(i).id);
            }else{
                a.remove(serviceOfferedArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }

    private void deleteMultipleSelections(){
        for(ContactStableContract c : ContactStableContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
        }
        for(ContactEnhancedContract c: ContactEnhancedContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
        }
        for(ContactClinicalAssessmentContract c: ContactClinicalAssessmentContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
        }
        for(ContactNonClinicalAssessmentContract c: ContactNonClinicalAssessmentContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
        }
        for(ContactServiceOfferedContract c: ContactServiceOfferedContract.findByContact(Contact.findById(itemID))){
            if(c != null)
                c.delete();
        }
    }
}
