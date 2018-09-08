package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @uthor Tasu Muzinda
 */
public class HeuMotherDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ListView mother;
    private Button save;
    private EditText search;
    ArrayAdapter<Patient> patientArrayAdapter;
    Patient item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heu_mother);
        Intent intent = getIntent();
        item = Patient.get(intent.getLongExtra(AppUtil.ID, 0L));
        mother = (ListView) findViewById(R.id.mother);
        search = (EditText) findViewById(R.id.search);
        save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        List<Patient> list = new ArrayList<>();
        patientArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, list);
        mother.setAdapter(patientArrayAdapter);
        mother.setItemsCanFocus(false);
        mother.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        patientArrayAdapter.notifyDataSetChanged();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = charSequence.toString();
                List<Patient> list = Patient.findYoungMothersByName(name);
                patientArrayAdapter.clear();
                patientArrayAdapter.addAll(list);
                patientArrayAdapter.notifyDataSetChanged();
                mother.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        setSupportActionBar(createToolBar("Add Major Patient Mother's Details"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if(getPatient() != null){
            item.motherOfHei = getPatient();
            item.save();
            AppUtil.createShortNotification(this, "Successfully added mother of HEI");
            Intent intent = new Intent(this, PatientListActivity.class);
            startActivity(intent);
            finish();
        }else{
            AppUtil.createShortNotification(this, "Please select mother of HEI");
        }
    }

    public Patient getPatient(){
        Patient item = null;
        for(int i = 0; i < mother.getCount(); i++){
            if(mother.isItemChecked(i))
                item = patientArrayAdapter.getItem(i);
        }
        return item;
    }
}
