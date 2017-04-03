package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "contact_assessment", id = "_id")
public class ContactAssessmentContract extends Model {

    //@Expose
    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "assessment_id")
    public Assessment assessment;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactAssessmentContract() {
        super();
    }

    public static List<ContactAssessmentContract> findByContact(Contact c){
        return new Select()
                .from(ContactAssessmentContract.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }

    public static void deleteItem(String id){
    }
}
