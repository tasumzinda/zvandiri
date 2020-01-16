package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.Support;

import java.util.List;

@Table(name = "mental_health_screening_support_contract", id = "_id")
public class MentalHealthScreeningSupportContract extends Model {

    @Expose
    @Column(name = "mental_health_screening")
    public MentalHealthScreening mentalHealthScreening;

    @Expose
    @Column(name = "support")
    public Support support;

    @Expose
    @Column(name = "id")
    public String id;

    public MentalHealthScreeningSupportContract() {
        super();
    }

    public static List<MentalHealthScreeningSupportContract> findByMentalHealthScreening(MentalHealthScreening c){
        return new Select()
                .from(MentalHealthScreeningSupportContract.class)
                .where("mental_health_screening = ?", c.getId())
                .execute();
    }
}
