package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "contact_clinical_assessment", id = "_id")
public class ContactClinicalAssessmentContract extends Model {

    //@Expose
    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "assessment_id")
    public Assessment assessment;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactClinicalAssessmentContract() {
        super();
    }

    public static List<ContactClinicalAssessmentContract> findByContact(Contact c){
        return new Select()
                .from(ContactClinicalAssessmentContract.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }

    public static void deleteItem(String id){
    }
}
