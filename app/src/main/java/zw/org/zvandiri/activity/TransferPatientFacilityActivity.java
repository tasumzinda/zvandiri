package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;
/*
public class TransferPatientFacilityActivity extends BaseActivity implements View.OnClickListener {

    private Spinner province;
    private Spinner district;
    private Spinner primaryClinic;
    private Button save;
    private Patient item;
    private String id;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_patient_facility);
        province = (Spinner) findViewById(R.id.province);
        district = (Spinner) findViewById(R.id.district);
        primaryClinic = (Spinner) findViewById(R.id.primaryClinic);
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
                ArrayAdapter<Facility> supportGroupArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, Facility.getByDistrict((District) district.getSelectedItem()));
                supportGroupArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                primaryClinic.setAdapter(supportGroupArrayAdapter);
                supportGroupArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(id != null){
            item = Patient.findById(id);
            int i = 0;
            for(Facility m : Facility.getAll()){
                if(item.primaryClinic != null && item.primaryClinic.equals(primaryClinic.getItemAtPosition(i))){
                    primaryClinic.setSelection(i, true);
                    break;
                }
                i++;
            }
        }
        save.setOnClickListener(this);
        setSupportActionBar(createToolBar("Transfer Patient To Another Clinic"));
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
        Intent intent = new Intent(TransferPatientFacilityActivity.this, TransferPatientFacilityListActivity.class);
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
        item.primaryClinic = (Facility) primaryClinic.getSelectedItem();
        item.pushed = false;
        item.save();
        AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
        Intent intent = new Intent(TransferPatientFacilityActivity.this, TransferPatientFacilityListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }
}*/
