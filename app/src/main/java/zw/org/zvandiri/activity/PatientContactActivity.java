package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.ActionTaken;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PatientContactActivity extends BaseActivity implements View.OnClickListener {

    private Spinner visitOutcome;
    private EditText contactDate;
    private EditText subjective;
    private EditText objective;
    private EditText plan;
    private Spinner location;
    private Spinner position;
    private Spinner reason;
    private Spinner externalReferral;
    private Spinner internalReferral;
    TextView externalReferralLabel;
    TextView internalReferralLabel;
    private EditText lastClinicAppointmentDate;
    private EditText nextClinicAppointmentDate;
    private Spinner attendedClinicAppointment;
    private Spinner contactPhoneOption;
    private LinearLayout phoneOptionContainer;
    private EditText numberOfSms;
    private LinearLayout smsContainer;
    private Spinner differentiatedService;
    private Button save;
    private Contact item;
    private String id;
    private String name;
    private String itemID;
    DatePickerDialog dialog;
    Contact holder;
    ArrayList<String> stableId;
    ArrayList<String> enhancedId;
    ArrayList<String> nonClinicalAssessmentId;
    ArrayList<String> clinicalAssessmentId;
    ArrayList<String> serviceOfferedId;
    ArrayList<String> labTaskId;
    CareLevel careLevel;
    String actionTakenId;
    String referredPersonId;
    private ArrayAdapter<VisitOutcome> visitOutcomeArrayAdapter;
    private ArrayAdapter<ContactPhoneOption> contactPhoneOptionArrayAdapter;
    private ArrayAdapter<DifferentiatedService> differentiatedServiceArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_contact_first);
        contactDate = (EditText) findViewById(R.id.contactDate);
        contactDate.setFocusable(false);
        subjective = (EditText) findViewById(R.id.subjective);
        objective = (EditText) findViewById(R.id.objective);
        plan = (EditText) findViewById(R.id.plan);
        location = (Spinner) findViewById(R.id.location);
        position = (Spinner) findViewById(R.id.position);
        reason = (Spinner) findViewById(R.id.reason);
        attendedClinicAppointment = (Spinner) findViewById(R.id.attendedClinicAppointment);
        externalReferral = (Spinner) findViewById(R.id.externalReferral);
        internalReferral = (Spinner) findViewById(R.id.internalReferral);
        lastClinicAppointmentDate = (EditText) findViewById(R.id.lastClinicAppointmentDate);
        lastClinicAppointmentDate.setFocusable(false);
        nextClinicAppointmentDate = (EditText) findViewById(R.id.nextClinicAppointmentDate);
        nextClinicAppointmentDate.setFocusable(false);
        save = (Button) findViewById(R.id.btn_save);
        externalReferralLabel = (TextView) findViewById(R.id.externalReferralLabel);
        internalReferralLabel = (TextView) findViewById(R.id.internalReferralLabel);
        visitOutcome = (Spinner) findViewById(R.id.visitOutcome);
        contactPhoneOption = (Spinner) findViewById(R.id.contactPhoneOption);
        phoneOptionContainer = (LinearLayout) findViewById(R.id.phoneOptionContainer);
        numberOfSms = (EditText) findViewById(R.id.numberOfSms);
        smsContainer = (LinearLayout) findViewById(R.id.smsContainer);
        differentiatedService = (Spinner) findViewById(R.id.differentiatedService);
        Intent intent = getIntent();
        holder = (Contact) intent.getSerializableExtra("contact");
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<YesNo> yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attendedClinicAppointment.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Location> locationArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Location.getAll());
        locationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(locationArrayAdapter);
        locationArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Position> positionArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Position.getAll());
        positionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(positionArrayAdapter);
        positionArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Reason> reasonArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Reason.values());
        reasonArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reason.setAdapter(reasonArrayAdapter);
        reasonArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<ExternalReferral> externalReferralArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ExternalReferral.getAll());
        externalReferralArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        externalReferral.setAdapter(externalReferralArrayAdapter);
        externalReferralArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<InternalReferral> internalReferralArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, InternalReferral.getAll());
        internalReferralArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        internalReferral.setAdapter(internalReferralArrayAdapter);
        internalReferralArrayAdapter.notifyDataSetChanged();
        visitOutcomeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, VisitOutcome.values());
        visitOutcomeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitOutcome.setAdapter(visitOutcomeArrayAdapter);
        visitOutcomeArrayAdapter.notifyDataSetChanged();
        contactPhoneOptionArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ContactPhoneOption.values());
        contactPhoneOptionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactPhoneOption.setAdapter(contactPhoneOptionArrayAdapter);
        contactPhoneOptionArrayAdapter.notifyDataSetChanged();
        differentiatedServiceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, DifferentiatedService.values());
        differentiatedServiceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        differentiatedService.setAdapter(differentiatedServiceArrayAdapter);
        differentiatedServiceArrayAdapter.notifyDataSetChanged();
        contactDate.setOnClickListener(this);
        lastClinicAppointmentDate.setOnClickListener(this);
        nextClinicAppointmentDate.setOnClickListener(this);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Location selected = (Location) adapterView.getItemAtPosition(i);
                if(selected.name.equalsIgnoreCase("Phone")) {
                    phoneOptionContainer.setVisibility(View.VISIBLE);
                }else {
                    phoneOptionContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        contactPhoneOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ContactPhoneOption selected = (ContactPhoneOption) adapterView.getItemAtPosition(i);
                if(selected.equals(ContactPhoneOption.SMS)) {
                    smsContainer.setVisibility(View.VISIBLE);
                }else{
                    smsContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemID != null){
            item = Contact.findById(itemID);
            updateLabel(item.contactDate, contactDate);
            subjective.setText(item.subjective);
            objective.setText(item.objective);
            plan.setText(item.plan);
            numberOfSms.setText(item.numberOfSms);
            if(item.lastClinicAppointmentDate != null){
                updateLabel(item.lastClinicAppointmentDate, lastClinicAppointmentDate);
            }
            if(item.nextClinicAppointmentDate != null){
                updateLabel(item.nextClinicAppointmentDate, nextClinicAppointmentDate);
            }

            if(item.reason.equals(Reason.EXTERNAL_REFERRAL)){
                externalReferral.setVisibility(View.VISIBLE);
                externalReferralLabel.setVisibility(View.VISIBLE);
            }else if(item.reason.equals(Reason.INTERNAL_REFERRAL)){
                internalReferralLabel.setVisibility(View.VISIBLE);
                internalReferral.setVisibility(View.VISIBLE);
            }else{
                internalReferral.setVisibility(View.GONE);
                internalReferralLabel.setVisibility(View.GONE);
                externalReferral.setVisibility(View.GONE);
                externalReferralLabel.setVisibility(View.GONE);
            }

            int i = 0;
            for(Location m : Location.getAll()){
                if(item.location != null && item.location.equals(location.getItemAtPosition(i))){
                    location.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Position m : Position.getAll()){
                if(item.position != null && item.position.equals(position.getItemAtPosition(i))){
                    position.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Reason m : Reason.values()){
                if(item.reason != null  && item.reason.equals(reason.getItemAtPosition(i))){
                    reason.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(ExternalReferral m : ExternalReferral.getAll()){
                if(item.externalReferral != null && item.externalReferral.equals(externalReferral.getItemAtPosition(i))){
                    externalReferral.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(InternalReferral m : InternalReferral.getAll()){
                if(item.internalReferral != null && item.internalReferral.equals(internalReferral.getItemAtPosition(i))){
                    internalReferral.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.attendedClinicAppointment != null && item.attendedClinicAppointment.equals(attendedClinicAppointment.getItemAtPosition(i))){
                    attendedClinicAppointment.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(VisitOutcome m : VisitOutcome.values()){
                if(item.visitOutcome != null && item.visitOutcome.equals(visitOutcome.getItemAtPosition(i))){
                    visitOutcome.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(ContactPhoneOption m : ContactPhoneOption.values()){
                if(item.contactPhoneOption != null && item.contactPhoneOption.equals(contactPhoneOption.getItemAtPosition(i))){
                    contactPhoneOption.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(DifferentiatedService m : DifferentiatedService.values()){
                if(item.differentiatedService != null && item.differentiatedService.equals(differentiatedService.getItemAtPosition(i))){
                    differentiatedService.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Contact - Step 1"));
        }else if(holder != null){
            updateLabel(holder.contactDate, contactDate);
            subjective.setText(holder.subjective);
            objective.setText(holder.objective);
            plan.setText(holder.plan);
            numberOfSms.setText(holder.numberOfSms != null ? String.valueOf(holder.numberOfSms) : "");
            if(holder.lastClinicAppointmentDate != null){
                updateLabel(holder.lastClinicAppointmentDate, lastClinicAppointmentDate);
            }
            if(holder.nextClinicAppointmentDate != null){
                updateLabel(holder.nextClinicAppointmentDate, nextClinicAppointmentDate);
            }
            if(holder.reason.equals(Reason.EXTERNAL_REFERRAL)){
                externalReferral.setVisibility(View.VISIBLE);
                externalReferralLabel.setVisibility(View.VISIBLE);
            }else if(holder.reason.equals(Reason.INTERNAL_REFERRAL)){
                internalReferralLabel.setVisibility(View.VISIBLE);
                internalReferral.setVisibility(View.VISIBLE);
            }else{
                internalReferral.setVisibility(View.GONE);
                internalReferralLabel.setVisibility(View.GONE);
                externalReferral.setVisibility(View.GONE);
                externalReferralLabel.setVisibility(View.GONE);
            }
            int i = 0;
            for(Location m : Location.getAll()){
                if(holder.locationId != null && holder.locationId.equals(((Location) location.getItemAtPosition(i)).id)){
                    location.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Position m : Position.getAll()){
                if(holder.positionId != null && holder.positionId.equals(((Position) position.getItemAtPosition(i)).id)){
                    position.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Reason m : Reason.values()){
                if(holder.reason != null  && holder.reason.equals(reason.getItemAtPosition(i))){
                    reason.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(ExternalReferral m : ExternalReferral.getAll()){
                if(holder.externalReferralId != null && holder.externalReferralId.equals(((ExternalReferral) externalReferral.getItemAtPosition(i)).id)){
                    externalReferral.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(InternalReferral m : InternalReferral.getAll()){
                if(holder.internalReferralId != null && holder.internalReferralId.equals(((InternalReferral) internalReferral.getItemAtPosition(i)).id)){
                    internalReferral.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.attendedClinicAppointment != null && holder.attendedClinicAppointment.equals(attendedClinicAppointment.getItemAtPosition(i))){
                    attendedClinicAppointment.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(VisitOutcome m : VisitOutcome.values()){
                if(holder.visitOutcome != null && holder.visitOutcome.equals(visitOutcome.getItemAtPosition(i))){
                    visitOutcome.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(ContactPhoneOption m : ContactPhoneOption.values()){
                if(holder.contactPhoneOption != null && holder.contactPhoneOption.equals(contactPhoneOption.getItemAtPosition(i))){
                    contactPhoneOption.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(DifferentiatedService m : DifferentiatedService.values()){
                if(holder.differentiatedService != null && holder.differentiatedService.equals(differentiatedService.getItemAtPosition(i))){
                    differentiatedService.setSelection(i, true);
                    break;
                }
                i++;
            }
            stableId = (ArrayList<String>) holder.stableId;
            enhancedId = (ArrayList<String>) holder.enhancedId;
            careLevel = holder.careLevel;
            clinicalAssessmentId = (ArrayList<String>) holder.clinicalAssessmentId;
            nonClinicalAssessmentId = (ArrayList<String>) holder.nonClinicalAssessmentId;
            serviceOfferedId = (ArrayList<String>) holder.serviceOfferedId;
            labTaskId = (ArrayList<String>) holder.labTaskId;
            actionTakenId = holder.actionTakenId;
            referredPersonId = holder.referredPersonId;
            setSupportActionBar(createToolBar("Add Contact - Step 1"));
        }else{
            holder = new Contact();
            if(Contact.findPatientLastContact(Patient.getById(id)) != null) {
                updateLabel(Contact.findPatientLastContact(Patient.getById(id)).nextClinicAppointmentDate, lastClinicAppointmentDate);
            }
            setSupportActionBar(createToolBar("Add Contact - Step 1"));
        }
        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(reason.getSelectedItem().equals(Reason.EXTERNAL_REFERRAL)){
                    externalReferral.setVisibility(View.VISIBLE);
                    externalReferralLabel.setVisibility(View.VISIBLE);
                    internalReferral.setVisibility(View.GONE);
                    internalReferralLabel.setVisibility(View.GONE);
                }else if(reason.getSelectedItem().equals(Reason.INTERNAL_REFERRAL)){
                    internalReferralLabel.setVisibility(View.VISIBLE);
                    internalReferral.setVisibility(View.VISIBLE);
                    externalReferral.setVisibility(View.GONE);
                    externalReferralLabel.setVisibility(View.GONE);
                }else{
                    internalReferral.setVisibility(View.GONE);
                    internalReferralLabel.setVisibility(View.GONE);
                    externalReferral.setVisibility(View.GONE);
                    externalReferralLabel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        save.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onBackPressed(){
        Intent intent = new Intent(PatientContactActivity.this, PatientListActivity.class);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.NAME, name);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == contactDate.getId()){
            showDatePickerDialog(contactDate);
        }
        if(view.getId() == lastClinicAppointmentDate.getId()){
            showDatePickerDialog(lastClinicAppointmentDate);
        }
        if(view.getId() == nextClinicAppointmentDate.getId()){
            showDatePickerDialog(nextClinicAppointmentDate);
        }
        if(view.getId() == save.getId()){
            if(validateLocal()){
                Intent intent = new Intent(PatientContactActivity.this, PatientContactActivityStep2.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                intent.putExtra(AppUtil.DETAILS_ID, itemID);
                holder.contactDate = DateUtil.getDateFromString(contactDate.getText().toString());
                holder.locationId = ((Location) location.getSelectedItem()).id;
                holder.positionId = ((Position) position.getSelectedItem()).id;
                if(externalReferral.getVisibility() == View.VISIBLE){
                    holder.externalReferralId = ((ExternalReferral) externalReferral.getSelectedItem()).id;
                }
                if(internalReferral.getVisibility() == View.VISIBLE){
                    holder.internalReferralId = ((InternalReferral) internalReferral.getSelectedItem()).id;
                }
                holder.reason = (Reason) reason.getSelectedItem();
                holder.attendedClinicAppointment = (YesNo) attendedClinicAppointment.getSelectedItem();
                if( ! lastClinicAppointmentDate.getText().toString().isEmpty()){
                    holder.lastClinicAppointmentDate = DateUtil.getDateFromString(lastClinicAppointmentDate.getText().toString());
                }
                if( ! nextClinicAppointmentDate.getText().toString().isEmpty()){
                    holder.nextClinicAppointmentDate = DateUtil.getDateFromString(nextClinicAppointmentDate.getText().toString());
                }
                holder.actionTakenId = actionTakenId;
                holder.referredPersonId = referredPersonId;
                holder.enhancedId = enhancedId;
                holder.stableId = stableId;
                holder.clinicalAssessmentId = clinicalAssessmentId;
                holder.nonClinicalAssessmentId = nonClinicalAssessmentId;
                holder.serviceOfferedId = serviceOfferedId;
                holder.labTaskId = labTaskId;
                holder.careLevel = careLevel;
                holder.subjective = subjective.getText().toString();
                holder.objective = objective.getText().toString();
                holder.plan = plan.getText().toString();
                holder.visitOutcome = (VisitOutcome) visitOutcome.getSelectedItem();
                holder.contactPhoneOption = (ContactPhoneOption) contactPhoneOption.getSelectedItem();
                holder.numberOfSms = AppUtil.parseInt(numberOfSms.getText().toString());
                holder.differentiatedService = (DifferentiatedService) differentiatedService.getSelectedItem();
                intent.putExtra("contact", holder);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        String date = contactDate.getText().toString();
        Date dateOfContact = null;
        if(date.isEmpty()) {
            contactDate.setError(getString(R.string.required_field_error));
            isValid = false;
        }else{
            contactDate.setError(null);
            dateOfContact = DateUtil.getDateFromString(date);
        }
        Date today = new Date();

        Date dateOfBirth = Patient.findById(id).dateOfBirth;
        if( ! checkDateFormat(date)){
            contactDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else if(checkDateFormat(date) && dateOfContact.after(today)){
                contactDate.setError(getResources().getString(R.string.date_aftertoday));
                isValid = false;
        }else if(checkDateFormat(date) && dateOfContact.before(dateOfBirth)){
                contactDate.setError(getResources().getString(R.string.date_before_birth));
                isValid = false;
        }else{
            contactDate.setError(null);
        }


        date = lastClinicAppointmentDate.getText().toString();
        Date appointmentDate = null;
        if(!date.isEmpty()) {
            appointmentDate = DateUtil.getDateFromString(date);
        }
        if(date.isEmpty()) {
            lastClinicAppointmentDate.setError(getString(R.string.required_field_error));
            isValid = false;
        }else if( ! date.isEmpty() && ! checkDateFormat(date)){
            lastClinicAppointmentDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else if(checkDateFormat(date) && appointmentDate.after(today)){
            lastClinicAppointmentDate.setError(getResources().getString(R.string.date_aftertoday));
            isValid = false;
        }else if(checkDateFormat(date) && appointmentDate.before(dateOfBirth)){
                lastClinicAppointmentDate.setError(getResources().getString(R.string.date_before_birth));
                isValid = false;

        }else{
        lastClinicAppointmentDate.setError(null);
       }
        date = nextClinicAppointmentDate.getText().toString();
        appointmentDate = null;
        if(!date.isEmpty()) {
            appointmentDate = DateUtil.getDateFromString(date);
        }
        if(date.isEmpty()) {
            nextClinicAppointmentDate.setError(getString(R.string.required_field_error));
            isValid = false;
        }else if( ! date.isEmpty() && ! checkDateFormat(date)){
            nextClinicAppointmentDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else if(checkDateFormat(date) && appointmentDate.before(dateOfBirth)){
            nextClinicAppointmentDate.setError(getResources().getString(R.string.date_before_birth));
            isValid = false;

        }else{
            nextClinicAppointmentDate.setError(null);
        }
        return isValid;
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
}
