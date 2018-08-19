package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Result;
import zw.org.zvandiri.business.domain.util.YesNo;

import java.util.List;

/**
 * @uthor Tasu Muzinda
 */
@Table(name = "hiv_self_testing", id = "_id")
public class HivSelfTesting extends Model {

    @Column
    @Expose
    public Result testedAtHealthFacilityResult;
    @Column
    @Expose
    public YesNo selfTestKitDistributed;
    @Column
    @Expose
    public Result hisSelfTestingResult;
    @Column
    @Expose
    public Result homeBasedTestingResult;
    @Column
    @Expose
    public Result confirmatoryTestingResult;
    @Column
    @Expose
    public YesNo artInitiation;
    @Column
    @Expose
    public Person person;
    @Expose
    @Column
    public String id;

    public static HivSelfTesting getItem(Long id) {
        return new Select()
                .from(HivSelfTesting.class).where("_id = ?", id).executeSingle();
    }

    public static List<HivSelfTesting> findByPerson(Person person) {
        return new Select()
                .from(HivSelfTesting.class)
                .where("person = ?", person.getId())
                .execute();
    }

    public static List<HivSelfTesting> getAll(){
        return new Select()
                .from(HivSelfTesting.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(HivSelfTesting.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Person.class).execute();
    }

    @Override
    public String toString() {
        return person.nameOfClient;
    }
}
