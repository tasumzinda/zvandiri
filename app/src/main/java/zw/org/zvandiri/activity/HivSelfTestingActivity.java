package zw.org.zvandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.domain.HivSelfTesting;
import zw.org.zvandiri.business.domain.Person;
import zw.org.zvandiri.business.domain.util.Result;
import zw.org.zvandiri.business.domain.util.YesNo;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.toolbox.Log;

/**
 * @uthor Tasu Muzinda
 */
public class HivSelfTestingActivity extends BaseActivity implements View.OnClickListener {

    Spinner testedAtHealthFacilityResult;
    Spinner selfTestKitDistributed;
    Spinner hisSelfTestingResult;
    Spinner homeBasedTestingResult;
    Spinner confirmatoryTestingResult;
    Spinner artInitiation;
    Button save;
    ArrayAdapter<Result> resultArrayAdapter;
    ArrayAdapter<YesNo> yesNoArrayAdapter;
    HivSelfTesting item;
    Person person;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiv_self_testing);
        Intent intent = getIntent();
        Long id = intent.getLongExtra(AppUtil.ID, 0L);
        Long itemId = intent.getLongExtra("itemId", 0L);
        testedAtHealthFacilityResult = (Spinner) findViewById(R.id.testedAtHealthFacilityResult);
        selfTestKitDistributed = (Spinner) findViewById(R.id.selfTestKitDistributed);
        hisSelfTestingResult = (Spinner) findViewById(R.id.hisSelfTestingResult);
        homeBasedTestingResult = (Spinner) findViewById(R.id.homeBasedTestingResult);
        confirmatoryTestingResult = (Spinner) findViewById(R.id.confirmatoryTestingResult);
        artInitiation = (Spinner) findViewById(R.id.artInitiation);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        resultArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, Result.values());
        resultArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testedAtHealthFacilityResult.setAdapter(resultArrayAdapter);
        hisSelfTestingResult.setAdapter(resultArrayAdapter);
        homeBasedTestingResult.setAdapter(resultArrayAdapter);
        confirmatoryTestingResult.setAdapter(resultArrayAdapter);
        yesNoArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, YesNo.values());
        yesNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selfTestKitDistributed.setAdapter(yesNoArrayAdapter);
        artInitiation.setAdapter(yesNoArrayAdapter);
        if(itemId != 0L){
            item = HivSelfTesting.getItem(itemId);
            int i = 0;
            for(YesNo m : YesNo.values()){
                if(item.selfTestKitDistributed != null && item.selfTestKitDistributed.equals(selfTestKitDistributed.getItemAtPosition(i))){
                    selfTestKitDistributed.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(YesNo m : YesNo.values()){
                if(item.artInitiation != null && item.artInitiation.equals(artInitiation.getItemAtPosition(i))){
                    artInitiation.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Result m : Result.values()){
                if(item.testedAtHealthFacilityResult != null && item.testedAtHealthFacilityResult.equals(testedAtHealthFacilityResult.getItemAtPosition(i))){
                    testedAtHealthFacilityResult.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Result m : Result.values()){
                if(item.hisSelfTestingResult != null && item.hisSelfTestingResult.equals(hisSelfTestingResult.getItemAtPosition(i))){
                    hisSelfTestingResult.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Result m : Result.values()){
                if(item.homeBasedTestingResult != null && item.homeBasedTestingResult.equals(homeBasedTestingResult.getItemAtPosition(i))){
                    homeBasedTestingResult.setSelection(i, true);
                    break;
                }
                i++;
            }
            i = 0;
            for(Result m : Result.values()){
                if(item.confirmatoryTestingResult != null && item.confirmatoryTestingResult.equals(confirmatoryTestingResult.getItemAtPosition(i))){
                    confirmatoryTestingResult.setSelection(i, true);
                    break;
                }
                i++;
            }
            person = item.person;
            setSupportActionBar(createToolBar("Update HIV Self Testing History For " + person.nameOfClient));
        }else{
            item = new HivSelfTesting();
            person = Person.getItem(id);
            setSupportActionBar(createToolBar("Add HIV Self Testing History For " + person.nameOfClient));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        item.person = person;
        item.artInitiation = (YesNo) artInitiation.getSelectedItem();
        item.selfTestKitDistributed = (YesNo) selfTestKitDistributed.getSelectedItem();
        item.testedAtHealthFacilityResult = (Result) testedAtHealthFacilityResult.getSelectedItem();
        item.hisSelfTestingResult = (Result) hisSelfTestingResult.getSelectedItem();
        item.homeBasedTestingResult = (Result) homeBasedTestingResult.getSelectedItem();
        item.confirmatoryTestingResult = (Result) confirmatoryTestingResult.getSelectedItem();
        item.save();
        AppUtil.createShortNotification(this, "Saved successfully!");
        Intent intent = new Intent(this, HivSelfTestingListActivity.class);
        intent.putExtra(AppUtil.ID, person.getId());
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, HivSelfTestingListActivity.class);
        intent.putExtra(AppUtil.ID, person.getId());
        startActivity(intent);
        finish();
    }
}
