package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.CareLevel;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

public class PatientContactActivityStep3 extends BaseActivity implements View.OnClickListener {

    private ListView assessments;
    private ArrayAdapter<Assessment> assessmentArrayAdapter;
    private String itemID;
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
    private Integer careLevel;
    private Button next;
    private Contact c;
    private ArrayList<String> enhanceds;
    private ArrayList<String> stables;
    private String lastClinicAppointmentDate;
    private Integer attendedClinicAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_contact_activity_step3);
        Intent intent = getIntent();
        lastClinicAppointmentDate = intent.getStringExtra("lastClinicAppointmentDate");
        attendedClinicAppointment = intent.getIntExtra("attendedClinicAppointment", 0);
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
        enhanceds = intent.getStringArrayListExtra("enhanceds");
        stables = intent.getStringArrayListExtra("stables");
        careLevel = intent.getIntExtra("careLevel", 1);
        assessments = (ListView) findViewById(R.id.assessments);
        assessmentArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, Assessment.getAll());
        assessments.setAdapter(assessmentArrayAdapter);
        assessmentArrayAdapter.notifyDataSetChanged();
        next = (Button) findViewById(R.id.btn_next);
        next.setOnClickListener(this);
        assessments.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assessments.setItemsCanFocus(false);
        if(itemID != null){
            c = Contact.findById(itemID);
            int i = 0;
            ArrayList<Assessment> assessmentList = (ArrayList<Assessment>) Assessment.findClinicalByContact(Contact.findById(itemID));
            int assessmentCount = assessmentArrayAdapter.getCount();
            for(i = 0; i < assessmentCount; i++){
                Assessment current = assessmentArrayAdapter.getItem(i);
                if(assessmentList.contains(current)){
                    assessments.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("Update Contact - Step 3"));
        }else{
            c = new Contact();
            setSupportActionBar(createToolBar("Add Contact - Step 3"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

   /* @Override
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
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    public void onBackPressed(){
        Intent intent = new Intent(PatientContactActivityStep3.this, PatientContactActivityStep2.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            Intent intent = new Intent(PatientContactActivityStep3.this, PatientContactActivityFinal.class);
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
            intent.putExtra("careLevel", careLevel);
            intent.putStringArrayListExtra("enhanceds", enhanceds);
            intent.putStringArrayListExtra("stables", stables);
            intent.putStringArrayListExtra("assessments", getAssessments());
            intent.putExtra("attendedClinicAppointment", attendedClinicAppointment);
            intent.putExtra("lastClinicAppointmentDate", lastClinicAppointmentDate);
            startActivity(intent);
            finish();
        }
    }

    private ArrayList<String> getAssessments(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < assessments.getCount(); i++){
            if(assessments.isItemChecked(i)){
                a.add(assessmentArrayAdapter.getItem(i).id);
            }else{
                a.remove(assessmentArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }
}
