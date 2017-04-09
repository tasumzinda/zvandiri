package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.SrhHist;
import zw.org.zvandiri.business.domain.util.CondomUse;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Date;

public class SrhHistActivity extends BaseActivity implements View.OnClickListener {

    EditText ageStartMen;
    EditText bleedHowOften;
    EditText bleedDays;
    Spinner sexualIntercourse;
    Spinner sexuallyActive;
    Spinner condomUse;
    Spinner birthControl;
    Button button;
    String id;
    String name;
    private SrhHist item;
    private String itemID;
    private EditText [] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srh_hist);
        ageStartMen = (EditText) findViewById(R.id.ageStartMen);
        bleedHowOften = (EditText) findViewById(R.id.bleedHowOften);
        bleedDays = (EditText) findViewById(R.id.bleedDays);
        sexualIntercourse = (Spinner) findViewById(R.id.sexualIntercourse);
        sexuallyActive = (Spinner) findViewById(R.id.sexuallyActive);
        condomUse = (Spinner) findViewById(R.id.condomUse);
        birthControl = (Spinner) findViewById(R.id.birth_control);
        button = (Button) findViewById(R.id.btn_save);
        button.setOnClickListener(this);
        ArrayAdapter<YesNo> yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexualIntercourse.setAdapter(yesNoArrayAdapter);
        sexuallyActive.setAdapter(yesNoArrayAdapter);
        birthControl.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<CondomUse> condomUseArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, CondomUse.values());
        condomUseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condomUse.setAdapter(condomUseArrayAdapter);
        condomUseArrayAdapter.notifyDataSetChanged();
        Intent intent = getIntent();
        fields = new EditText[] {ageStartMen, bleedDays, bleedHowOften};
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        sexualIntercourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sexualIntercourse.getSelectedItem().equals(YesNo.NO)){
                    sexuallyActive.setVisibility(View.GONE);
                    condomUse.setVisibility(View.GONE);
                    birthControl.setVisibility(View.GONE);
                    birthControl.setVisibility(View.GONE);
                }else{
                    sexuallyActive.setVisibility(View.VISIBLE);
                    condomUse.setVisibility(View.VISIBLE);
                    birthControl.setVisibility(View.VISIBLE);
                    birthControl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemID != null){
            item = SrhHist.getItem(itemID);
            ageStartMen.setText(String.valueOf(item.ageStartMen));
            bleedHowOften.setText(String.valueOf(item.bleedHowOften));
            bleedDays.setText(String.valueOf(item.bleeddays));
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.sexualIntercourse != null && item.sexualIntercourse.equals(sexualIntercourse.getItemAtPosition(i))){
                    sexualIntercourse.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.sexuallyActive != null && item.sexuallyActive.equals(sexuallyActive.getItemAtPosition(i))){
                    sexuallyActive.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.birthControl != null && item.birthControl.equals(birthControl.getItemAtPosition(i))){
                    birthControl.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Srh History"));
        }else{
            item = new SrhHist();
            setSupportActionBar(createToolBar("Add Srh History"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onClick(View view) {
        if(validate(fields)){
            if(itemID != null){
                item.id = itemID;
                item.dateModified = new Date();
                item.isNew = false;
            }else{
                item.id = UUIDGen.generateUUID();
                item.dateCreated = new Date();
                item.isNew = true;
            }
            item.ageStartMen = Integer.parseInt(ageStartMen.getText().toString().trim());
            item.birthControl = (YesNo) birthControl.getSelectedItem();
            item.bleeddays = Integer.parseInt(bleedDays.getText().toString().trim());
            item.bleedHowOften = Integer.parseInt(bleedHowOften.getText().toString().trim());
            item.condomUse = (CondomUse) condomUse.getSelectedItem();
            item.sexualIntercourse = (YesNo) sexualIntercourse.getSelectedItem();
            item.sexuallyActive = (YesNo) sexuallyActive.getSelectedItem();
            Patient p = Patient.findById(id);
            item.patient = p;
            item.pushed = false;
            item.save();
            p.pushed = 1;
            p.save();
            AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
            Intent intent = new Intent(SrhHistActivity.this, SrhHistListActivity.class);
            intent.putExtra(AppUtil.ID, id);
            intent.putExtra(AppUtil.NAME, name);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SrhHistActivity.this, SrhHistListActivity.class);
        intent.putExtra(AppUtil.NAME, name);
        intent.putExtra(AppUtil.ID, id);
        startActivity(intent);
        finish();
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
}
