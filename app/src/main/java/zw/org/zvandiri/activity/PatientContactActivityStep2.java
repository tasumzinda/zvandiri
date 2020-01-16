package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.ActionTaken;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;
import zw.org.zvandiri.toolbox.ListViewUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private Spinner userLevel;
    private ArrayAdapter<UserLevel> userLevelArrayAdapter;
    private Spinner referredPerson;
    private ArrayAdapter<User> userArrayAdapter;
    private LinearLayout userLevelContainer;
    private Spinner province;
    private ArrayAdapter<Province> provinceArrayAdapter;
    private LinearLayout provinceContainer;
    private Spinner district;
    private ArrayAdapter<District> districtArrayAdapter;
    private LinearLayout districtContainer;
    private LinearLayout testResultContainer;
    private Spinner vlResultsAvailable;
    private ArrayAdapter<YesNo> yesNoArrayAdapter;
    private EditText vlDateTaken;
    private EditText vlResult;
    private Spinner vlSource;
    private ArrayAdapter<Cd4CountResultSource> sourceArrayAdapter;
    private EditText vlNextLab;
    private LinearLayout vlContainer;
    private Spinner cd4ResultsAvailable;
    private EditText cd4DateTaken;
    private EditText cd4Result;
    private Spinner cd4Source;
    private EditText cd4NextLab;
    private LinearLayout cd4Container;
    private ListView labTasks;
    private ArrayAdapter<LabTask> labTaskArrayAdapter;
    private Spinner followUp;
    private Contact holder;
    DatePickerDialog dialog;

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
        userLevel = (Spinner) findViewById(R.id.userLevel);
        referredPerson = (Spinner) findViewById(R.id.referredPerson);
        userLevelContainer = (LinearLayout) findViewById(R.id.userLevelContainer);
        province = (Spinner) findViewById(R.id.province);
        provinceContainer = (LinearLayout) findViewById(R.id.provinceContainer);
        district = (Spinner) findViewById(R.id.district);
        districtContainer = (LinearLayout) findViewById(R.id.districtContainer);
        testResultContainer = (LinearLayout) findViewById(R.id.testResultContainer);
        vlResultsAvailable = (Spinner) findViewById(R.id.vlResultsAvailable);
        vlDateTaken = (EditText) findViewById(R.id.vlDateTaken);
        vlResult = (EditText) findViewById(R.id.vlResult);
        vlSource = (Spinner) findViewById(R.id.vlSource);
        vlNextLab = (EditText) findViewById(R.id.vlNextLab);
        vlContainer = (LinearLayout) findViewById(R.id.vlContainer);
        cd4ResultsAvailable = (Spinner) findViewById(R.id.cd4ResultsAvailable);
        cd4DateTaken = (EditText) findViewById(R.id.cd4DateTaken);
        cd4Result = (EditText) findViewById(R.id.cd4Result);
        cd4Source = (Spinner) findViewById(R.id.cd4Source);
        cd4NextLab = (EditText) findViewById(R.id.cd4NextLab);
        cd4Container = (LinearLayout) findViewById(R.id.cd4Container);
        labTasks = (ListView) findViewById(R.id.labTasks);
        followUp = (Spinner) findViewById(R.id.followUp);
        next = (Button) findViewById(R.id.btn_next);
        next.setOnClickListener(this);
        ArrayAdapter<CareLevel> careLevelArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CareLevel.values());
        careLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        careLevel.setAdapter(careLevelArrayAdapter);
        careLevelArrayAdapter.notifyDataSetChanged();
        clinicalAssessmentArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Assessment.getAssessmentByType("CLINICAL"));
        clinicalAssessments.setAdapter(clinicalAssessmentArrayAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(clinicalAssessments);
        clinicalAssessmentArrayAdapter.notifyDataSetChanged();
        nonClinicalAssessmentArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Assessment.getAssessmentByType("NON_CLINICAL"));
        nonClinicalAssessments.setAdapter(nonClinicalAssessmentArrayAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(nonClinicalAssessments);
        nonClinicalAssessmentArrayAdapter.notifyDataSetChanged();
        stableArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Stable.getAll());
        stable.setAdapter(stableArrayAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(stable);
        stableArrayAdapter.notifyDataSetChanged();
        enhancedArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Enhanced.getAll());
        enhanced.setAdapter(enhancedArrayAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(enhanced);
        enhancedArrayAdapter.notifyDataSetChanged();
        serviceOfferedArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, ServiceOffered.getAll());
        servicesOffered.setAdapter(serviceOfferedArrayAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(servicesOffered);
        serviceOfferedArrayAdapter.notifyDataSetChanged();
        labTaskArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, LabTask.getAll());
        labTasks.setAdapter(labTaskArrayAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(labTasks);
        labTaskArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<FollowUp> followUpArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, FollowUp.values());
        followUpArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        followUp.setAdapter(followUpArrayAdapter);
        followUpArrayAdapter.notifyDataSetChanged();
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
        labTasks.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        labTasks.setItemsCanFocus(false);
        actionTakenArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, zw.org.zvandiri.business.domain.ActionTaken.getAll());
        actionTakenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionTaken.setAdapter(actionTakenArrayAdapter);
        actionTakenArrayAdapter.notifyDataSetChanged();
        userLevelArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, UserLevel.values());
        userLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userLevel.setAdapter(userLevelArrayAdapter);
        userLevelArrayAdapter.notifyDataSetChanged();
        userArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, User.getUsers(null, null, null));
        userArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        referredPerson.setAdapter(userArrayAdapter);
        userArrayAdapter.notifyDataSetChanged();
        provinceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Province.getAll());
        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(provinceArrayAdapter);
        provinceArrayAdapter.notifyDataSetChanged();
        districtArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, District.getAll());
        districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district.setAdapter(districtArrayAdapter);
        districtArrayAdapter.notifyDataSetChanged();
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vlResultsAvailable.setAdapter(yesNoArrayAdapter);
        cd4ResultsAvailable.setAdapter(yesNoArrayAdapter);
        sourceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Cd4CountResultSource.values());
        sourceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vlSource.setAdapter(sourceArrayAdapter);
        cd4Source.setAdapter(sourceArrayAdapter);
        actionTaken.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ActionTaken selected = (ActionTaken) adapterView.getItemAtPosition(i);
                if(selected.name.equals("Internal Referral")) {
                    userLevelContainer.setVisibility(View.VISIBLE);
                }else {
                    userLevelContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        userLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserLevel selected = (UserLevel) adapterView.getItemAtPosition(i);
                List<User> users = User.getUsers(null, null, selected);
                userArrayAdapter.clear();
                userArrayAdapter.addAll(users);
                userArrayAdapter.notifyDataSetChanged();
                if(selected.getName().equalsIgnoreCase("Province")) {
                    provinceContainer.setVisibility(View.VISIBLE);
                }else if(selected.getName().equalsIgnoreCase("District")){
                    provinceContainer.setVisibility(View.VISIBLE);
                    districtContainer.setVisibility(View.VISIBLE);
                }else{
                    provinceContainer.setVisibility(View.GONE);
                    districtContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Province selected = (Province) adapterView.getItemAtPosition(i);
                UserLevel level = (UserLevel) userLevel.getSelectedItem();
                List<User> users = User.getUsers(selected, null, level);
                userArrayAdapter.clear();
                userArrayAdapter.addAll(users);
                userArrayAdapter.notifyDataSetChanged();
                if(level.getName().equalsIgnoreCase("District")) {
                    List<District> districts = District.getByProvince(selected);
                    districtArrayAdapter.clear();
                    districtArrayAdapter.addAll(districts);
                    districtArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        vlResultsAvailable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = (YesNo) adapterView.getItemAtPosition(i);
                if(selected.equals(YesNo.YES)) {
                    vlContainer.setVisibility(View.VISIBLE);
                }else{
                    vlContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        vlDateTaken.setFocusable(false);
        vlDateTaken.setOnClickListener(this);
        vlNextLab.setFocusable(false);
        vlNextLab.setOnClickListener(this);

        cd4ResultsAvailable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YesNo selected = (YesNo) adapterView.getItemAtPosition(i);
                if(selected.equals(YesNo.YES)) {
                    cd4Container.setVisibility(View.VISIBLE);
                }else{
                    cd4Container.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cd4DateTaken.setFocusable(false);
        cd4DateTaken.setOnClickListener(this);
        cd4NextLab.setFocusable(false);
        cd4NextLab.setOnClickListener(this);
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
            ArrayList<LabTask> labTaskList = (ArrayList<LabTask>) LabTask.findByContact(Contact.findById(itemID));
            count = labTaskArrayAdapter.getCount();
            for(i = 0; i < count; i++){
                LabTask current = labTaskArrayAdapter.getItem(i);
                if(labTaskList.contains(current)){
                    labTasks.setItemChecked(i, true);
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
            i = 0;
            for (User m : User.getAll()) {
                if (c.referredPerson != null && c.referredPerson.equals(referredPerson.getItemAtPosition(i))) {
                    referredPerson.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(FollowUp m : FollowUp.values()){
                if(c.followUp != null && c.followUp.equals(followUp.getItemAtPosition(i))){
                    followUp.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Contact - Step 2"));
        }else if(holder.careLevel != null){
            testResultContainer.setVisibility(View.VISIBLE);
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

            if(holder.labTaskId != null){
                ArrayList<String> list = (ArrayList<String>) holder.labTaskId;
                int count = labTaskArrayAdapter.getCount();
                for(i = 0; i < count; i++){
                    LabTask current = labTaskArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        labTasks.setItemChecked(i, true);
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
            i = 0;
            for (User m : User.getAll()) {
                if (holder.referredPersonId != null && holder.referredPersonId.equals(((User) referredPerson.getItemAtPosition(i)).id)) {
                    referredPerson.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(FollowUp m : FollowUp.values()){
                if(holder.followUp != null && holder.followUp.equals(followUp.getItemAtPosition(i))){
                    followUp.setSelection(i, true);
                    break;
                }
                i++;
            }
            c = new Contact();
            if(Contact.findPatientLastContact(Patient.getById(id)) != null) {
                Contact previous = Contact.findPatientLastContact(Patient.getById(id));
                i = 0;
                for (CareLevel m : CareLevel.values()) {
                    if (previous.careLevel != null && previous.careLevel.equals(careLevel.getItemAtPosition(i))) {
                        careLevel.setSelection(i, true);
                        break;
                    }
                    i++;
                }
            }
            setSupportActionBar(createToolBar("Add Contact - Step 2"));
        }else{
            testResultContainer.setVisibility(View.VISIBLE);
            c = new Contact();
            if(Contact.findPatientLastContact(Patient.getById(id)) != null) {
                int i = 0;
                Contact previous = Contact.findPatientLastContact(Patient.getById(id));
                for (CareLevel m : CareLevel.values()) {
                    if (previous.careLevel != null && previous.careLevel.equals(careLevel.getItemAtPosition(i))) {
                        careLevel.setSelection(i, true);
                        break;
                    }
                    i++;
                }
            }
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
        holder.followUp = (FollowUp) followUp.getSelectedItem();
        if(careLevel.getSelectedItem().equals(CareLevel.ENHANCED)){
            holder.enhancedId = getEnhanceds();
        }else{
            holder.stableId = getStables();
        }
        holder.clinicalAssessmentId = getClinicalAssessments();
        holder.nonClinicalAssessmentId = getNonClinicalAssessments();
        holder.serviceOfferedId = getServicesOffered();
        holder.referredPersonId = ((User) referredPerson.getSelectedItem()).id;
        holder.labTaskId = getLabTasks();
        intent.putExtra("contact", holder);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            if(validate()) {
                save();
            }
        }
        if(view.getId() == vlDateTaken.getId()){
            showDatePickerDialog(vlDateTaken);
        }
        if(view.getId() == vlNextLab.getId()){
            showDatePickerDialog(vlNextLab);
        }

        if(view.getId() == cd4DateTaken.getId()){
            showDatePickerDialog(cd4DateTaken);
        }
        if(view.getId() == cd4NextLab.getId()){
            showDatePickerDialog(cd4NextLab);
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
        c.followUp = (FollowUp) followUp.getSelectedItem();
        c.visitOutcome = holder.visitOutcome;
        c.contactPhoneOption = holder.contactPhoneOption;
        c.numberOfSms = holder.numberOfSms;
        c.objective = holder.objective;
        c.subjective = holder.subjective;
        c.plan = holder.plan;
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
        c.nextClinicAppointmentDate = holder.nextClinicAppointmentDate;
        if(holder.locationId != null){
            c.location = Location.getItem(holder.locationId);
        }
        c.patient =Patient.getById(id);
        c.pushed = 0;
        c.actionTaken = (ActionTaken) actionTaken.getSelectedItem();
        c.referredPerson = (User) referredPerson.getSelectedItem();
        c.attendedClinicAppointment = holder.attendedClinicAppointment;
        c.differentiatedService = holder.differentiatedService;
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
        for(int i = 0; i <  getLabTasks().size(); i++){
            ContactLabTaskContact contract = new ContactLabTaskContact();
            contract.labTask = LabTask.getItem(getLabTasks().get(i));
            if(itemID != null){
                contract.contact = Contact.findById(itemID);
            }else{
                contract.contact = Contact.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        if(vlResultsAvailable.getSelectedItem().equals(YesNo.YES)) {
            InvestigationTest viralLoad = new InvestigationTest();
            viralLoad.dateTaken = DateUtil.getDateFromString(vlDateTaken.getText().toString());
            viralLoad.nextTestDate = DateUtil.getDateFromString(vlNextLab.getText().toString());
            viralLoad.result = Integer.valueOf(vlResult.getText().toString());
            viralLoad.source = (Cd4CountResultSource) vlSource.getSelectedItem();
            viralLoad.testType = TestType.VIRAL_LOAD;
            viralLoad.resultTaken = (YesNo) vlResultsAvailable.getSelectedItem();
            viralLoad.isNew = 1;
            viralLoad.patient =Patient.getById(id);
            viralLoad.save();
        }
        if(cd4ResultsAvailable.getSelectedItem().equals(YesNo.YES)) {
            InvestigationTest cd4 = new InvestigationTest();
            cd4.dateTaken = DateUtil.getDateFromString(cd4DateTaken.getText().toString());
            cd4.nextTestDate = DateUtil.getDateFromString(cd4NextLab.getText().toString());
            cd4.result = Integer.valueOf(cd4Result.getText().toString());
            cd4.source = (Cd4CountResultSource) cd4Source.getSelectedItem();
            cd4.testType = TestType.CD4_COUNT;
            cd4.resultTaken = (YesNo) cd4ResultsAvailable.getSelectedItem();
            cd4.isNew = 1;
            cd4.patient =Patient.getById(id);
            cd4.save();
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

    private ArrayList<String> getLabTasks(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < labTasks.getCount(); i++){
            if(labTasks.isItemChecked(i)){
                a.add(labTaskArrayAdapter.getItem(i).id);
            }else{
                a.remove(labTaskArrayAdapter.getItem(i).id);
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
        for(ContactLabTaskContact c : ContactLabTaskContact.findByContact(Contact.findById(itemID))) {
            if(c != null)
                c.delete();
        }
    }

    private void showDatePickerDialog(final EditText field) {
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), field);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private boolean validate() {
        boolean isValid = true;
        if(vlResultsAvailable.getSelectedItem().equals(YesNo.YES)) {
            String selected = vlDateTaken.getText().toString();
            if(selected.isEmpty()) {
                vlDateTaken.setError(getString(R.string.required_field_error));
                isValid = false;
            }else{
                vlDateTaken.setError(null);
            }
            selected = vlResult.getText().toString();
            if(selected.isEmpty()) {
                vlResult.setError(getString(R.string.required_field_error));
                isValid = false;
            }else{
                vlResult.setError(null);
            }
            selected = vlNextLab.getText().toString();
            if(selected.isEmpty()) {
                vlNextLab.setError(getString(R.string.required_field_error));
                isValid = false;
            }else{
                vlNextLab.setError(null);
            }
        }
        if(cd4ResultsAvailable.getSelectedItem().equals(YesNo.YES)) {
            String selected = cd4DateTaken.getText().toString();
            if(selected.isEmpty()) {
                cd4DateTaken.setError(getString(R.string.required_field_error));
                isValid = false;
            }else{
                cd4DateTaken.setError(null);
            }
            selected = cd4Result.getText().toString();
            if(selected.isEmpty()) {
                cd4Result.setError(getString(R.string.required_field_error));
                isValid = false;
            }else{
                cd4Result.setError(null);
            }
            selected = cd4NextLab.getText().toString();
            if(selected.isEmpty()) {
                cd4NextLab.setError(getString(R.string.required_field_error));
                isValid = false;
            }else{
                cd4NextLab.setError(null);
            }
        }
        return isValid;
    }
}
