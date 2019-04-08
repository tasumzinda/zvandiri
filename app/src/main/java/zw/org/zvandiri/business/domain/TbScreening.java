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
@Table(name = "tb_screening", id = "_id")
public class TbScreening extends Model {

    @Column
    @Expose
    public Person person;
    @Expose
    @Column
    public String id;
    @Expose
    @Column
    public YesNo coughing;
    @Expose
    @Column
    public YesNo sweating;
    @Expose
    @Column
    public YesNo weightLoss;
    @Expose
    @Column
    public YesNo fever;
    @Expose
    @Column
    public YesNo currentlyOnTreatment;
    @Expose
    @Column
    public String typeOfDrug;
    @Expose
    @Column
    public Result tbOutcome;
    @Expose
    @Column
    public TbTreatmentStatus tbTreatmentStatus;
    @Expose
    @Column
    public TbTreatmentOutcome tbTreatmentOutcome;

    public static TbScreening getItem(Long id) {
        return new Select()
                .from(TbScreening.class).where("_id = ?", id).executeSingle();
    }

    public static List<TbScreening> findByPerson(Person person) {
        return new Select()
                .from(TbScreening.class)
                .where("person = ?", person.getId())
                .execute();
    }

    public static List<TbScreening> getAll(){
        return new Select()
                .from(TbScreening.class)
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(TbScreening.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(TbScreening.class).execute();
    }

    @Override
    public String toString() {
        return person.nameOfClient;
    }
}
