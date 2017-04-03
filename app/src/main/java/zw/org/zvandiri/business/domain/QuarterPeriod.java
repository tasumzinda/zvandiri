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
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "quarter_period", id = "_id")
public class QuarterPeriod extends Model {

    private static final String GET_URL = "http://pzat.org:8089/zvandiri";
    private final Response.ErrorListener onQuarterPeriodsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("Quarter period error", error.toString());
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
    private final Response.Listener<String> onQuarterPeriodsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<QuarterPeriod> quarterPeriodList = Arrays.asList(AppUtil.createGson().fromJson(response, QuarterPeriod[].class));
            for (QuarterPeriod quarterPeriod : quarterPeriodList) {
                Log.d("saving quarter period", quarterPeriod.getName());
                quarterPeriod.save();
            }
        }
    };
    @Expose
    @Column(name = "period_type")
    public PeriodType periodType;

    public QuarterPeriod() {
        super();
    }

    public QuarterPeriod(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public QuarterPeriod(Date startDate, Date endDate, PeriodType periodType) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodType = periodType;
    }

    public List<QuarterPeriod> getAll() {
        return new Select().from(QuarterPeriod.class).execute();
    }

    public String getName() {
        return getYearMonthName(startDate) + " - " + getYearMonthName(endDate);
    }

    public void fetchQuarterPeriods(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_URL, onQuarterPeriodsLoaded, onQuarterPeriodsError);
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }

    public String toString() {
        return getName();
    }
}
