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
public class PatientReferralStep9Activity extends BaseActivity implements View.OnClickListener {

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
        label = (TextView) findViewById(R.id.label);
        label.setText("HIV/STI Prevention");
        Intent intent = getIntent();
        holder = (Referral) intent.getSerializableExtra("referral");
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        servicesReferredArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, ServicesReferred.getByType(ReferalType.HIV_STI_PREVENTION));
        servicesReferred.setAdapter(servicesReferredArrayAdapter);
        servicesReferredArrayAdapter.notifyDataSetChanged();
        servicesReferred.setItemsCanFocus(false);
        servicesReferred.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        if(itemID != null){
            item = Referral.findById(itemID);
            ArrayList<ServicesReferred> list = (ArrayList<ServicesReferred>) ServicesReferred.HivStiAvailed(Referral.findById(itemID));
            int count = servicesReferredArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                if(list.contains(current)){
                    servicesReferred.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("Update Referrals: Services Provided/Received"));
        }else if(holder.hivStiServicesAvailedId != null){
            if(holder.hivStiServicesAvailedId != null){
                ArrayList<String> list = (ArrayList<String>) holder.hivStiServicesAvailedId;
                int stableCount = servicesReferredArrayAdapter.getCount();
                for(int i = 0; i < stableCount; i++){
                    ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        servicesReferred.setItemChecked(i, true);
                    }
                }
            }
            setSupportActionBar(createToolBar("Add Referrals: Services Provided/Received"));
        }
        else{
            item = new Referral();
            setSupportActionBar(createToolBar("Add Referrals: Services Provided/Received"));
        }
        save.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onBackPressed(){
        Intent intent = new Intent(PatientReferralStep9Activity.this, PatientReferralStep8Activity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        holder.hivStiServicesAvailedId = getServicesReferred();
        intent.putExtra("referral", holder);
        startActivity(intent);
        finish();
    }

    public void onClick(View v){
        Intent intent = new Intent(this, PatientReferralStep10Activity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        holder.hivStiServicesAvailedId = getServicesReferred();
        intent.putExtra("referral", holder);
        startActivity(intent);
        finish();
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
}
