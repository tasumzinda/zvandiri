package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Cd4CountResultSource;
import zw.org.zvandiri.business.domain.util.TestType;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.Date;
import java.util.List;

@Table(name = "investigation_test", id = "_id")
public class InvestigationTest extends Model {

    @Column
    @Expose
    public String id;

    @Column
    @Expose
    public Patient patient;
    @Column
    public int pushed = 0;
    @Column(name = "is_new")
    public int isNew = 0;
    @Expose
    @Column
    public TestType testType;
    @Expose
    @Column
    public Date dateTaken;
    @Expose
    @Column
    public Date nextTestDate;
    @Expose
    @Column
    public Integer result;
    @Expose
    @Column
    public String tnd;
    public YesNo resultTaken;
    @Expose
    @Column
    public Cd4CountResultSource source;

    public static InvestigationTest getItem(Long id) {
        return new Select()
                .from(InvestigationTest.class).where("_id = ?", id).executeSingle();
    }

    public static List<InvestigationTest> findByPatient(Patient patient) {
        return new Select()
                .from(InvestigationTest.class)
                .where("patient = ?", patient.getId())
                .execute();
    }

    public static List<InvestigationTest> getAll(){
        return new Select()
                .from(InvestigationTest.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(InvestigationTest.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(InvestigationTest.class).execute();
    }

    @Override
    public String toString() {
        return patient.name != null ? patient.name : patient.firstName + " " + patient.lastName;
    }
}
