package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.PeriodType;

import java.util.Date;
import java.util.List;

import static zw.org.zvandiri.business.util.DateUtil.getYearMonthName;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "year_period", id = "_id")
public class YearPeriod extends Model {

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

    public YearPeriod() {
        super();
    }

    public YearPeriod(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public YearPeriod(Date startDate, Date endDate, PeriodType periodType) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodType = periodType;
    }

    public List<YearPeriod> getAll() {
        return new Select().from(YearPeriod.class).execute();
    }

    public String getName() {
        return getYearMonthName(startDate) + " - " + getYearMonthName(endDate);
    }
}
