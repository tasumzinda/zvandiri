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
import zw.org.zvandiri.business.domain.util.HIVDisclosureLocation;
import zw.org.zvandiri.business.domain.util.TransmissionMode;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.MobileNumberFormat;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 4/3/2017.
 */
public class PatientRegStep2Activity extends BaseActivity implements View.OnClickListener{

    private EditText mobileNumber;
    private Spinner mobileOwner;
    private EditText ownerName;
    private EditText email;
    private Spinner mobileOwnerRelation;
    private EditText secondaryMobileNumber;
    private Spinner ownSecondaryMobile;
    private  EditText secondaryMobileOwnerName;
    private Spinner secondaryMobileownerRelation;
    private Button next;
    private String itemID;
    private TextView relationshipLabel;
    private TextView secondaryRelationshipLabel;
    private Patient holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step2);
        Intent intent = getIntent();
        holder = (Patient) intent.getSerializableExtra("patient");
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        next = (Button) findViewById(R.id.btn_save);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        ownerName = (EditText) findViewById(R.id.ownerName);
        email = (EditText) findViewById(R.id.email);
        secondaryMobileNumber = (EditText) findViewById(R.id.secondaryMobileNumber);
        secondaryMobileOwnerName = (EditText) findViewById(R.id.secondaryMobileOwnerName);
        mobileOwner = (Spinner) findViewById(R.id.mobileOwner);
        mobileOwnerRelation = (Spinner) findViewById(R.id.mobileOwnerRelation);
        ownSecondaryMobile = (Spinner) findViewById(R.id.ownSecondaryMobile);
        secondaryMobileownerRelation = (Spinner) findViewById(R.id.secondaryMobileownerRelation);
        relationshipLabel = (TextView) findViewById(R.id.relationshipLabel);
        secondaryRelationshipLabel = (TextView) findViewById(R.id.secondaryRelationshipLabel);
        ArrayAdapter<YesNo> mobileOwnerArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        mobileOwnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mobileOwner.setAdapter(mobileOwnerArrayAdapter);
        mobileOwnerArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Relationship> mobileOwnerRelationArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Relationship.getAll());
        mobileOwnerRelationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mobileOwnerRelation.setAdapter(mobileOwnerRelationArrayAdapter);
        mobileOwnerRelationArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<YesNo> ownSecondaryMobileArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        ownSecondaryMobileArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownSecondaryMobile.setAdapter(ownSecondaryMobileArrayAdapter);
        ownSecondaryMobileArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Relationship> secondaryMobileownerRelationArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Relationship.getAll());
        secondaryMobileownerRelationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secondaryMobileownerRelation.setAdapter(secondaryMobileownerRelationArrayAdapter);
        secondaryMobileownerRelationArrayAdapter.notifyDataSetChanged();
        if(holder.firstName != null){
            mobileNumber.setText(holder.mobileNumber);
            ownerName.setText(holder.ownerName);
            email.setText(holder.email);
            secondaryMobileNumber.setText(holder.secondaryMobileNumber);
            secondaryMobileOwnerName.setText(holder.secondaryMobileOwnerName);
            if(holder.mobileOwner != null){
                if(holder.mobileOwner.equals(YesNo.NO)){
                    ownerName.setVisibility(View.VISIBLE);
                    relationshipLabel.setVisibility(View.VISIBLE);
                    mobileOwnerRelation.setVisibility(View.VISIBLE);
                }else{
                    ownerName.setVisibility(View.GONE);
                    relationshipLabel.setVisibility(View.GONE);
                    mobileOwnerRelation.setVisibility(View.GONE);
                }
            }
            if(holder.ownSecondaryMobile != null){
                if(holder.ownSecondaryMobile.equals(YesNo.NO)){
                    secondaryMobileOwnerName.setVisibility(View.VISIBLE);
                    secondaryRelationshipLabel.setVisibility(View.VISIBLE);
                    secondaryMobileownerRelation.setVisibility(View.VISIBLE);
                }else{
                    secondaryMobileOwnerName.setVisibility(View.GONE);
                    secondaryRelationshipLabel.setVisibility(View.GONE);
                    secondaryMobileownerRelation.setVisibility(View.GONE);
                }
            }

            int i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.mobileOwner != null  && holder.mobileOwner.equals(mobileOwner.getItemAtPosition(i))){
                    mobileOwner.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Relationship m : Relationship.getAll()){
                if(holder.mobileOwnerRelationId != null  && holder.mobileOwnerRelationId.equals(((Relationship)mobileOwnerRelation.getItemAtPosition(i)).id)){
                    mobileOwnerRelation.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.ownSecondaryMobile != null  && holder.ownSecondaryMobile.equals(ownSecondaryMobile.getItemAtPosition(i))){
                    ownSecondaryMobile.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Relationship m : Relationship.getAll()){
                if(holder.secondaryMobileownerRelationId != null  && holder.secondaryMobileownerRelationId.equals(((Relationship)secondaryMobileownerRelation.getItemAtPosition(i)).id)){
                    secondaryMobileownerRelation.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        mobileOwner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mobileOwner.getSelectedItem().equals(YesNo.NO)){
                    ownerName.setVisibility(View.VISIBLE);
                    relationshipLabel.setVisibility(View.VISIBLE);
                    mobileOwnerRelation.setVisibility(View.VISIBLE);
                }else{
                    ownerName.setVisibility(View.GONE);
                    relationshipLabel.setVisibility(View.GONE);
                    mobileOwnerRelation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ownSecondaryMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(ownSecondaryMobile.getSelectedItem().equals(YesNo.NO)){
                    secondaryMobileOwnerName.setVisibility(View.VISIBLE);
                    secondaryRelationshipLabel.setVisibility(View.VISIBLE);
                    secondaryMobileownerRelation.setVisibility(View.VISIBLE);
                }else{
                    secondaryMobileOwnerName.setVisibility(View.GONE);
                    secondaryRelationshipLabel.setVisibility(View.GONE);
                    secondaryMobileownerRelation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add Contact Details Step 2 of 7 "));
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
        Intent intent = new Intent(PatientRegStep2Activity.this, PatientRegStep1Activity.class);
        holder.email = email.getText().toString();
        holder.mobileNumber = mobileNumber.getText().toString();
        holder.ownerName = ownerName.getText().toString();
        holder.secondaryMobileNumber = secondaryMobileNumber.getText().toString();
        holder.secondaryMobileOwnerName = secondaryMobileOwnerName.getText().toString();
        holder.mobileOwner = (YesNo) mobileOwner.getSelectedItem();
        if( ! secondaryMobileNumber.getText().toString().isEmpty()){
            holder.ownSecondaryMobile = (YesNo) ownSecondaryMobile.getSelectedItem();
        }
        if(mobileOwnerRelation.getVisibility() == View.VISIBLE){
            holder.mobileOwnerRelationId = ((Relationship) mobileOwnerRelation.getSelectedItem()).id;
        }
        if(secondaryMobileownerRelation.getVisibility() == View.VISIBLE){
            holder.secondaryMobileownerRelationId = ((Relationship) secondaryMobileownerRelation.getSelectedItem()).id;
        }
        intent.putExtra("patient", holder);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            if(validateLocal()){
                Intent intent = new Intent(PatientRegStep2Activity.this, PatientRegStep3Activity.class);
                intent.putExtra(AppUtil.DETAILS_ID, itemID);
                holder.email = email.getText().toString();
                holder.mobileNumber = mobileNumber.getText().toString();
                holder.ownerName = ownerName.getText().toString();
                holder.secondaryMobileNumber = secondaryMobileNumber.getText().toString();
                holder.secondaryMobileOwnerName = secondaryMobileOwnerName.getText().toString();
                holder.mobileOwner = (YesNo) mobileOwner.getSelectedItem();
                if( ! secondaryMobileNumber.getText().toString().isEmpty()){
                    holder.ownSecondaryMobile = (YesNo) ownSecondaryMobile.getSelectedItem();
                }
                if(mobileOwnerRelation.getVisibility() == View.VISIBLE){
                    holder.mobileOwnerRelationId = ((Relationship) mobileOwnerRelation.getSelectedItem()).id;
                }
                if(secondaryMobileownerRelation.getVisibility() == View.VISIBLE){
                    holder.secondaryMobileownerRelationId = ((Relationship) secondaryMobileownerRelation.getSelectedItem()).id;
                }
                intent.putExtra("patient", holder);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        String mobile = mobileNumber.getText().toString();
        if( ! mobile.isEmpty() && ! mobile.matches(MobileNumberFormat.ZIMBABWE)){
            mobileNumber.setError(getResources().getString(R.string.mobile_number_format));
            isValid = false;
        }else{
            mobileNumber.setError(null);
        }
        if(mobileOwner.getSelectedItem().equals(YesNo.NO) &&  ! mobile.isEmpty()){
            if(ownerName.getText().toString().isEmpty()){
                ownerName.setError(this.getString(R.string.required_field_error));
                isValid = false;
            }else{
                ownerName.setError(null);
            }
        }
        mobile = secondaryMobileNumber.getText().toString();
        if( ! mobile.isEmpty() && mobile.matches(MobileNumberFormat.ZIMBABWE)){
            secondaryMobileNumber.setError(getResources().getString(R.string.mobile_number_format));
            isValid = false;
        }else{
            secondaryMobileNumber.setError(null);
        }
        if(ownSecondaryMobile.getSelectedItem().equals(YesNo.NO) &&  ! mobile.isEmpty()){
            if(secondaryMobileOwnerName.getText().toString().isEmpty()){
                secondaryMobileOwnerName.setError(this.getString(R.string.required_field_error));
                isValid = false;
            }else{
                secondaryMobileOwnerName.setError(null);
            }
        }
        String emailAddres = email.getText().toString().trim();
        if( ! emailAddres.isEmpty() && Patient.findByEmail(emailAddres) != null){
            email.setError(getResources().getString(R.string.email_exist));
            isValid = false;
        }else{
            email.setError(null);
        }
        if( ! emailAddres.isEmpty()  && ! validateEmail(emailAddres)){
            email.setError(getResources().getString(R.string.email_format_error));
            isValid = false;
        }else{
            email.setError(null);
        }
        return isValid;
    }
}
