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
        }else if(holder.legalAvailedId != null){
            if(holder.legalAvailedId != null){
                ArrayList<String> list = (ArrayList<String>) holder.legalAvailedId;
                int stableCount = servicesReferredArrayAdapter.getCount();
                for(int i = 0; i < stableCount; i++){
                    ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        servicesReferred.setItemChecked(i, true);
                    }
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

    public void onBackPressed(){
        Intent intent = new Intent(PatientReferralStep15Activity.this, PatientReferralStep14Activity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        holder.legalAvailedId = getServicesReferred();
        intent.putExtra("referral", holder);
        startActivity(intent);
        finish();
    }

    public void onClick(View v){
        if(validate()){
            save();
        }
    }

    public boolean validate(){
        boolean isValid = true;
        int size = holder.hivStiServicesReqId.size() + holder.oiArtReqId.size() + holder.srhReqId.size() + holder.laboratoryReqId.size()
                + holder.tbReqId.size() + holder.psychReqId.size() + holder.legalReqId.size();
        if(size == 0){
            AppUtil.createShortNotification(getApplicationContext(), "Please select at least one service referred");
            isValid = false;
        }
        return isValid;
    }

    private ArrayList<String> getServicesReferred(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < servicesReferred.getCount(); i++){
            if(servicesReferred.isItemChecked(i)){
                a.add(servicesReferredArrayAdapter.getItem(i).id);
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
        holder.isNew = 1;
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
        for(int i = 0; i < holder.hivStiServicesReqId.size(); i++){
            ReferralHivStiServicesReqContract contract = new ReferralHivStiServicesReqContract();
            contract.hivStiServicesReq = ServicesReferred.getItem(holder.hivStiServicesReqId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.oiArtReqId.size(); i++){
            ReferralOIArtReqContract contract = new ReferralOIArtReqContract();
            contract.oiArtReq = ServicesReferred.getItem(holder.oiArtReqId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.srhReqId.size(); i++){
            ReferralSrhReqContract contract = new ReferralSrhReqContract();
            contract.srhReq = ServicesReferred.getItem(holder.srhReqId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.laboratoryReqId.size(); i++){
            ReferralLaboratoryReqContract contract = new ReferralLaboratoryReqContract();
            contract.laboratoryReq = ServicesReferred.getItem(holder.laboratoryReqId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.tbReqId.size(); i++){
            ReferralTbReqContact contract = new ReferralTbReqContact();
            contract.tbReq = ServicesReferred.getItem(holder.tbReqId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.psychReqId.size(); i++){
            ReferralPsychReqContract contract = new ReferralPsychReqContract();
            contract.psychReq = ServicesReferred.getItem(holder.psychReqId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.legalReqId.size(); i++){
            ReferralLegalReqContract contract = new ReferralLegalReqContract();
            contract.legalReq = ServicesReferred.getItem(holder.legalReqId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.hivStiServicesAvailedId.size(); i++){
            ReferralHivStiServicesAvailedContract contract = new ReferralHivStiServicesAvailedContract();
            contract.hivStiServicesAvailed = ServicesReferred.getItem(holder.hivStiServicesAvailedId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.oiArtAvailedId.size(); i++){
            ReferralOIArtAvailedContract contract = new ReferralOIArtAvailedContract();
            contract.oiArtAvailed = ServicesReferred.getItem(holder.oiArtAvailedId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.srhAvailedId.size(); i++){
            ReferralSrhAvailedContract contract = new ReferralSrhAvailedContract();
            contract.srhAvailed = ServicesReferred.getItem(holder.srhAvailedId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.laboratoryAvailedId.size(); i++){
            ReferralLaboratoryAvailedContract contract = new ReferralLaboratoryAvailedContract();
            contract.laboratoryAvailed = ServicesReferred.getItem(holder.laboratoryAvailedId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.tbAvailedId.size(); i++){
            ReferralTbAvailedContract contract = new ReferralTbAvailedContract();
            contract.tbAvailed = ServicesReferred.getItem(holder.tbAvailedId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < holder.psychAvailedId.size(); i++){
            ReferralPsychAvailedContract contract = new ReferralPsychAvailedContract();
            contract.psychAvailed = ServicesReferred.getItem(holder.psychAvailedId.get(i));
            contract.referral = Referral.findById(contactId);
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < getServicesReferred().size(); i++){
            ReferralLegalAvailedContract contract = new ReferralLegalAvailedContract();
            contract.legalAvailed = ServicesReferred.getItem(getServicesReferred().get(i));
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
