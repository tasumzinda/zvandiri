package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.ArvHist;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Relationship;
import zw.org.zvandiri.business.domain.SocialHist;
import zw.org.zvandiri.business.domain.util.AbuseOutcome;
import zw.org.zvandiri.business.domain.util.AbuseType;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Date;
import java.util.List;

public class SocialHistActivity extends BaseActivity implements View.OnClickListener {

    EditText liveWith;
    Spinner relationship;
    Spinner abuse;
    Spinner abuseType;
    Spinner disclosure;
    Spinner abuseOutcome;
    Spinner feelSafe;
    Button button;
    String id;
    String name;
    private SocialHist item;
    private String itemID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        setContentView(R.layout.activity_social_hist);
        liveWith = (EditText) findViewById(R.id.liveWith);
        relationship = (Spinner) findViewById(R.id.relationship);
        abuse = (Spinner) findViewById(R.id.abuse);
        abuseType = (Spinner) findViewById(R.id.abuseType);
        disclosure = (Spinner) findViewById(R.id.disclosure);
        abuseOutcome = (Spinner) findViewById(R.id.abuseOutcome);
        feelSafe = (Spinner) findViewById(R.id.feelSafe);
        button = (Button) findViewById(R.id.btn_save);
        button.setOnClickListener(this);
        ArrayAdapter<YesNo> yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disclosure.setAdapter(yesNoArrayAdapter);
        feelSafe.setAdapter(yesNoArrayAdapter);
        abuse.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<AbuseOutcome> abuseOutcomeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, AbuseOutcome.values());
        abuseOutcomeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        abuseOutcome.setAdapter(abuseOutcomeArrayAdapter);
        abuseOutcomeArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<AbuseType> abuseTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, AbuseType.values());
        abuseTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        abuseType.setAdapter(abuseTypeArrayAdapter);
        abuseTypeArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Relationship> relationshipArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Relationship.getAll());
        relationshipArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationship.setAdapter(relationshipArrayAdapter);
        relationshipArrayAdapter.notifyDataSetChanged();
        abuse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(abuse.getSelectedItem().equals(YesNo.NO)){
                    abuseType.setVisibility(View.GONE);
                    disclosure.setVisibility(View.GONE);
                    abuseOutcome.setVisibility(View.GONE);
                    feelSafe.setVisibility(View.GONE);
                }else{
                    abuseType.setVisibility(View.VISIBLE);
                    disclosure.setVisibility(View.VISIBLE);
                    abuseOutcome.setVisibility(View.VISIBLE);
                    feelSafe.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemID != null){
            item = SocialHist.getItem(itemID);
            liveWith.setText(item.liveWith);
            int i = 0;
            for(Relationship r: Relationship.getAll()){
                if(item.relationship != null && item.relationship.equals(relationship.getItemAtPosition(i))){
                    relationship.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m: YesNo.values()){
                if(item.abuse != null && item.abuse.equals(abuse.getItemAtPosition(i))){
                    abuse.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(AbuseType m: AbuseType.values()){
                if(item.abuseType != null && item.abuseType.equals(abuseType.getItemAtPosition(i))){
                    abuseType.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m: YesNo.values()){
                if(item.dosclosure != null && item.dosclosure.equals(disclosure.getItemAtPosition(i))){
                    disclosure.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(AbuseOutcome m: AbuseOutcome.values()){
                if(item.abuseOutcome != null && item.abuse.equals(abuseOutcome.getItemAtPosition(i))){
                    abuseOutcome.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m: YesNo.values()){
                if(item.feelSafe != null && item.feelSafe.equals(feelSafe.getItemAtPosition(i))){
                    feelSafe.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Social History"));
        }else{
            item = new SocialHist();
            setSupportActionBar(createToolBar("Add Social History"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == button.getId()){
            save();
        }
    }

    public boolean validate(){
        boolean isValid = true;
        if(liveWith.getText().toString().isEmpty()){
            liveWith.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            liveWith.setError(null);
        }
        return isValid;
    }

    public void save(){
        if(validate()){
            item.abuse = (YesNo) abuse.getSelectedItem();
            item.abuseOutcome = (AbuseOutcome) abuseOutcome.getSelectedItem();
            item.abuseType = (AbuseType) abuseType.getSelectedItem();
            item.dosclosure = (YesNo) disclosure.getSelectedItem();
            item.feelSafe = (YesNo) feelSafe.getSelectedItem();
            item.liveWith = liveWith.getText().toString();
            if(itemID != null){
                item.id = itemID;
                item.dateModified = new Date();
                item.isNew = false;
            }else{
                item.id = UUIDGen.generateUUID();
                item.dateCreated = new Date();
                item.isNew = true;
            }
            item.relationship = (Relationship) relationship.getSelectedItem();
            Patient p = Patient.findById(id);
            item.patient = p;
            item.pushed = false;
            item.save();
            p.pushed = false;
            p.save();
            AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
            Intent intent = new Intent(SocialHistActivity.this, SocialHistListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SocialHistActivity.this, SocialHistListActivity.class);
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
}
