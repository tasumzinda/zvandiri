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
public class PatientReferralStep8Activity extends BaseActivity implements View.OnClickListener {

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
            ArrayList<ServicesReferred> list = (ArrayList<ServicesReferred>) ServicesReferred.LegalReq(Referral.findById(itemID));
            int count = servicesReferredArrayAdapter.getCount();
            for(int i = 0; i < count; i++){
                ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                if(list.contains(current)){
                    servicesReferred.setItemChecked(i, true);
                }
            }
            setSupportActionBar(createToolBar("Update Referrals: Services Referred"));
        }else if(holder.legalReqId != null){
            if(holder.legalReqId != null){
                ArrayList<String> list = (ArrayList<String>) holder.legalReqId;
                int stableCount = servicesReferredArrayAdapter.getCount();
                for(int i = 0; i < stableCount; i++){
                    ServicesReferred current = servicesReferredArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        servicesReferred.setItemChecked(i, true);
                    }
                }
            }
            setSupportActionBar(createToolBar("Add Referrals: Services Referred"));
        }
        else{
            item = new Referral();
            setSupportActionBar(createToolBar("Add Referrals: Services Referred"));
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
        Intent intent = new Intent(PatientReferralStep8Activity.this, PatientReferralStep7Activity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        holder.legalReqId = getServicesReferred();
        intent.putExtra("referral", holder);
        startActivity(intent);
        finish();
    }

    public void onClick(View v){
        Intent intent = new Intent(this, PatientReferralStep9Activity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.DETAILS_ID, itemID);
        holder.legalReqId = getServicesReferred();
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
