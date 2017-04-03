package zw.org.zvandiri.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import zw.org.zvandiri.R;
import zw.org.zvandiri.business.util.AppUtil;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if(AppUtil.isLoggedIn(LauncherActivity.this)){
            intent = new Intent(this, PatientListActivity.class);
            startActivity(intent);
        }else{
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
       super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
