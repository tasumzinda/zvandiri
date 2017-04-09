package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.MentalHealth;
import zw.org.zvandiri.business.domain.MentalHealthItem;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;

public class MentalHealthItemActivity extends BaseActivity implements View.OnClickListener {

    Spinner mentalHealth;
    EditText startDate;
    EditText endDate;
    EditText past;
    EditText current;
    EditText medication;
    Spinner receivedProfessionalHelp;
    EditText profHelpStart;
    EditText profHelpEnd;
    Spinner beenHospitalized;
    EditText mentalHistText;
    Button save;
    DatePickerDialog startDateDialog, endDateDialog, profHelpStartDialog, profHelpEndDialog;
    String id;
    String name;
    private MentalHealthItem item;
    private String itemID;
    private EditText[] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health_item);
        mentalHealth = (Spinner) findViewById(R.id.mentalHealth);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        past = (EditText) findViewById(R.id.past);
        current = (EditText) findViewById(R.id.current);
        medication = (EditText) findViewById(R.id.medication);
        receivedProfessionalHelp = (Spinner) findViewById(R.id.receivedProfessionalHelp);
        profHelpEnd = (EditText) findViewById(R.id.profHelpEnd);
        profHelpStart = (EditText) findViewById(R.id.profHelpStart);
        beenHospitalized = (Spinner) findViewById(R.id.beenHospitalized);
        mentalHistText = (EditText) findViewById(R.id.mentalHistText);
        save = (Button) findViewById(R.id.btn_save);
        fields = new EditText[] {startDate, endDate, past, current, mentalHistText};
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<MentalHealth> mentalHealthArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, MentalHealth.getAll());
        mentalHealthArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mentalHealth.setAdapter(mentalHealthArrayAdapter);
        mentalHealthArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<YesNo> yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beenHospitalized.setAdapter(yesNoArrayAdapter);
        receivedProfessionalHelp.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        startDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), startDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        startDate.setOnClickListener(this);
        endDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), endDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        endDate.setOnClickListener(this);
        profHelpEndDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), profHelpEnd);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        profHelpEnd.setOnClickListener(this);
        profHelpStartDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), profHelpStart);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        profHelpStart.setOnClickListener(this);
        save.setOnClickListener(this);
        receivedProfessionalHelp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(receivedProfessionalHelp.getSelectedItem().equals(YesNo.NO)){
                    profHelpStart.setVisibility(View.INVISIBLE);
                    profHelpEnd.setVisibility(View.INVISIBLE);
                }else{
                    profHelpEnd.setVisibility(View.VISIBLE);
                    profHelpStart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemID != null){
            item = MentalHealthItem.getItem(itemID);
            updateLabel(item.startDate, startDate);
            updateLabel(item.endDate, endDate);
            if(item.profHelpEnd != null){
                updateLabel(item.profHelpEnd, profHelpEnd);
            }
            if(item.profHelpStart != null){
                updateLabel(item.profHelpStart, profHelpStart);
            }
            past.setText(item.past);
            current.setText(item.current);
            medication.setText(item.medication);
            mentalHistText.setText(item.mentalHistText);
            int i = 0;
            for(MentalHealth m : MentalHealth.getAll()){
                if(item.mentalHealth != null && item.mentalHealth.equals(mentalHealth.getItemAtPosition(i))){
                    mentalHealth.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.receivedProfessionalHelp != null && item.receivedProfessionalHelp.equals(receivedProfessionalHelp.getItemAtPosition(i))){
                    receivedProfessionalHelp.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.beenHospitalized != null && item.beenHospitalized.equals(beenHospitalized.getItemAtPosition(i))){
                    beenHospitalized.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Mental Health"));
        }else{
            item = new MentalHealthItem();
            setSupportActionBar(createToolBar("Add Mental Health"));
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

    public void onBackPressed(){
        Intent intent = new Intent(MentalHealthItemActivity.this, MentalHealthItemListActivity.class);
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
        if(view.getId() == startDate.getId()){
            startDateDialog.show();
        }
        if(view.getId() == endDate.getId()){
            endDateDialog.show();
        }
        if(view.getId() == profHelpEnd.getId()){
            profHelpEndDialog.show();
        }
        if(view.getId() == profHelpStart.getId()){
            profHelpStartDialog.show();
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
                    item.id = UUIDGen.generateUUID();
                    item.dateCreated = new Date();
                    item.isNew = true;
                }
                item.beenHospitalized = (YesNo) beenHospitalized.getSelectedItem();
                item.current = current.getText().toString();
                item.endDate = DateUtil.getDateFromString(endDate.getText().toString());
                item.medication = medication.getText().toString();
                item.mentalHealth = (MentalHealth) mentalHealth.getSelectedItem();
                item.mentalHistText = mentalHistText.getText().toString();
                item.past = past.getText().toString();
                Patient p = Patient.findById(id);
                item.patient = p;
                if( ! profHelpStart.getText().toString().isEmpty()){
                    item.profHelpStart = DateUtil.getDateFromString(profHelpStart.getText().toString());
                }
                if( ! profHelpEnd.getText().toString().isEmpty()){
                    item.profHelpEnd = DateUtil.getDateFromString(profHelpEnd.getText().toString());
                }
                item.receivedProfessionalHelp = (YesNo) receivedProfessionalHelp.getSelectedItem();
                item.startDate = DateUtil.getDateFromString(startDate.getText().toString());
                item.pushed = false;
                item.save();
                p.pushed = 1;
                p.save();
                AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
                Intent intent = new Intent(MentalHealthItemActivity.this, MentalHealthItemListActivity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(startDate.getText().toString())){
            startDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            startDate.setError(null);
        }
        if( ! checkDateFormat(endDate.getText().toString())){
            endDate.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            endDate.setError(null);
        }
        Date start = DateUtil.getDateFromString(startDate.getText().toString());
        Date end = DateUtil.getDateFromString(endDate.getText().toString());
        if(checkDateFormat(startDate.getText().toString()) && checkDateFormat(endDate.getText().toString())){
            if(start.after(end)){
                endDate.setError(getResources().getString(R.string.date_mismatch_error));
                isValid = false;
            }else{
                endDate.setError(null);
            }
        }

        if(receivedProfessionalHelp.getSelectedItem().equals(YesNo.YES)){
            if(profHelpEnd.getText().toString().isEmpty()){
                profHelpEnd.setError(getResources().getString(R.string.required_field_error));
                isValid = false;
            }else{
                profHelpEnd.setError(null);
            }
            if(profHelpStart.getText().toString().isEmpty()){
                profHelpStart.setError(getResources().getString(R.string.required_field_error));
                isValid = false;
            }else{
                profHelpStart.setError(null);
            }
            if( ! checkDateFormat(profHelpStart.getText().toString())){
                profHelpStart.setError(getResources().getString(R.string.date_format_error));
                isValid = false;
            }else{
                profHelpStart.setError(null);
            }
            if( ! checkDateFormat(profHelpEnd.getText().toString())){
                profHelpEnd.setError(getResources().getString(R.string.date_format_error));
                isValid = false;
            }else{
                profHelpEnd.setError(null);
            }
            if( checkDateFormat(profHelpStart.getText().toString()) && checkDateFormat(profHelpEnd.getText().toString())){
                Date profStart = DateUtil.getDateFromString(profHelpStart.getText().toString());
                Date profEnd = DateUtil.getDateFromString(profHelpEnd.getText().toString());
                if(profStart.after(profEnd)){
                    profHelpEnd.setError(getResources().getString(R.string.date_mismatch_error));
                    isValid = false;
                }else{
                    profHelpEnd.setError(null);
                }
            }
        }
        return isValid;
    }
}
