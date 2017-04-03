package zw.org.zvandiri.business.domain;

import android.content.Context;
import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.PeriodType;
import zw.org.zvandiri.business.util.AppUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static zw.org.zvandiri.business.util.DateUtil.getYearMonthName;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "period", id = "_id")
public class Period extends Model {

    private static final String GET_URL = "http://pzat.org:8089/zvandiri";
    private static final Response.Listener<String> onPeriodsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<Period> periodList = Arrays.asList(AppUtil.createGson().fromJson(response, Period[].class));
            for (Period period : periodList) {
                Log.d("Saving period", period.getName());
                period.save();
            }
        }
    };
    private static final Response.ErrorListener onPeriodsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("Period error", error.toString());
        }
    };
    @Expose
    @Column(name = "uuid")
    public String uuid;
    @Expose
    @Column(name = "created_by")
    public User createdBy;
    @Expose
    @Column(name = "modified_by")
    public User modifiedBy;
    //@Expose
    @Column(name = "date_created")
    public Date dateCreated;
    @Expose
    @Column(name = "date_modified")
    public Date dateModified;
    @Expose
    @Column(name = "version")
    public Long version;
    @Expose
    @Column(name = "active")
    public Boolean active = Boolean.TRUE;
    @Expose
    @Column(name = "deleted")
    public Boolean deleted = Boolean.FALSE;
    @Expose
    @Column(name = "id")
    public String id;
    @Expose
    @Column(name = "start_date")
    public Date startDate;
    @Expose
    @Column(name = "end_date")
    public Date endDate;
    @Expose
    @Column(name = "period_type")
    public PeriodType periodType;

    public Period() {
        super();
    }

    public Period(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Period(Date startDate, Date endDate, PeriodType periodType) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodType = periodType;
    }

    public static void fetchPeriods(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_URL, onPeriodsLoaded, onPeriodsError);
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }

    public List<Period> getAll() {
        return new Select().from(Period.class).execute();
    }

    public String getName() {
        return getYearMonthName(startDate) + " - " + getYearMonthName(endDate);
    }

    public String toString() {
        return getName();
    }

    public static Period getItem(String id){
        return new Select().from(Period.class).where("id = ?", id).executeSingle();
    }
}
