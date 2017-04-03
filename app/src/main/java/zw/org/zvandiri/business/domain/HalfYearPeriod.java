package zw.org.zvandiri.business.domain;

import android.content.Context;
import android.util.Base64;
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

import java.util.*;

import static zw.org.zvandiri.business.util.DateUtil.getYearMonthName;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "half_year_period", id = "_id")
public class HalfYearPeriod extends Model {

    //private static final String URL = "http://pzat.org:8089/zvandiri-mobile/rest/mobile/static/half-year-period";
    private static final String URL = "http://192.168.20.39:8084/zvandiri-mobile/rest/mobile/static/half-year-period";
    @Expose
    @Column(name = "uuid")
    public String uuid;

    @Expose
    @Column(name = "created_by")
    public User createdBy;

    @Expose
    @Column(name = "modified_by")
    public User modifiedBy;

    @Expose
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

    public HalfYearPeriod() {
        super();
    }

    public HalfYearPeriod(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public HalfYearPeriod(Date startDate, Date endDate, PeriodType periodType) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodType = periodType;
    }

    public List<HalfYearPeriod> getAll() {
        return new Select().from(HalfYearPeriod.class).execute();
    }

    public String getName() {
        return getYearMonthName(startDate) + " - " + getYearMonthName(endDate);
    }

    public String toString() {
        return getName();
    }

    public static void fetchRemote(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<HalfYearPeriod> halfYearPeriodList = Arrays.asList(AppUtil.createGson().fromJson(response, HalfYearPeriod[].class));
                        for(HalfYearPeriod halfYearPeriod : halfYearPeriodList){
                            Log.d("Saving hfPeriod", halfYearPeriod.getName());
                            halfYearPeriod.save();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Half Year Period", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", AppUtil.getUsername(context), AppUtil.getPassword(context)).getBytes(), Base64.DEFAULT)));*/
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "jmuzinda@gmail.com", "16-JudgE@84").getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }
        };
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }
}
