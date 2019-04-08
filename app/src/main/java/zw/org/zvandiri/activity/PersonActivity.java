package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.District;
import zw.org.zvandiri.business.domain.Person;
import zw.org.zvandiri.business.domain.Province;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.util.Calendar;

/**
 * @uthor Tasu Muzinda
 */
public class PersonActivity extends BaseActivity implements View.OnClickListener {

    EditText nameOfClient;
    EditText age;
    EditText dateOfBirth;
    Spinner gender;
    EditText nameOfMother;
    Spinner province;
    Spinner district;
    Button save;
    ArrayAdapter<Gender> genderArrayAdapter;
    ArrayAdapter<Province> provinceArrayAdapter;
    ArrayAdapter<District> districtArrayAdapter;
    Person item;
    DatePickerDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        setSupportActionBar(createToolBar("Create/Edit Person"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nameOfClient = (EditText) findViewById(R.id.nameOfClient);
        age = (EditText) findViewById(R.id.age);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        nameOfMother = (EditText) findViewById(R.id.nameOfMother);
        gender = (Spinner) findViewById(R.id.gender);
        province = (Spinner) findViewById(R.id.province);
        district = (Spinner) findViewById(R.id.district);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        genderArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Gender.values());
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderArrayAdapter);
        genderArrayAdapter.notifyDataSetChanged();
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateOfBirth);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateOfBirth.setOnClickListener(this);
        provinceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Province.getAll());
        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(provinceArrayAdapter);
        provinceArrayAdapter.notifyDataSetChanged();
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, District.getByProvince((Province) province.getSelectedItem()));
                districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                district.setAdapter(districtArrayAdapter);
                districtArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == dateOfBirth.getId()){
            dialog.show();
        }
        if(v.getId() == save.getId()){
            if(validate()){
                item = new Person();
                item.nameOfClient = nameOfClient.getText().toString();
                item.age = Integer.parseInt(age.getText().toString());
                item.dateOfBirth = DateUtil.getDateFromString(dateOfBirth.getText().toString());
                item.gender = (Gender) gender.getSelectedItem();
                item.nameOfMother = nameOfMother.getText().toString();
                item.district = (District) district.getSelectedItem();
                item.save();
                AppUtil.createGson().toJson(item);
                AppUtil.createShortNotification(this, "Saved successfully!");
                Intent intent = new Intent(this, PersonListActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public boolean validate(){
        boolean isValid = true;
        if(nameOfClient.getText().toString().isEmpty()){
            nameOfClient.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            nameOfClient.setError(null);
        }
        if(age.getText().toString().isEmpty()){
            age.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            age.setError(null);
        }
        if(dateOfBirth.getText().toString().isEmpty()){
            dateOfBirth.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            dateOfBirth.setError(null);
        }
        if(nameOfMother.getText().toString().isEmpty()){
            nameOfMother.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            nameOfMother.setError(null);
        }
        View selectedView = district.getSelectedView();
        TextView selectedTextView = (TextView) selectedView;
        if(district.getSelectedItem() == null){
            selectedTextView.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            selectedTextView.setError(null);
        }
        return isValid;
    }
}
