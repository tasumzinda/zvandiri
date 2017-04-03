package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.HIVStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "dependent", id = "_id")
public class Dependent extends Model {

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
    @Column(name = "first_name")
    public String firstName;
    @Expose
    @Column(name = "last_name")
    public String lastName;
    @Expose
    @Column(name = "gender")
    public Gender gender;
    @Expose
    @Column(name = "date_of_birth")
    public Date dateOfBirth;
    @Expose
    @Column(name = "hiv_status")
    public HIVStatus hivStatus;
    @Column(name = "pushed")
    public boolean pushed = true;
    @Column(name = "is_new")
    public boolean isNew = false;

    public Dependent() {
        super();
    }

    public Dependent(Patient patient) {
        super();
        this.patient = patient;
    }

    public static Dependent getItem(String id) {
        return new Select()
                .from(Dependent.class).where("id = ?", id).executeSingle();
    }

    public static List<Dependent> getAll() {
        return new Select()
                .from(Dependent.class)
                .orderBy("last_name ASC")
                .execute();
    }

    public static List<Dependent> findByPatient(Patient patient){
        return new Select()
                .from(Dependent.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<Dependent> findByPatientAndPushed(Patient patient){
        return new Select()
                .from(Dependent.class)
                .where("patient = ?", patient.getId())
                .and("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(Dependent.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Dependent.class).execute();
    }

    public String toString(){
        return firstName + " " + lastName;
    }
}
