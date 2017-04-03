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
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.Calendar;

public class PatientContactActivity extends BaseActivity implements View.OnClickListener {

    private EditText contactDate;
    private Spinner location;
    private Spinner position;
    private Spinner reason;
    private Spinner externalReferral;
    private Spinner internalReferral;
    private EditText subjective;
    private EditText objective;
    private EditText plan;
    private Spinner followUp;
    TextView externalReferralLabel;
    TextView internalReferralLabel;
    private Button save;
    private Contact item;
    private String id;
    private String name;
    private String itemID;
    private EditText[] fields;
    DatePickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_contact_first);
        contactDate = (EditText) findViewById(R.id.contactDate);
        location = (Spinner) findViewById(R.id.location);
        position = (Spinner) findViewById(R.id.position);
        reason = (Spinner) findViewById(R.id.reason);
        externalReferral = (Spinner) findViewById(R.id.externalReferral);
        internalReferral = (Spinner) findViewById(R.id.internalReferral);
        subjective = (EditText) findViewById(R.id.subjective);
        objective = (EditText) findViewById(R.id.objective);
        plan = (EditText) findViewById(R.id.plan);
        followUp = (Spinner) findViewById(R.id.followUp);
        fields = new EditText[] {contactDate, subjective, objective, plan};
        save = (Button) findViewById(R.id.btn_save);
        externalReferralLabel = (TextView) findViewById(R.id.externalReferralLabel);
        internalReferralLabel = (TextView) findViewById(R.id.internalReferralLabel);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
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
        ArrayAdapter<FollowUp> followUpArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, FollowUp.values());
        followUpArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        followUp.setAdapter(followUpArrayAdapter);
        followUpArrayAdapter.notifyDataSetChanged();
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), contactDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        contactDate.setOnClickListener(this);
        if(itemID != null){
            item = Contact.findById(itemID);
            updateLabel(item.contactDate, contactDate);
            subjective.setText(item.subjective);
            objective.setText(item.objective);
            plan.setText(item.plan);
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
            for(FollowUp m : FollowUp.values()){
                if(item.followUp != null && item.followUp.equals(followUp.getItemAtPosition(i))){
                    followUp.setSelection(i, true);
                    break;
                }
            }
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        save.setOnClickListener(this);
        setSupportActionBar(createToolBar("Add Contact - Step 1"));
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
        Intent intent = new Intent(PatientContactActivity.this, PatientListActivity.class);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.NAME, name);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == contactDate.getId()){
            dialog.show();
        }
        if(view.getId() == save.getId()){
            if(validate(fields)){
                if(validateLocal()){
                    Intent intent = new Intent(PatientContactActivity.this, PatientContactActivityStep2.class);
                    intent.putExtra(AppUtil.NAME, name);
                    intent.putExtra(AppUtil.ID, id);
                    intent.putExtra(AppUtil.DETAILS_ID, itemID);
                    intent.putExtra("contactDate", contactDate.getText().toString());
                    intent.putExtra("subjective", subjective.getText().toString());
                    intent.putExtra("objective", objective.getText().toString());
                    intent.putExtra("plan", plan.getText().toString());
                    Location loc = (Location) location.getSelectedItem();
                    intent.putExtra("location", loc.id);
                    Position pos = (Position) position.getSelectedItem();
                    intent.putExtra("position", pos.id);
                    ExternalReferral extRef = (ExternalReferral) externalReferral.getSelectedItem();
                    intent.putExtra("externalReferral", extRef.id);
                    InternalReferral intRef = (InternalReferral) internalReferral.getSelectedItem();
                    intent.putExtra("internalReferral", intRef.id);
                    intent.putExtra("reason", ((Reason) reason.getSelectedItem()).getCode());
                    intent.putExtra("followUp", ((FollowUp) followUp.getSelectedItem()).getCode());
                    startActivity(intent);
                    finish();
                }

            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(contactDate.getText().toString())){
            contactDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            contactDate.setError(null);
        }
        return isValid;
    }
}
