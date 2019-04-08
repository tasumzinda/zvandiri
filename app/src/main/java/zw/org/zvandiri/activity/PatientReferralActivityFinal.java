package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.ReferralServicesReferredContract;
import zw.org.zvandiri.business.domain.ServicesReferred;
import zw.org.zvandiri.business.domain.util.ReferralActionTaken;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.ArrayList;
import java.util.Date;

public class PatientReferralActivityFinal extends BaseActivity implements View.OnClickListener {

    private ListView servicesReferred;
    private Referral item;
    private Button save;
    ArrayAdapter<ServicesReferred> servicesReferredArrayAdapter;
    private String itemID;
    private String id;
    private String name;
    private String referralDate;
    private String organisation;
    private String dateAttended;
    private String attendingOfficer;
    private String designation;
    private Integer actionTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_referral_final);
        servicesReferred = (ListView) findViewById(R.id.list);
        save = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        referralDate = intent.getStringExtra("referralDate");
        organisation = intent.getStringExtra("organisation");
        dateAttended = intent.getStringExtra("dateAttended");
        attendingOfficer = intent.getStringExtra("attendingOfficer");
        designation = intent.getStringExtra("designation");
        actionTaken = intent.getIntExtra("actionTaken", 1);
        servicesReferredArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, ServicesReferred.getAll());
        servicesReferred.setAdapter(servicesReferredArrayAdapter);
        servicesReferredArrayAdapter.notifyDataSetChanged();
        servicesReferred.setItemsCanFocus(false);
        servicesReferred.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        if(itemID != null){
            item = Referral.findById(itemID);
            ArrayList<ServicesReferred> list = (ArrayList<ServicesReferred>) ServicesReferred.findByReferral(Referral.findById(itemID));
            int count = servicesReferredArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                if(list.contains(current)){
                    servicesReferred.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("Update Referrals-Step 2"));
        }else{
            item = new Referral();
            setSupportActionBar(createToolBar("Add Referrals-Step 2"));
        }
        save.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onBackPressed(){
        Intent intent = new Intent(PatientReferralActivityFinal.this, PatientReferralActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == save.getId()){
            save();
        }
    }

    public void save(){
        String referralID = UUIDGen.generateUUID();
        if(itemID != null){
            item.id = itemID;
            item.dateModified = new Date();
            //item.isNew = false;
        }else{
            item.id = referralID;
            item.dateCreated = new Date();
            //item.isNew = true;
        }
        //item.pushed = false;
        item.referralDate = DateUtil.getDateFromString(referralDate);
        Patient p = Patient.findById(id);
        item.patient = p;
        item.actionTaken = ReferralActionTaken.get(actionTaken);
        item.attendingOfficer = attendingOfficer;
        item.designation = designation;
        item.organisation = organisation;
        item.dateAttended = DateUtil.getDateFromString(dateAttended);
        item.save();
        if(itemID != null){
            for(ReferralServicesReferredContract item : ReferralServicesReferredContract.findByReferral(Referral.findById(itemID))){
                item.delete();
            }
        }
        for (ServicesReferred s : getServicesReferred()){
            ReferralServicesReferredContract contract = new ReferralServicesReferredContract();
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(referralID);
            }
            contract.servicesReferred = s;
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        p.pushed = 1;
        p.save();
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent = new Intent(PatientReferralActivityFinal.this, PatientReferralListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    private ArrayList<ServicesReferred> getServicesReferred(){
        ArrayList<ServicesReferred> a = new ArrayList<>();
        for(int i = 0; i < servicesReferred.getCount(); i++){
            if(servicesReferred.isItemChecked(i)){
                a.add(servicesReferredArrayAdapter.getItem(i));
            }else{
                a.remove(servicesReferredArrayAdapter.getItem(i));
            }
        }
        return a;
    }
}
