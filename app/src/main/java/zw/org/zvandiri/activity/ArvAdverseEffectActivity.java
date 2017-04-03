package zw.org.zvandiri.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.ArvAdverseEffect;
import zw.org.zvandiri.business.domain.util.Source;
import zw.org.zvandiri.business.domain.util.Status;
import zw.org.zvandiri.business.util.DateUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Calendar;
import java.util.Date;

public class ArvAdverseEffectActivity extends BaseActivity implements View.OnClickListener{

    EditText event;
    EditText dateCommenced;
    Spinner status;
    Spinner source;
    Button button;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arv_adverse_effect);
        event = (EditText) findViewById(R.id.event);
        dateCommenced = (EditText) findViewById(R.id.dateCommenced);
        status = (Spinner) findViewById(R.id.status);
        source = (Spinner) findViewById(R.id.source);
        button = (Button) findViewById(R.id.btn_save);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                updateLabel(new Date(), dateCommenced);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dateCommenced.setOnClickListener(this);
        button.setOnClickListener(this);
        ArrayAdapter<Source> sourceArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Source.values());
        sourceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source.setAdapter(sourceArrayAdapter);
        sourceArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Status> statusArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Status.values());
        statusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(statusArrayAdapter);
        statusArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == button.getId()){
            save();
        }
        if(view.getId() == dateCommenced.getId()){
            datePickerDialog.show();
        }
    }

    public boolean validate(){
        boolean isValid = true;
        if(event.getText().toString().isEmpty()){
            event.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else {
            event.setError(null);
        }
        if(dateCommenced.getText().toString().isEmpty()){
            dateCommenced.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else {
            dateCommenced.setError(null);
        }
        return isValid;
    }

    public void save(){
        if(validate()){
            ArvAdverseEffect arvAdverseEffect = new ArvAdverseEffect();
            arvAdverseEffect.id = UUIDGen.generateUUID();
            arvAdverseEffect.event = event.getText().toString();
            arvAdverseEffect.dateCommenced = DateUtil.getDateFromString(dateCommenced.getText().toString());
            arvAdverseEffect.source = (Source) source.getSelectedItem();
            arvAdverseEffect.status = (Status) status.getSelectedItem();
            arvAdverseEffect.save();
        }
    }
}
