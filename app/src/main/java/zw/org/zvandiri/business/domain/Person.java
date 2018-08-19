package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Gender;

import java.util.Date;
import java.util.List;

/**
 * @uthor Tasu Muzinda
 */
@Table(name = "person", id = "_id")
public class Person extends Model {

    @Expose
    @Column
    public String nameOfClient;
    @Expose
    @Column
    public Gender gender;
    @Expose
    @Column
    public Integer age;
    @Column
    public Date dateOfBirth;
    @Expose
    @Column
    public District district;
    @Expose
    @Column
    public String nameOfMother;
    @Expose
    public String dob;
    @Column
    public int pushed = 0;
    @Expose
    @Column
    public String id;

    public static Person getItem(Long id) {
        return new Select()
                .from(Person.class).where("_id = ?", id).executeSingle();
    }

    public static Person findById(String id) {
        return new Select()
                .from(Person.class).where("id = ?", id).executeSingle();
    }

    public static List<Person> getAll() {
        return new Select()
                .from(Person.class)
                .orderBy("nameOfClient ASC")
                .execute();
    }

    public static List<Person> findNew(){
        return new Select()
                .from(Person.class)
                .where("pushed = ?", 0)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(Person.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Person.class).execute();
    }

    @Override
    public String toString() {
        return nameOfClient;
    }
}
