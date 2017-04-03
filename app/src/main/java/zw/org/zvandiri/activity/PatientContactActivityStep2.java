package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.CareLevel;
import zw.org.zvandiri.business.domain.util.FollowUp;
import zw.org.zvandiri.business.domain.util.Reason;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.ArrayList;

public class PatientContactActivityStep2 extends BaseActivity implements View.OnClickListener {


    private Spinner careLevel;
    private ListView stable;
    private ListView enhanced;
    private ListView intensive;
    private TextView stableLabel;
    private TextView enhancedLabel;
    private TextView intensiveLabel;
    private String itemID;
    private SparseBooleanArray stables;
    private SparseBooleanArray enhanceds;
    private SparseBooleanArray intensives;
    private String id;
    private String name;
    private String contactDate;
    private String subjective;
    private String objective;
    private String plan;
    private String location;
    private String externalReferral;
    private String internalReferral;
    private Integer reason;
    private Integer followUp;
    private String position;
    private Button next;
    private Contact c;
    private ArrayAdapter<Stable> stableArrayAdapter;
    private ArrayAdapter<Enhanced> enhancedArrayAdapter;
    private ArrayAdapter<Intensive> intensiveArrayAdapter;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_contact_activity_step2);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        contactDate = intent.getStringExtra("contactDate");
        subjective = intent.getStringExtra("subjective");
        objective = intent.getStringExtra("objective");
        plan = intent.getStringExtra("plan");
        location = intent.getStringExtra("location");
        externalReferral = intent.getStringExtra("externalReferral");
        internalReferral = intent.getStringExtra("internalReferral");
        reason = intent.getIntExtra("reason", 1);
        followUp = intent.getIntExtra("followUp", 1);
        position = intent.getStringExtra("position");
        careLevel = (Spinner) findViewById(R.id.careLevel);
        stable = (ListView) findViewById(R.id.stable);
        enhanced = (ListView) findViewById(R.id.enhanced);
        intensive = (ListView) findViewById(R.id.intensive);
        stableLabel = (TextView) findViewById(R.id.stableLabel);
        enhancedLabel = (TextView) findViewById(R.id.enhancedLabel);
        intensiveLabel = (TextView) findViewById(R.id.intensiveLabel);
        list = new ArrayList<>();
        next = (Button) findViewById(R.id.btn_next);
        next.setOnClickListener(this);
        ArrayAdapter<CareLevel> careLevelArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CareLevel.values());
        careLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        careLevel.setAdapter(careLevelArrayAdapter);
        careLevelArrayAdapter.notifyDataSetChanged();
        stableArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Stable.getAll());
        stable.setAdapter(stableArrayAdapter);
        stableArrayAdapter.notifyDataSetChanged();
        enhancedArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Enhanced.getAll());
        enhanced.setAdapter(enhancedArrayAdapter);
        enhancedArrayAdapter.notifyDataSetChanged();
        intensiveArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Intensive.getAll());
        intensive.setAdapter(intensiveArrayAdapter);
        intensiveArrayAdapter.notifyDataSetChanged();
        stables = new SparseBooleanArray();
        enhanceds = new SparseBooleanArray();
        intensives = new SparseBooleanArray();
        careLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (careLevel.getSelectedItem().equals(CareLevel.STABLE)) {
                    stable.setVisibility(View.VISIBLE);
                    stableLabel.setVisibility(View.VISIBLE);
                    enhanced.setVisibility(View.GONE);
                    enhancedLabel.setVisibility(View.GONE);
                    intensive.setVisibility(View.GONE);
                    intensiveLabel.setVisibility(View.GONE);
                } else if (careLevel.getSelectedItem().equals(CareLevel.ENHANCED)) {
                    enhanced.setVisibility(View.VISIBLE);
                    enhancedLabel.setVisibility(View.VISIBLE);
                    stable.setVisibility(View.GONE);
                    stableLabel.setVisibility(View.GONE);
                    intensive.setVisibility(View.GONE);
                    intensiveLabel.setVisibility(View.GONE);
                } else if (careLevel.getSelectedItem().equals(CareLevel.INTENSIVE)) {
                    intensive.setVisibility(View.VISIBLE);
                    intensiveLabel.setVisibility(View.VISIBLE);
                    enhanced.setVisibility(View.GONE);
                    enhancedLabel.setVisibility(View.GONE);
                    stable.setVisibility(View.GONE);
                    stableLabel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stable.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        stable.setItemsCanFocus(false);
        stable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                stables = stable.getCheckedItemPositions();
            }
        });
        enhanced.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        enhanced.setItemsCanFocus(false);
        enhanced.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                enhanceds = enhanced.getCheckedItemPositions();
            }
        });
        intensive.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        intensive.setItemsCanFocus(false);
        intensive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                intensives = intensive.getCheckedItemPositions();
            }
        });
        if(itemID != null){
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
            int stableCount = stableArrayAdapter.getCount();
            for(i = 0; i < stableCount; i++){
                Stable current = stableArrayAdapter.getItem(i);
                if(stables.contains(current)){
                    stable.setItemChecked(i, true);
                }
            }
            ArrayList<Enhanced> enhanceds = (ArrayList<Enhanced>) Enhanced.findByContact(Contact.findById(itemID));
            int enhancedCount = enhancedArrayAdapter.getCount();
            for(i = 0; i < enhancedCount; i++){
                Enhanced current = enhancedArrayAdapter.getItem(i);
                if(enhanceds.contains(current)){
                    enhanced.setItemChecked(i, true);
                }
            }
            ArrayList<Intensive> intensives = (ArrayList<Intensive>) Intensive.findByContact(Contact.findById(itemID));
            int intensiveCount = intensiveArrayAdapter.getCount();
            for(i = 0; i < intensiveCount; i++){
                Intensive current = intensiveArrayAdapter.getItem(i);
                if(intensives.contains(current)){
                    intensive.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("Update Contact - Step 2"));
        }else{
            c = new Contact();
            setSupportActionBar(createToolBar("Add Contact - Step 2"));
        }

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
        Intent intent = new Intent(PatientContactActivityStep2.this, PatientContactActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            Intent intent = new Intent(PatientContactActivityStep2.this, PatientContactActivityStep3.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            intent.putExtra(AppUtil.DETAILS_ID, itemID);
            intent.putExtra("contactDate", contactDate);
            intent.putExtra("subjective", subjective);
            intent.putExtra("objective", objective);
            intent.putExtra("plan", plan);
            intent.putExtra("location", location);
            intent.putExtra("position", position);
            intent.putExtra("externalReferral", externalReferral);
            intent.putExtra("internalReferral", internalReferral);
            intent.putExtra("reason", reason);
            intent.putExtra("followUp", followUp);
            intent.putExtra("careLevel", ((CareLevel) careLevel.getSelectedItem()).getCode());
            intent.putStringArrayListExtra("enhanceds", getEnhanceds());
            intent.putStringArrayListExtra("intensives", getIntensives());
            intent.putStringArrayListExtra("stables", getStables());
            startActivity(intent);
            finish();
        }
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
    private ArrayList<String> getIntensives(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < intensive.getCount(); i++){
            if(intensive.isItemChecked(i)){
                a.add(intensiveArrayAdapter.getItem(i).id);
            }else{
                a.remove(intensiveArrayAdapter.getItem(i).id);
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
}
