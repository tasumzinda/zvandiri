package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.Family;
import zw.org.zvandiri.business.domain.OrphanStatus;
import zw.org.zvandiri.business.domain.Patient;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.UUIDGen;

import java.util.Date;

public class FamilyActivity extends BaseActivity implements View.OnClickListener {

    Spinner orphanStatus;
    EditText numberOfSiblings;
    Button save;
    String id;
    String name;
    private Family item;
    private String itemID;
    private EditText[] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        orphanStatus = (Spinner) findViewById(R.id.orphanStatus);
        numberOfSiblings = (EditText) findViewById(R.id.numberOfSiblings);
        save = (Button) findViewById(R.id.btn_save);
        fields = new EditText[] {numberOfSiblings};
        Intent intent = getIntent();
        id = intent.getStringExtra(AppUtil.ID);
        name = intent.getStringExtra(AppUtil.NAME);
        itemID = intent.getStringExtra(AppUtil.DETAILS_ID);
        ArrayAdapter<OrphanStatus> orphanStatusArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, OrphanStatus.getAll());
        orphanStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orphanStatus.setAdapter(orphanStatusArrayAdapter);
        orphanStatusArrayAdapter.notifyDataSetChanged();
        save.setOnClickListener(this);
        if(itemID != null){
            item = Family.getItem(itemID);
            numberOfSiblings.setText(String.valueOf(item.numberOfSiblings));
            int i = 0;
            for(OrphanStatus m : OrphanStatus.getAll()){
                if(item.orphanStatus != null && item.orphanStatus.equals(orphanStatus.getItemAtPosition(i))){
                    orphanStatus.setSelection(i, true);
                    break;
                }
                i++;
            }
            setSupportActionBar(createToolBar("Update Family"));
        }else{
            item = new Family();
            setSupportActionBar(createToolBar("Add Family"));
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
        Intent intent = new Intent(FamilyActivity.this, FamilyListActivity.class);
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
        if(validate(fields)){
            if(itemID != null){
                item.id = itemID;
                item.dateModified = new Date();
                item.isNew = false;
            }else{
                item.dateCreated = new Date();
                item.id = UUIDGen.generateUUID();
                item.isNew = true;
            }
            item.numberOfSiblings = Integer.parseInt(numberOfSiblings.getText().toString());
            item.orphanStatus = (OrphanStatus) orphanStatus.getSelectedItem();
            Patient p = Patient.findById(id);
            item.patient = p;
            item.pushed = false;
            item.save();
            p.pushed = 1;
            p.save();
            AppUtil.createShortNotification(getApplicationContext(), getResources().getString(R.string.save_success_message));
            Intent intent = new Intent(FamilyActivity.this, FamilyListActivity.class);
            intent.putExtra(AppUtil.NAME, name);
            intent.putExtra(AppUtil.ID, id);
            startActivity(intent);
            finish();
        }
    }
}
