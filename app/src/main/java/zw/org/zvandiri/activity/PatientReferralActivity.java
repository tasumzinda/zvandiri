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
    private Spinner actionTaken;
    private Button next;
    private Referral item;
    private String id;
    private String name;
    private String itemID;
    private EditText[] fields;
    private DatePickerDialog referralDateDialog;
    private DatePickerDialog dateAttendedDialog;
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
        next = (Button) findViewById(R.id.btn_next);
        Intent intent = getIntent();
        holder = (Referral) intent.getSerializableExtra("referral");
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        fields = new EditText[] {referralDate, organisation, attendingOfficer, designation};
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
        referralDate.setOnClickListener(this);
        dateAttended.setOnClickListener(this);
        ArrayAdapter<ReferralActionTaken> referralActionTakenArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ReferralActionTaken.values());
        referralActionTakenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionTaken.setAdapter(referralActionTakenArrayAdapter);
        referralActionTakenArrayAdapter.notifyDataSetChanged();
        if(itemID != null){
            item = Referral.findById(itemID);
            updateLabel(item.referralDate, referralDate);
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
            organisation.setText(holder.organisation);
            updateLabel(holder.dateAttended, dateAttended);
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
    }

    public void save(){
        if(validate(fields)){
            if(validateLocal()){
                Intent intent = new Intent(PatientReferralActivity.this, PatientRefferalStep2Activity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                intent.putExtra(AppUtil.DETAILS_ID, itemID);
                holder.referralDate = DateUtil.getDateFromString(referralDate.getText().toString());
                holder.organisation = organisation.getText().toString();
                if( ! dateAttended.getText().toString().isEmpty()){
                    holder.dateAttended = DateUtil.getDateFromString(dateAttended.getText().toString());
                }
                holder.attendingOfficer = attendingOfficer.getText().toString();
                holder.designation = designation.getText().toString();
                holder.actionTaken = (ReferralActionTaken) actionTaken.getSelectedItem();
                intent.putExtra("referral", holder);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(referralDate.getText().toString())){
            referralDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            referralDate.setError(null);
        }
        if( ! checkDateFormat(dateAttended.getText().toString())){
            dateAttended.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            dateAttended.setError(null);
        }
        return isValid;
    }
}
