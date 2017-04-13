package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Relationship;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;

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
    private Patient item;
    private String dateOfBirth;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer gender;
    private TextView relationshipLabel;
    private TextView secondaryRelationshipLabel;
    private String OINumber;
    private Patient holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step2);
        Intent intent = getIntent();
        /*firstName = intent.getStringExtra("firstName");
        middleName = intent.getStringExtra("middleName");
        lastName = intent.getStringExtra("lastName");
        dateOfBirth = intent.getStringExtra("dateOfBirth");
        gender = intent.getIntExtra("gender", 0);
        OINumber = intent.getStringExtra("OINumber");*/
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
            //item = Patient.findById(itemID);
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
                if(holder.mobileOwnerRelation != null  && holder.mobileOwnerRelation.equals(mobileOwnerRelation.getItemAtPosition(i))){
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
                if(holder.secondaryMobileownerRelation != null  && holder.secondaryMobileownerRelation.equals(secondaryMobileownerRelation.getItemAtPosition(i))){
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
                /*intent.putExtra("dateOfBirth", dateOfBirth);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("middleName", middleName);
                intent.putExtra("gender", gender);*/
                holder.email = email.getText().toString();
                holder.mobileNumber = mobileNumber.getText().toString();
                holder.ownerName = ownerName.getText().toString();
                holder.secondaryMobileNumber = secondaryMobileNumber.getText().toString();
                holder.secondaryMobileOwnerName = secondaryMobileOwnerName.getText().toString();
                holder.mobileOwner = (YesNo) mobileOwner.getSelectedItem();
                holder.ownSecondaryMobile = (YesNo) ownSecondaryMobile.getSelectedItem();
                /*intent.putExtra("email", email.getText().toString());
                intent.putExtra("mobileNumber", mobileNumber.getText().toString());
                intent.putExtra("ownerName", ownerName.getText().toString());
                intent.putExtra("secondaryMobileNumber", secondaryMobileNumber.getText().toString());
                intent.putExtra("secondaryMobileOwnerName", secondaryMobileOwnerName.getText().toString());
                intent.putExtra("mobileOwner", ((YesNo) mobileOwner.getSelectedItem()).getCode());
                intent.putExtra("ownSecondaryMobile", ((YesNo) ownSecondaryMobile.getSelectedItem()).getCode());*/
                if(mobileOwnerRelation.getVisibility() == View.VISIBLE){
                    //intent.putExtra("mobileOwnerRelation", ((Relationship) mobileOwnerRelation.getSelectedItem()).id);
                    holder.mobileOwnerRelation = (Relationship) mobileOwnerRelation.getSelectedItem();
                }
                if(secondaryMobileownerRelation.getVisibility() == View.VISIBLE){
                    //intent.putExtra("secondaryMobileownerRelation", ((Relationship) secondaryMobileownerRelation.getSelectedItem()).id);
                    holder.secondaryMobileownerRelation = (Relationship) secondaryMobileownerRelation.getSelectedItem();
                }
                //intent.putExtra("OINumber", OINumber);
                intent.putExtra("patient", holder);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if(mobileOwner.getSelectedItem().equals(YesNo.NO) &&  ! mobileNumber.getText().toString().isEmpty()){
            if(ownerName.getText().toString().isEmpty()){
                ownerName.setError(this.getString(R.string.required_field_error));
                isValid = false;
            }else{
                ownerName.setError(null);
            }
        }
        if(ownSecondaryMobile.getSelectedItem().equals(YesNo.NO) &&  ! secondaryMobileNumber.getText().toString().isEmpty()){
            if(secondaryMobileOwnerName.getText().toString().isEmpty()){
                secondaryMobileOwnerName.setError(this.getString(R.string.required_field_error));
                isValid = false;
            }else{
                secondaryMobileOwnerName.setError(null);
            }
        }
        return isValid;
    }
}
