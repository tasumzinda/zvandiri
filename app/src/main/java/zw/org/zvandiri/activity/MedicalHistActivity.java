package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedicalHistActivity extends BaseActivity implements View.OnClickListener  {

    EditText hospWhen;
    Spinner province;
    Spinner district;
    Spinner primaryClinic;
    Spinner hospCause;
    Button save;
    String id;
    String name;
    DatePickerDialog dialog;
    private MedicalHist item;
    private String itemID;
    private EditText[] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_hist);
        hospWhen = (EditText) findViewById(R.id.hospWhen);
        province = (Spinner) findViewById(R.id.province);
        district = (Spinner) findViewById(R.id.district);
        primaryClinic = (Spinner) findViewById(R.id.primaryClinic);
        hospCause = (Spinner) findViewById(R.id.hospCause);
        save = (Button) findViewById(R.id.btn_save);
        fields = new EditText[] {hospWhen};
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<Province> provinceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Province.getAll());
        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(provinceArrayAdapter);
        provinceArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<HospCause> hospCauseArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, HospCause.getAll());
        hospCauseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospCause.setAdapter(hospCauseArrayAdapter);
        hospCauseArrayAdapter.notifyDataSetChanged();
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), hospWhen);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        hospWhen.setOnClickListener(this);
        save.setOnClickListener(this);
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ArrayAdapter<District> districtArrayAdapter = new ArrayAdapter<>(parent.getContext(), R.layout.simple_spinner_item, District.getByProvince((Province) province.getSelectedItem()));
                districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                district.setAdapter(districtArrayAdapter);
                districtArrayAdapter.notifyDataSetChanged();
                Log.d("Province", ((Province) province.getSelectedItem()).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Facility> facilityArrayAdapter = new ArrayAdapter<>(parent.getContext(), R.layout.simple_spinner_item, Facility.getByDistrict((District) district.getSelectedItem()));
                facilityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                primaryClinic.setAdapter(facilityArrayAdapter);
                facilityArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemID != null){
            item = MedicalHist.getItem(itemID);
            updateLabel(item.hospWhen, hospWhen);
            int i = 0;
            for(HospCause m : HospCause.getAll()){
                if(item.hospCause != null && item.hospCause.equals(hospCause.getItemAtPosition(i))){
                    hospCause.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Facility f : Facility.getAll()){
                if(item.primaryClinic != null && item.primaryClinic.equals(primaryClinic.getItemAtPosition(i))){
                    primaryClinic.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Hospitalization History"));
        }else{
            item = new MedicalHist();
            setSupportActionBar(createToolBar("Add Hospitalization History"));
        }
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
        Intent intent = new Intent(MedicalHistActivity.this, MedicalHistListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == save.getId()){
            save();
        }

        if(view.getId() == hospWhen.getId()){
            dialog.show();
        }
    }

    public void save(){
        if(validate(fields)){
            if(validateLocal()){
                if(itemID != null){
                    item.id = itemID;
                    item.dateModified = new Date();
                    item.isNew = false;
                }else{
                    item.dateCreated = new Date();
                    item.id = UUIDGen.generateUUID();
                    item.isNew = true;
                }
                item.hospCause = (HospCause)  hospCause.getSelectedItem();
                item.hospWhen = DateUtil.getDateFromString(hospWhen.getText().toString());
                item.primaryClinic = (Facility) primaryClinic.getSelectedItem();
                Patient p = Patient.findById(id);
                item.patient = p;
                item.pushed = false;
                item.save();
                p.pushed = 1;
                p.save();
                AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
                Intent intent = new Intent(MedicalHistActivity.this, MedicalHistListActivity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(hospWhen.getText().toString())){
            hospWhen.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            hospWhen.setError(null);
        }
        return isValid;
    }
}
