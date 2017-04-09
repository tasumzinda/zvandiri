package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Text;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.MentalHealthItem;
import zw.org.zvandiri.business.domain.ObstercHist;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.domain.util.PregType;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Date;

public class ObstetricHistActivity extends BaseActivity implements View.OnClickListener {

    Spinner pregnant;
    EditText numberOfPreg;
    Spinner  pregCurrent;
    Spinner givenBirth;
    Spinner pregType;
    EditText children;
    EditText childrenHivStatus;
    TextView birthTypeLabel;
    TextView pregCurrentLabel;
    TextView givenBirthLabel;
    Button save;
    String id;
    String name;
    private ObstercHist item;
    private String itemID;
    private EditText[] fields;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obstetric_hist);
        pregnant = (Spinner) findViewById(R.id.pregnant);
        numberOfPreg = (EditText) findViewById(R.id.numberOfPreg);
        pregCurrent = (Spinner) findViewById(R.id.pregCurrent);
        givenBirth = (Spinner) findViewById(R.id.givenBirth);
        pregType = (Spinner) findViewById(R.id.pregType);
        children = (EditText) findViewById(R.id.children);
        childrenHivStatus = (EditText) findViewById(R.id.childrenHivStatus);
        birthTypeLabel = (TextView) findViewById(R.id.birth_type_label);
        pregCurrentLabel = (TextView) findViewById(R.id.pregCurrentLabel);
        givenBirthLabel = (TextView) findViewById(R.id.givenBirthLabel);
        save = (Button) findViewById(R.id.btn_save);
        fields = new EditText[] {numberOfPreg, children, childrenHivStatus};
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<PregType> pregTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, PregType.values());
        pregTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pregType.setAdapter(pregTypeArrayAdapter);
        pregTypeArrayAdapter.notifyDataSetChanged();
        ArrayAdapter<YesNo> yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pregnant.setAdapter(yesNoArrayAdapter);
        pregCurrent.setAdapter(yesNoArrayAdapter);
        givenBirth.setAdapter(yesNoArrayAdapter);
        yesNoArrayAdapter.notifyDataSetChanged();
        save.setOnClickListener(this);
        givenBirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(givenBirth.getSelectedItem().equals(YesNo.NO)){
                    pregType.setVisibility(View.GONE);
                    children.setVisibility(View.GONE);
                    childrenHivStatus.setVisibility(View.GONE);
                    birthTypeLabel.setVisibility(View.GONE);
                }else{
                    pregType.setVisibility(View.VISIBLE);
                    children.setVisibility(View.VISIBLE);
                    childrenHivStatus.setVisibility(View.VISIBLE);
                    birthTypeLabel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(itemID != null){
            item = ObstercHist.getItem(itemID);
            numberOfPreg.setText(String.valueOf(item.numberOfPreg));
            children.setText(String.valueOf(item.children));
            childrenHivStatus.setText(item.childrenHivStatus);
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.pregnant != null && item.pregnant.equals(pregnant.getItemAtPosition(i))){
                    pregnant.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.pregCurrent != null && item.pregCurrent.equals(pregCurrent.getItemAtPosition(i))){
                    pregCurrent.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.givenBirth != null && item.givenBirth.equals(givenBirth.getItemAtPosition(i))){
                    givenBirth.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(PregType m : PregType.values()){
                if(item.pregType != null && item.pregType.equals(pregType.getItemAtPosition(i))){
                    pregType.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Obstetric History for " + name));
        }else{
            item = new ObstercHist();
            setSupportActionBar(createToolBar("Add Obstetric History for " + name));
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
        Intent intent = new Intent(ObstetricHistActivity.this, ObstetricHistListActivity.class);
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
    }

    public void save(){
        if(validate()){
            if(itemID != null){
                item.id = itemID;
                item.dateModified = new Date();
                item.isNew = false;
            }else{
                item.id = UUIDGen.generateUUID();
                item.dateCreated = new Date();
                item.isNew = true;
            }
            if( ! children.getText().toString().isEmpty()){
                item.children = Integer.parseInt(children.getText().toString());
            }
            item.childrenHivStatus = childrenHivStatus.getText().toString();
            item.givenBirth = (YesNo) givenBirth.getSelectedItem();
            if( ! numberOfPreg.getText().toString().isEmpty()){
                item.numberOfPreg = Integer.parseInt(numberOfPreg.getText().toString());
            }
            Patient p = Patient.findById(id);
            item.patient = p;
            item.pregCurrent = (YesNo) pregCurrent.getSelectedItem();
            item.pregnant = (YesNo) pregnant.getSelectedItem();
            item.pregType = (PregType) pregType.getSelectedItem();
            item.pushed = false;
            item.save();
            p.pushed = 1;
            p.save();
            Intent intent = new Intent(ObstetricHistActivity.this, ObstetricHistListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }

    public boolean validate(){
        boolean isValid = true;
        if(givenBirth.getSelectedItem().equals(YesNo.YES)){
            if(childrenHivStatus.getText().toString().isEmpty()){
                childrenHivStatus.setError(getResources().getString(R.string.required_field_error));
                isValid = false;
            }else{
                childrenHivStatus.setError(null);
            }

            if(children.getText().toString().isEmpty()){
                children.setError(getResources().getString(R.string.required_field_error));
                isValid = false;
            }else{
                children.setError(null);
            }
        }
        if(numberOfPreg.getText().toString().isEmpty()){
            numberOfPreg.setError(getResources().getString(R.string.required_field_error));
            isValid = false;
        }else{
            numberOfPreg.setError(null);
        }
        return isValid;
    }
}
