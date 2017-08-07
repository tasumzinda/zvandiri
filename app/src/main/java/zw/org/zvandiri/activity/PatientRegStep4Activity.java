package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.*;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.HIVDisclosureLocation;
import zw.org.zvandiri.business.domain.util.TransmissionMode;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 4/3/2017.
 */
public class PatientRegStep4Activity extends BaseActivity implements View.OnClickListener{

    private Spinner education;
    private Spinner educationLevel;
    private EditText dateJoined;
    private Spinner referer;
    private Button next;
    private DatePickerDialog dialog;
    private TextView reasonLabel;
    private Spinner reasonForNotReachingOLevel;
    private EditText refererName;
    private Patient holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step4);
        Intent intent = getIntent();
        holder = (Patient) intent.getSerializableExtra("patient");
        next = (Button) findViewById(R.id.btn_save);
        dateJoined = (EditText) findViewById(R.id.dateJoined);
        education = (Spinner) findViewById(R.id.education);
        educationLevel = (Spinner) findViewById(R.id.educationLevel);
        referer = (Spinner) findViewById(R.id.referer);
        refererName = (EditText) findViewById(R.id.refererName);
        reasonForNotReachingOLevel = (Spinner) findViewById(R.id.reasonForNotReachingOLevel);
        Log.d("Primary clinic", holder.primaryClinicId);
        reasonLabel = (TextView) findViewById(R.id.reasonLabel);
        ArrayAdapter<Education> educationArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Education.getAll());
        educationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setAdapter(educationArrayAdapter);
        educationArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<EducationLevel> educationLevelArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, EducationLevel.getAll());
        educationLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        educationLevel.setAdapter(educationLevelArrayAdapter);
        educationLevelArrayAdapter.notifyDataSetChanged();
        educationLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(((EducationLevel)educationLevel.getSelectedItem()).name.equals("Primary School") || ((EducationLevel)educationLevel.getSelectedItem()).name.equals("N/A")){
                    reasonForNotReachingOLevel.setVisibility(View.VISIBLE);
                    reasonLabel.setVisibility(View.VISIBLE);
                    ArrayAdapter<ReasonForNotReachingOLevel> reasonForNotReachingOLevelArrayAdapter = new ArrayAdapter<>(adapterView.getContext(), R.layout.simple_spinner_item, ReasonForNotReachingOLevel.getAll());
                    reasonForNotReachingOLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    reasonForNotReachingOLevel.setAdapter(reasonForNotReachingOLevelArrayAdapter);
                    reasonForNotReachingOLevelArrayAdapter.notifyDataSetChanged();
                }else{
                    reasonForNotReachingOLevel.setVisibility(View.GONE);
                    reasonLabel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<Referer> refererArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Referer.getAll());
        refererArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        referer.setAdapter(refererArrayAdapter);
        refererArrayAdapter.notifyDataSetChanged();
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateJoined);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateJoined.setOnClickListener(this);
        if(holder.educationLevelId != null){
            if(holder.dateJoined != null){
                updateLabel(holder.dateJoined, dateJoined);
            }
            refererName.setText(holder.refererName);
            int k = 0;
            for(Education e : Education.getAll()){
                if(holder.educationId != null && holder.educationId.equals(((Education)education.getItemAtPosition(k)).id)){
                    education.setSelection(k);
                    break;
                }
                k++;
            }
            k = 0;
            for(EducationLevel e : EducationLevel.getAll()){
                if(holder.educationLevelId != null && holder.educationLevelId.equals(((EducationLevel)educationLevel.getItemAtPosition(k)).id)){
                    educationLevel.setSelection(k);
                    break;
                }
                k++;
            }
            k = 0;
            for(Referer r : Referer.getAll()){
                if(holder.referrerId != null && holder.referrerId.equals(((Referer)referer.getItemAtPosition(k)).id)){
                    referer.setSelection(k);
                    break;
                }
                k++;
            }
            k = 0;
            /*for(ReasonForNotReachingOLevel r : ReasonForNotReachingOLevel.getAll()){
                if(holder.reasonForNotReachingOLevelId != null && holder.reasonForNotReachingOLevelId.equals(((ReasonForNotReachingOLevel) reasonForNotReachingOLevel.getItemAtPosition(k)).id)){
                    reasonForNotReachingOLevel.setSelection(k);
                    break;
                }
                k++;
            }*/
        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add Education and Other Details Step 4 of 7 "));
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
        Intent intent = new Intent(PatientRegStep4Activity.this, PatientRegStep3Activity.class);
        if( ! dateJoined.getText().toString().isEmpty()){
            holder.dateJoined = DateUtil.getDateFromString(dateJoined.getText().toString());
        }
        holder.educationId = ((Education) education.getSelectedItem()).id;
        holder.educationLevelId = ((EducationLevel) educationLevel.getSelectedItem()).id;
        holder.referrerId = ((Referer) referer.getSelectedItem()).id;
        holder.refererName = refererName.getText().toString();
        if(reasonForNotReachingOLevel.getVisibility() == View.VISIBLE){
            holder.reasonForNotReachingOLevelId = ((ReasonForNotReachingOLevel) reasonForNotReachingOLevel.getSelectedItem()).id;
        }
        intent.putExtra("patient", holder);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == dateJoined.getId()){
            dialog.show();
        }
        if(view.getId() == next.getId()){
            if(validateLocal()){
                Intent intent = new Intent(PatientRegStep4Activity.this, PatientRegStep5Activity.class);
                holder.dateJoined = DateUtil.getDateFromString(dateJoined.getText().toString());
                holder.educationId = ((Education) education.getSelectedItem()).id;
                holder.educationLevelId = ((EducationLevel) educationLevel.getSelectedItem()).id;
                holder.referrerId = ((Referer) referer.getSelectedItem()).id;
                holder.refererName = refererName.getText().toString();
                if(reasonForNotReachingOLevel.getVisibility() == View.VISIBLE){
                    holder.reasonForNotReachingOLevelId = ((ReasonForNotReachingOLevel) reasonForNotReachingOLevel.getSelectedItem()).id;
                }
                intent.putExtra("patient", holder);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        String date = dateJoined.getText().toString();
        if(date.isEmpty()){
            dateJoined.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            dateJoined.setError(null);
        }
        if( ! checkDateFormat(date)){
            dateJoined.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            dateJoined.setError(null);
        }
        Date today = new Date();
        if( checkDateFormat(date)){
            if(DateUtil.getDateFromString(dateJoined.getText().toString()).after(today)){
                dateJoined.setError(this.getString(R.string.date_aftertoday));
                isValid = false;
            }else{
                dateJoined.setError(null);
            }
        }

        return isValid;
    }
}
