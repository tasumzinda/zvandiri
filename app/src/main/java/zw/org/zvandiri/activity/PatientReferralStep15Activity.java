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
    private ArrayList<String> srhAvailed;
    private ArrayList<String> laboratoryAvailed;
    private ArrayList<String> tbAvailed;
    private ArrayList<String> psychAvailed;
    private TextView label;

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
        hivStiServicesReq = intent.getStringArrayListExtra("hivStiServicesReq");
        oiArtReq = intent.getStringArrayListExtra("oiArtReq");
        srhReq = intent.getStringArrayListExtra("srhReq");
        laboratoryReq = intent.getStringArrayListExtra("laboratoryReq");
        tbReq = intent.getStringArrayListExtra("tbReq");
        psychReq = intent.getStringArrayListExtra("psychReq");
        legalReq = intent.getStringArrayListExtra("legalReq");
        hivStiServicesAvailed = intent.getStringArrayListExtra("hivStiServicesAvailed");
        oiArtAvailed = intent.getStringArrayListExtra("oiArtAvailed");
        srhAvailed = intent.getStringArrayListExtra("srhAvailed");
        laboratoryAvailed = intent.getStringArrayListExtra("laboratoryAvailed");
        tbAvailed = intent.getStringArrayListExtra("tbAvailed");
        psychAvailed = intent.getStringArrayListExtra("psychAvailed");
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
            setSupportActionBar(createToolBar("Update Referrals-Step 2: Services Provided/Received"));
        }else{
            item = new Referral();
            setSupportActionBar(createToolBar("Add Referrals-Step 2: Services Provided/Received"));
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
        startActivity(intent);
        finish();
    }

    public void onClick(View v){

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
        if(itemID != null){
            item.id = itemID;
            item.dateModified = new Date();
            item.isNew = false;
        }else{
            item.id = contactId;
            item.dateCreated = new Date();
            item.isNew = true;
        }
        item.actionTaken = ReferralActionTaken.get(actionTaken);
        if(referralDate != null){
            item.referralDate = DateUtil.getDateFromString(referralDate);
        }
        if(dateAttended != null){
            item.dateAttended = DateUtil.getDateFromString(dateAttended);
        }
        item.organisation = organisation;
        Patient p = Patient.findById(id);
        item.patient = p;
        item.attendingOfficer = attendingOfficer;
        item.designation = designation;
        item.pushed = false;
        item.save();
        if(itemID != null){
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
        }
        for(int i = 0; i < hivStiServicesReq.size(); i++){
            ReferralHivStiServicesReqContract contract = new ReferralHivStiServicesReqContract();
            contract.hivStiServicesReq = ServicesReferred.getItem(hivStiServicesReq.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < oiArtReq.size(); i++){
            ReferralOIArtReqContract contract = new ReferralOIArtReqContract();
            contract.oiArtReq = ServicesReferred.getItem(oiArtReq.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < srhReq.size(); i++){
            ReferralSrhReqContract contract = new ReferralSrhReqContract();
            contract.srhReq = ServicesReferred.getItem(srhReq.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < laboratoryReq.size(); i++){
            ReferralLaboratoryReqContract contract = new ReferralLaboratoryReqContract();
            contract.laboratoryReq = ServicesReferred.getItem(laboratoryReq.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < tbReq.size(); i++){
            ReferralTbReqContact contract = new ReferralTbReqContact();
            contract.tbReq = ServicesReferred.getItem(tbReq.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < psychReq.size(); i++){
            ReferralPsychReqContract contract = new ReferralPsychReqContract();
            contract.psychReq = ServicesReferred.getItem(psychReq.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < legalReq.size(); i++){
            ReferralLegalReqContract contract = new ReferralLegalReqContract();
            contract.legalReq = ServicesReferred.getItem(legalReq.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < hivStiServicesAvailed.size(); i++){
            ReferralHivStiServicesAvailedContract contract = new ReferralHivStiServicesAvailedContract();
            contract.hivStiServicesAvailed = ServicesReferred.getItem(hivStiServicesAvailed.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < oiArtAvailed.size(); i++){
            ReferralOIArtAvailedContract contract = new ReferralOIArtAvailedContract();
            contract.oiArtAvailed = ServicesReferred.getItem(oiArtAvailed.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < srhAvailed.size(); i++){
            ReferralSrhAvailedContract contract = new ReferralSrhAvailedContract();
            contract.srhAvailed = ServicesReferred.getItem(srhAvailed.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < laboratoryAvailed.size(); i++){
            ReferralLaboratoryAvailedContract contract = new ReferralLaboratoryAvailedContract();
            contract.laboratoryAvailed = ServicesReferred.getItem(laboratoryAvailed.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < tbAvailed.size(); i++){
            ReferralTbAvailedContract contract = new ReferralTbAvailedContract();
            contract.tbAvailed = ServicesReferred.getItem(tbAvailed.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        for(int i = 0; i < psychAvailed.size(); i++){
            ReferralPsychAvailedContract contract = new ReferralPsychAvailedContract();
            contract.psychAvailed = ServicesReferred.getItem(psychAvailed.get(i));
            if(itemID != null){
                contract.referral = Referral.findById(itemID);
            }else{
                contract.referral = Referral.findById(contactId);
            }
            contract.id = UUIDGen.generateUUID();
            contract.save();
        }
        p.pushed = false;
        p.save();
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent = new Intent(this, PatientReferralListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}
