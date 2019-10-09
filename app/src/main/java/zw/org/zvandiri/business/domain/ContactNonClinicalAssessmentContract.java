package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import java.util.List;

@Table(name = "contact_non_clinical_assessment", id = "_id")
public class ContactNonClinicalAssessmentContract extends Model {

    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "assessment_id")
    public Assessment assessment;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactNonClinicalAssessmentContract() {
        super();
    }

    public static List<ContactNonClinicalAssessmentContract> findByContact(Contact c){
        return new Select()
                .from(ContactNonClinicalAssessmentContract.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }

    public static void deleteItem(String id){
    }
}
