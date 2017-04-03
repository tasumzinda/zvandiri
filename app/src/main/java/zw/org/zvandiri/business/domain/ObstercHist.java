package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.PregType;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "obsterc_hist", id = "_id")
public class ObstercHist extends Model {

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
    @Column(name = "patient")
    public Patient patient;
    @Expose
    @Column(name = "pregnant")
    public YesNo pregnant;
    @Expose
    @Column(name = "number_of_preg")
    public Integer numberOfPreg;
    @Expose
    @Column(name = "preg_current")
    public YesNo pregCurrent;
    @Expose
    @Column(name = "given_birth")
    public YesNo givenBirth;
    @Expose
    @Column(name = "preg_type")
    public PregType pregType;
    @Expose
    @Column(name = "children")
    public Integer children;
    @Expose
    @Column(name = "children_hiv_status")
    public String childrenHivStatus;
    @Column(name = "pushed")
    public boolean pushed = true;
    @Column(name = "is_new")
    public boolean isNew = false;

    public ObstercHist() {
        super();
    }

    public ObstercHist(Patient patient) {
        super();
        this.patient = patient;
    }

    public static ObstercHist getItem(String id) {
        return new Select()
                .from(ObstercHist.class).where("id = ?", id).executeSingle();
    }

    public static List<ObstercHist> getAll() {
        return new Select()
                .from(ObstercHist.class)
                .execute();
    }

    public static List<ObstercHist> findByPatient(Patient patient){
        return new Select()
                .from(ObstercHist.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<ObstercHist> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(ObstercHist.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(ObstercHist.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(ObstercHist.class).execute();
    }

    @Override
    public String toString(){
        return "Birth Type: " + pregType.getName();
    }

}
