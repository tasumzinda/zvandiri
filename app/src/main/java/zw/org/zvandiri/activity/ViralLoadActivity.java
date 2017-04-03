package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.ViralLoad;
import zw.org.zvandiri.business.domain.util.Cd4CountResultSource;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;

public class ViralLoadActivity extends BaseActivity implements View.OnClickListener {

    private EditText dateTaken;
    private EditText result;
    private Spinner source;
    private Button save;
    private DatePickerDialog dialog;
    private ViralLoad item;
    private String id;
    private String name;
    private String itemID;
    private EditText [] fields;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_result);
        dateTaken = (EditText) findViewById(R.id.dateTaken);
        result = (EditText) findViewById(R.id.result);
        source = (Spinner) findViewById(R.id.source);
        save = (Button) findViewById(R.id.btn_save);
        fields = new EditText[] {dateTaken, result};
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<Cd4CountResultSource> arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Cd4CountResultSource.values());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(newDate.getTime(), dateTaken);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateTaken.setOnClickListener(this);
        if(itemID != null){
            item = ViralLoad.getItem(itemID);
            updateLabel(item.dateTaken, dateTaken);
            result.setText(String.valueOf(item.result));
            int i = 0;
            for(Cd4CountResultSource m : Cd4CountResultSource.values()){
                if(item.source != null && item.source.equals(source.getItemAtPosition(i))){
                    source.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Viral Load Item"));
        }else{
            item = new ViralLoad();
            setSupportActionBar(createToolBar("Add Viral Load Item"));
        }
        save.setOnClickListener(this);
        dateTaken.setOnClickListener(this);
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
        Intent intent = new Intent(ViralLoadActivity.this, ViralLoadListActivity.class);
        intent.putExtra(AppUtil.ID, id);
        intent.putExtra(AppUtil.NAME, name);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == save.getId()){
            save();
        }
        if(view.getId() == dateTaken.getId()){
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
                item.source = (Cd4CountResultSource) source.getSelectedItem();
                item.dateTaken = DateUtil.getDateFromString(dateTaken.getText().toString());
                item.result = Integer.parseInt(result.getText().toString());
                Patient p = Patient.findById(id);
                item.patient = p;
                item.pushed = false;
                item.save();
                p.pushed = false;
                p.save();
                AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
                Intent intent = new Intent(ViralLoadActivity.this, ViralLoadListActivity.class);
                intent.putExtra(AppUtil.NAME, name);
                intent.putExtra(AppUtil.ID, id);
                startActivity(intent);
                finish();
            }

        }
    }

    public boolean validateLocal(){
        boolean isValid = true;
        if( ! checkDateFormat(dateTaken.getText().toString())){
            dateTaken.setError(getResources().getString(R.string.date_format_error));
            isValid = false;
        }else{
            dateTaken.setError(null);
        }
        return isValid;
    }
}
