package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.ReferralServicesReferredContract;
import zw.org.zvandiri.business.domain.ServicesReferred;
import zw.org.zvandiri.business.domain.util.ReferralActionTaken;
import zw.org.zvandiri.business.domain.util.ReferralStatus;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PatientReferralActivity extends BaseActivity implements View.OnClickListener {

    private EditText referralDate;
    private EditText organisation;
    private EditText dateAttended;
    private EditText attendingOfficer;
    private EditText designation;
    private EditText expectedVisitDate;
    private Spinner actionTaken;
    private Button next;
    private Referral item;
    private String id;
    private String name;
    private String itemID;
    private EditText[] fields;
    private DatePickerDialog referralDateDialog;
    private DatePickerDialog dateAttendedDialog;
    private DatePickerDialog expectedVisitDateDialog;
    private Referral holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_referral);
        referralDate = (EditText) findViewById(R.id.referralDate);
        organisation = (EditText) findViewById(R.id.organisation);
        dateAttended = (EditText) findViewById(R.id.dateAttended);
        attendingOfficer = (EditText) findViewById(R.id.attendingOfficer);
        designation = (EditText) findViewById(R.id.designation);
        actionTaken = (Spinner) findViewById(R.id.actionTaken);
        expectedVisitDate = (EditText) findViewById(R.id.expectedVisitDate);
        next = (Button) findViewById(R.id.btn_next);
        Intent intent = getIntent();
        holder = (Referral) intent.getSerializableExtra("referral");
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        fields = new EditText[] {referralDate, organisation};
        referralDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), referralDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateAttendedDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateAttended);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        expectedVisitDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), expectedVisitDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        referralDate.setFocusable(false);
        dateAttended.setFocusable(false);
        expectedVisitDate.setFocusable(false);
        referralDate.setOnClickListener(this);
        dateAttended.setOnClickListener(this);
        expectedVisitDate.setOnClickListener(this);
        ArrayAdapter<ReferralActionTaken> referralActionTakenArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ReferralActionTaken.values());
        referralActionTakenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionTaken.setAdapter(referralActionTakenArrayAdapter);
        referralActionTakenArrayAdapter.notifyDataSetChanged();
        if(itemID != null){
            item = Referral.findById(itemID);
            updateLabel(item.referralDate, referralDate);
            updateLabel(item.expectedVisitDate, expectedVisitDate);
            organisation.setText(item.organisation);
            if(item.dateAttended != null){
                updateLabel(item.dateAttended, dateAttended);
            }
            attendingOfficer.setText(item.attendingOfficer);
            designation.setText(item.designation);
            int i = 0;
            for(ReferralActionTaken m : ReferralActionTaken.values()){
                if(item.actionTaken != null && item.actionTaken.equals(actionTaken.getItemAtPosition(i))){
                    actionTaken.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Referrals"));
        }else if(holder != null){
            updateLabel(holder.referralDate, referralDate);
            if(holder.expectedVisitDate != null){
                updateLabel(holder.expectedVisitDate, expectedVisitDate);
            }
            organisation.setText(holder.organisation);
            if(holder.dateAttended != null){
                updateLabel(holder.dateAttended, dateAttended);
            }
            attendingOfficer.setText(holder.attendingOfficer);
            designation.setText(holder.designation);
            int i = 0;
            for(ReferralActionTaken m : ReferralActionTaken.values()){
                if(holder.actionTaken != null && holder.actionTaken.equals(actionTaken.getItemAtPosition(i))){
                    actionTaken.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Add Referrals"));
        }
        else{
            holder = new Referral();
            setSupportActionBar(createToolBar("Add Referrals"));
        }
        next.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onBackPressed(){
        Intent intent = new Intent(PatientReferralActivity.this, PatientReferralListActivity.class);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.NAME, name);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            save();
        }
        if(view.getId() == referralDate.getId()){
            referralDateDialog.show();
        }
        if(view.getId() == dateAttended.getId()){
            dateAttendedDialog.show();
        }
        if(view.getId() == expectedVisitDate.getId()){
            expectedVisitDateDialog.show();
        }
    }

    public void save(){
        if(validate(fields)){
            if(validateLocal()){
                Intent intent = new Intent(PatientReferralActivity.this, PatientRefferalStep2Activity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                intent.putExtra(AppUtil.DETAILS_ID, itemID);
                holder.referralDate = DateUtil.getDateFromString(referralDate.getText().toString());
                if( ! expectedVisitDate.getText().toString().isEmpty()){
                    holder.expectedVisitDate = DateUtil.getDateFromString(expectedVisitDate.getText().toString());
                }
                holder.organisation = organisation.getText().toString();
                if( ! dateAttended.getText().toString().isEmpty()){
                    holder.dateAttended = DateUtil.getDateFromString(dateAttended.getText().toString());
                }
                if( ! attendingOfficer.getText().toString().isEmpty()){
                    holder.attendingOfficer = attendingOfficer.getText().toString();
                }
                if( ! designation.getText().toString().isEmpty()){
                    holder.designation = designation.getText().toString();
                }
                holder.actionTaken = (ReferralActionTaken) actionTaken.getSelectedItem();
                intent.putExtra("referral", holder);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        Patient patient = Patient.findById(id);
        String referral = referralDate.getText().toString();
        if( ! checkDateFormat(referral)){
            referralDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else if( ! referral.isEmpty() && DateUtil.getDateFromString(referral).after(new Date())){
            referralDate.setError(getResources().getString(R.string.date_aftertoday));
            isValid = false;
        }else if( ! referral.isEmpty() && patient.dateOfBirth != null && DateUtil.getDateFromString(referral).before(patient.dateOfBirth)){
            referralDate.setError(getResources().getString(R.string.date_before_birth));
            isValid = false;
        }else{
            referralDate.setError(null);
        }


        String attended = dateAttended.getText().toString();
        if( ! attended.isEmpty() && attendingOfficer.getText().toString().isEmpty()){
            attendingOfficer.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            attendingOfficer.setError(null);
        }
        if( ! attended.isEmpty() && designation.getText().toString().isEmpty()){
            designation.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            designation.setError(null);
        }
        if( ! checkDateFormat(attended)){
            dateAttended.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else if( ! attended.isEmpty() && DateUtil.getDateFromString(attended).after(new Date())){
            dateAttended.setError(getResources().getString(R.string.date_aftertoday));
            isValid = false;
        }else if( ! attended.isEmpty() && patient.dateOfBirth != null && DateUtil.getDateFromString(attended).before(patient.dateOfBirth)){
            dateAttended.setError(getResources().getString(R.string.date_aftertoday));
            isValid = false;
        }else if(( ! attended.isEmpty() && ! referral.isEmpty()) && DateUtil.getDateFromString(attended).before(DateUtil.getDateFromString(referral))){
            dateAttended.setError(getResources().getString(R.string.referral_date_after_date_attended));
            isValid = false;
        }else{
            dateAttended.setError(null);
        }



        if( ! checkDateFormat(expectedVisitDate.getText().toString())){
            expectedVisitDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else {
            expectedVisitDate.setError(null);
        }
        return isValid;
    }
}
