package zw.org.zvandiri.business.util;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Tasu Muzinda on 12/10/2016.
 */
public class DbUtil extends Application {

    public static Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration dbConfiguration = new Configuration.Builder(this).create();
        JodaTimeAndroid.init(this);
        ActiveAndroid.initialize(dbConfiguration);
        bus = new Bus(ThreadEnforcer.MAIN);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static Bus getInstance() {
        return bus;
    }
}
