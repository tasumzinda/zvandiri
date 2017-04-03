package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Facility;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Relationship;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.util.AppUtil;
/*
public class PrimaryCareGiverActivity extends BaseActivity implements View.OnClickListener {

    private EditText firstName;
    private EditText lastName;
    private EditText mobileNumber;
    private Spinner gender;
    private Spinner relationship;
    private Button save;
    private String id;
    private String name;
    private Patient item;
    EditText [] fields;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_care_giver);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        mobileNumber = (EditText) findViewById(R.id.mobile_number);
        gender = (Spinner) findViewById(R.id.gender);
        relationship = (Spinner) findViewById(R.id.relationship);
        fields = new EditText[] {firstName, lastName, mobileNumber};
        save = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        ArrayAdapter<Gender> genderArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Gender.values());
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderArrayAdapter);
        genderArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Relationship> relationshipArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Relationship.getAll());
        relationshipArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationship.setAdapter(relationshipArrayAdapter);
        relationshipArrayAdapter.notifyDataSetChanged();
        save.setOnClickListener(this);
        if(id != null){
            item = Patient.findById(id);
            firstName.setText(item.pfirstName);
            lastName.setText(item.plastName);
            mobileNumber.setText(item.pmobileNumber);
            int i = 0;
            for(Gender m : Gender.values()){
                if(item.pgender != null && item.pgender.equals(gender.getItemAtPosition(i))){
                    gender.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Relationship m : Relationship.getAll()){
                if(item.relationship != null && item.relationship.equals(relationship.getItemAtPosition(i))){
                    relationship.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        setSupportActionBar(createToolBar("Add Primary Care Giver"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(PrimaryCareGiverActivity.this, PrimaryCareGiverListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
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

    @Override
    public void onClick(View view) {
        save();
    }

    public void save(){
        if(validate(fields)){
            item.pgender = (Gender) gender.getSelectedItem();
            item.relationship = (Relationship) relationship.getSelectedItem();
            item.pfirstName = firstName.getText().toString();
            item.plastName = lastName.getText().toString();
            item.pmobileNumber = mobileNumber.getText().toString();
            item.id = id;
            item.pushed = false;
            item.save();
            AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
            Intent intent = new Intent(PrimaryCareGiverActivity.this, PrimaryCareGiverListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }
}*/
