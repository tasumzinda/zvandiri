package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.SupportGroup;
import zw.org.zvandiri.business.util.AppUtil;
/*
public class TransferPatientSupportGroupActivity extends BaseActivity implements View.OnClickListener {

    private Spinner province;
    private Spinner district;
    private Spinner supportGroup;
    private Button save;
    private Patient item;
    private String id;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_patient_support_group);
        province = (Spinner) findViewById(R.id.province);
        district = (Spinner) findViewById(R.id.district);
        supportGroup = (Spinner) findViewById(R.id.supportGroup);
        save = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        ArrayAdapter<Province> provinceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Province.getAll());
        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(provinceArrayAdapter);
        provinceArrayAdapter.notifyDataSetChanged();
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<District> districtArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, District.getByProvince((Province) province.getSelectedItem()));
                districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                district.setAdapter(districtArrayAdapter);
                districtArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<SupportGroup> supportGroupArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, SupportGroup.getByDistrict((District) district.getSelectedItem()));
                supportGroupArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                supportGroup.setAdapter(supportGroupArrayAdapter);
                supportGroupArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(id != null){
            item = Patient.findById(id);
            int i = 0;
            for(SupportGroup m : SupportGroup.getAll()){
                if(item.supportGroup != null && item.supportGroup.equals(supportGroup.getItemAtPosition(i))){
                    supportGroup.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        save.setOnClickListener(this);
        setSupportActionBar(createToolBar("Transfer Patient To Another Support Group"));
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


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(TransferPatientSupportGroupActivity.this, TransferPatientSupportGroupListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        save();
    }

    public void save(){
        item.supportGroup = (SupportGroup) supportGroup.getSelectedItem();
        item.pushed = false;
        item.save();
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent = new Intent(TransferPatientSupportGroupActivity.this, TransferPatientSupportGroupListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}*/
