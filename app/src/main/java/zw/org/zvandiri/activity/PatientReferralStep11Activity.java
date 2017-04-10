package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Referral;
import zw.org.zvandiri.business.domain.ServicesReferred;
import zw.org.zvandiri.business.domain.util.ReferalType;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;

/**
 * Created by User on 4/6/2017.
 */
public class PatientReferralStep11Activity extends BaseActivity implements View.OnClickListener {

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
    private ArrayList<String> hivStiServicesReq;
    private ArrayList<String> oiArtReq;
    private ArrayList<String> srhReq;
    private ArrayList<String> laboratoryReq;
    private ArrayList<String> tbReq;
    private ArrayList<String> psychReq;
    private ArrayList<String> legalReq;
    private ArrayList<String> hivStiServicesAvailed;
    private ArrayList<String> oiArtAvailed;
    private TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_referral_step3);
        servicesReferred = (ListView) findViewById(R.id.list);
        save = (Button) findViewById(R.id.btn_save);
        label = (TextView) findViewById(R.id.label);
        label.setText("SRH Services");
        Intent intent = getIntent();
        hivStiServicesReq = intent.getStringArrayListExtra("hivStiServicesReq");
        oiArtReq = intent.getStringArrayListExtra("oiArtReq");
        srhReq = intent.getStringArrayListExtra("srhReq");
        laboratoryReq = intent.getStringArrayListExtra("laboratoryReq");
        tbReq = intent.getStringArrayListExtra("tbReq");
        psychReq = intent.getStringArrayListExtra("psychReq");
        legalReq = intent.getStringArrayListExtra("legalReq");
        hivStiServicesAvailed = intent.getStringArrayListExtra("hivStiServicesAvailed");
        oiArtAvailed = intent.getStringArrayListExtra("oiArtAvailed");
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        referralDate = intent.getStringExtra("referralDate");
        organisation = intent.getStringExtra("organisation");
        dateAttended = intent.getStringExtra("dateAttended");
        attendingOfficer = intent.getStringExtra("attendingOfficer");
        designation = intent.getStringExtra("designation");
        actionTaken = intent.getIntExtra("actionTaken", 1);
        servicesReferredArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, ServicesReferred.getByType(ReferalType.SRH_SERVICES));
        servicesReferred.setAdapter(servicesReferredArrayAdapter);
        servicesReferredArrayAdapter.notifyDataSetChanged();
        servicesReferred.setItemsCanFocus(false);
        servicesReferred.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        if(itemID != null){
            item = Referral.findById(itemID);
            ArrayList<ServicesReferred> list = (ArrayList<ServicesReferred>) ServicesReferred.SrhAvailed(Referral.findById(itemID));
            int count = servicesReferredArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                if(list.contains(current)){
                    servicesReferred.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("Update Referrals: Services Provided/Received"));
        }else{
            item = new Referral();
            setSupportActionBar(createToolBar("Add Referrals: Services Provided/Received"));
        }
        save.setOnClickListener(this);
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
        Intent intent = new Intent(PatientReferralStep11Activity.this, PatientReferralStep10Activity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        startActivity(intent);
        finish();
    }

    public void onClick(View v){
        Intent intent = new Intent(this, PatientReferralStep12Activity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        intent.putExtra("referralDate", referralDate);
        intent.putExtra("organisation", organisation);
        intent.putExtra("dateAttended", dateAttended);
        intent.putExtra("attendingOfficer", attendingOfficer);
        intent.putExtra("designation", designation);
        intent.putExtra("actionTaken", actionTaken);
        intent.putExtra("hivStiServicesReq", hivStiServicesReq);
        intent.putExtra("oiArtReq", oiArtReq);
        intent.putExtra("srhReq", srhReq);
        intent.putExtra("laboratoryReq", laboratoryReq);
        intent.putExtra("tbReq", tbReq);
        intent.putExtra("psychReq", psychReq);
        intent.putExtra("legalReq", legalReq);
        intent.putExtra("hivStiServicesAvailed", hivStiServicesAvailed);
        intent.putExtra("oiArtAvailed", oiArtAvailed);
        intent.putExtra("srhAvailed", getServicesReferred());
        startActivity(intent);
        finish();
    }

    private ArrayList<String> getServicesReferred(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < servicesReferred.getCount(); i++){
            if(servicesReferred.isItemChecked(i)){
                a.add(servicesReferredArrayAdapter.getItem(i).id);
            }else{
                a.remove(servicesReferredArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }
}
