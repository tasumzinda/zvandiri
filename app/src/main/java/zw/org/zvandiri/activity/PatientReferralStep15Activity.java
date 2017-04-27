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
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.ActionTaken;
import zw.org.zvandiri.business.domain.util.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 4/6/2017.
 */
public class PatientReferralStep15Activity extends BaseActivity implements View.OnClickListener {

    private ListView servicesReferred;
    private Referral item;
    private Button save;
    ArrayAdapter<ServicesReferred> servicesReferredArrayAdapter;
    private String itemID;
    private String id;
    private String name;
    private TextView label;
    private Referral holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_referral_step3);
        servicesReferred = (ListView) findViewById(R.id.list);
        save = (Button) findViewById(R.id.btn_save);
        save.setText("Save");
        label = (TextView) findViewById(R.id.label);
        label.setText("Legal Services");
        Intent intent = getIntent();
        holder = (Referral) intent.getSerializableExtra("referral");
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        servicesReferredArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, ServicesReferred.getByType(ReferalType.LEGAL_SUPPORT));
        servicesReferred.setAdapter(servicesReferredArrayAdapter);
        servicesReferredArrayAdapter.notifyDataSetChanged();
        servicesReferred.setItemsCanFocus(false);
        servicesReferred.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        if(itemID != null){
            item = Referral.findById(itemID);
            ArrayList<ServicesReferred> list = (ArrayList<ServicesReferred>) ServicesReferred.LegalAvailed(Referral.findById(itemID));
            int count = servicesReferredArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                if(list.contains(current)){
                    servicesReferred.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("Update Referrals: Services Provided/Received"));
        }else if(holder.legalAvailed != null){
            ArrayList<ServicesReferred> list = (ArrayList<ServicesReferred>) holder.legalAvailed;
            ArrayList<String> list1 = new ArrayList<>();
            for(ServicesReferred s : list){
                list1.add(s.name);
            }
            int count = servicesReferredArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                if(list1.contains(current.name)){
                    servicesReferred.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("Add Referrals: Services Provided/Received Final"));
        }
        else{
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
        Intent intent = new Intent(PatientReferralStep15Activity.this, PatientReferralStep14Activity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        holder.legalAvailed = getServicesReferred();
        intent.putExtra("referral", holder);
        startActivity(intent);
        finish();
    }

    public void onClick(View v){
        int size = holder.hivStiServicesReq.size() + holder.oiArtReq.size() + holder.srhReq.size() + holder.laboratoryReq.size()
                + holder.tbReq.size() + holder.psychReq.size() + holder.legalReq.size();
         if(size > 0){
             save();
         }else{
             AppUtil.createShortNotification(getApplicationContext(), "Please select at least one service referred");
         }

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

    public void save(){
        String contactId = UUIDGen.generateUUID();
        holder.id = contactId;
        Patient p = Patient.findById(id);
        holder.patient = p;
        holder.save();
        /*if(itemID != null){
            for(ReferralHivStiServicesAvailedContract c : ReferralHivStiServicesAvailedContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralHivStiServicesReqContract c : ReferralHivStiServicesReqContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralLaboratoryAvailedContract c : ReferralLaboratoryAvailedContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralLaboratoryReqContract c : ReferralLaboratoryReqContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralLegalAvailedContract c : ReferralLegalAvailedContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralLegalReqContract c : ReferralLegalReqContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralOIArtAvailedContract c : ReferralOIArtAvailedContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralOIArtReqContract c : ReferralOIArtReqContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralPsychAvailedContract c : ReferralPsychAvailedContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralPsychReqContract c : ReferralPsychReqContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralSrhAvailedContract c : ReferralSrhAvailedContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralSrhReqContract c : ReferralSrhReqContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralTbAvailedContract c : ReferralTbAvailedContract.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
            for(ReferralTbReqContact c : ReferralTbReqContact.findByReferral(Referral.findById(itemID))){
                c.delete();
            }
        }*/
        for(int i = 0; i < holder.hivStiServicesReq.size(); i++){
            ReferralHivStiServicesReqContract contract = new ReferralHivStiServicesReqContract();
            contract.hivStiServicesReq = holder.hivStiServicesReq.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.oiArtReq.size(); i++){
            ReferralOIArtReqContract contract = new ReferralOIArtReqContract();
            contract.oiArtReq = holder.oiArtReq.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.srhReq.size(); i++){
            ReferralSrhReqContract contract = new ReferralSrhReqContract();
            contract.srhReq = holder.srhReq.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.laboratoryReq.size(); i++){
            ReferralLaboratoryReqContract contract = new ReferralLaboratoryReqContract();
            contract.laboratoryReq = holder.laboratoryReq.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.tbReq.size(); i++){
            ReferralTbReqContact contract = new ReferralTbReqContact();
            contract.tbReq = holder.tbReq.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.psychReq.size(); i++){
            ReferralPsychReqContract contract = new ReferralPsychReqContract();
            contract.psychReq = holder.psychReq.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.legalReq.size(); i++){
            ReferralLegalReqContract contract = new ReferralLegalReqContract();
            contract.legalReq = holder.legalReq.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.hivStiServicesAvailed.size(); i++){
            ReferralHivStiServicesAvailedContract contract = new ReferralHivStiServicesAvailedContract();
            contract.hivStiServicesAvailed = holder.hivStiServicesAvailed.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.oiArtAvailed.size(); i++){
            ReferralOIArtAvailedContract contract = new ReferralOIArtAvailedContract();
            contract.oiArtAvailed = holder.oiArtAvailed.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.srhAvailed.size(); i++){
            ReferralSrhAvailedContract contract = new ReferralSrhAvailedContract();
            contract.srhAvailed = holder.srhAvailed.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.laboratoryAvailed.size(); i++){
            ReferralLaboratoryAvailedContract contract = new ReferralLaboratoryAvailedContract();
            contract.laboratoryAvailed = holder.laboratoryAvailed.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.tbAvailed.size(); i++){
            ReferralTbAvailedContract contract = new ReferralTbAvailedContract();
            contract.tbAvailed = holder.tbAvailed.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.psychAvailed.size(); i++){
            ReferralPsychAvailedContract contract = new ReferralPsychAvailedContract();
            contract.psychAvailed = holder.psychAvailed.get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < getServicesReferred().size(); i++){
            ReferralLegalAvailedContract contract = new ReferralLegalAvailedContract();
            contract.legalAvailed = getServicesReferred().get(i);
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent = new Intent(this, PatientReferralListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}
