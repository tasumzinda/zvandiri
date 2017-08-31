package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.DisabilityCategory;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.ReasonForNotReachingOLevel;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 4/4/2017.
 */
public class PatientRegStep5ContActivity extends BaseActivity implements View.OnClickListener{

    private Spinner disability;
    private TextView disabilityLabel;
    private ListView disabilityCategorys;
    private Button next;
    ArrayAdapter<DisabilityCategory> disabilityCategorysArrayAdapter;
    private Patient holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg_step5_cont);
        Intent intent = getIntent();
        holder = (Patient) intent.getSerializableExtra("patient");
        next = (Button) findViewById(R.id.btn_save);
        disability = (Spinner) findViewById(R.id.disability);
        disabilityLabel = (TextView) findViewById(R.id.disabilityLabel);
        disabilityCategorys = (ListView) findViewById(R.id.disabilityCategorys);
        ArrayAdapter<YesNo> disabilityArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        disabilityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disability.setAdapter(disabilityArrayAdapter);
        disabilityArrayAdapter.notifyDataSetChanged();
        disabilityCategorysArrayAdapter = new ArrayAdapter<>(this, R.layout.check_box_item, DisabilityCategory.getAll());
        disabilityCategorys.setAdapter(disabilityCategorysArrayAdapter);
        disabilityCategorysArrayAdapter.notifyDataSetChanged();
        disabilityCategorys.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        disabilityCategorys.setItemsCanFocus(false);
        disability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(disability.getSelectedItem().equals(YesNo.YES)){
                    disabilityCategorys.setVisibility(View.VISIBLE);
                    disabilityLabel.setVisibility(View.VISIBLE);
                }else{
                    disabilityLabel.setVisibility(View.GONE);
                    disabilityCategorys.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(holder.disability != null){
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(holder.disability != null  && holder.disability.equals(disability.getItemAtPosition(i))){
                    disability.setSelection(i, true);
                    break;
                }
                i++;
            }

            if(disabilityCategorys.getVisibility() == View.VISIBLE){
                ArrayList<String> list = holder.disabilityCategorysId;
                int disabilityCategorysCount = disabilityCategorysArrayAdapter.getCount();
                for(i = 0; i < disabilityCategorysCount; i++){
                    DisabilityCategory current = disabilityCategorysArrayAdapter.getItem(i);
                    if(list.contains(current.id)){
                        disabilityCategorys.setItemChecked(i, true);
                    }
                }

            }

        }
        next.setOnClickListener(this);
        setSupportActionBar(createToolBar("Create Patient Add HIV and Health Details Step 5 of 7 "));
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
        Intent intent = new Intent(PatientRegStep5ContActivity.this, PatientRegStep5Activity.class);
        holder.disability = (YesNo) disability.getSelectedItem();
        if(disability.getSelectedItem().equals(YesNo.YES)){
            holder.disabilityCategorysId = getDisabilityCategorys();
        }
        intent.putExtra("patient", holder);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == next.getId()){
            if(validate()){
                Intent intent = new Intent(PatientRegStep5ContActivity.this, PatientRegStep6Activity.class);
                holder.disability = (YesNo) disability.getSelectedItem();
                if(disability.getSelectedItem().equals(YesNo.YES)){
                    holder.disabilityCategorysId = getDisabilityCategorys();
                }
                intent.putExtra("patient", holder);
                startActivity(intent);
                finish();
            }

        }
    }

    private ArrayList<String> getDisabilityCategorys(){
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i < disabilityCategorys.getCount(); i++){
            if(disabilityCategorys.isItemChecked(i)){
                a.add(disabilityCategorysArrayAdapter.getItem(i).id);
            }else{
                a.remove(disabilityCategorysArrayAdapter.getItem(i).id);
            }
        }
        return a;
    }

    private boolean validate(){
        boolean isValid = true;
        if(disability.getSelectedItem() != null && disability.getSelectedItem().equals(YesNo.YES) && getDisabilityCategorys().size() < 1){
            AppUtil.createShortNotification(this, "Please select at least one disability category");
            isValid = false;
        }
        return isValid;
    }
}
