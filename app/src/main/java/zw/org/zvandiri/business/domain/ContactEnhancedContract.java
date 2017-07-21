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
@Table(name = "contact_enhanced", id = "_id")
public class ContactEnhancedContract extends Model {

    //@Expose
    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "enhanced_id")
    public Enhanced enhanced;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactEnhancedContract() {
        super();
    }

    public static List<ContactEnhancedContract> findByContact(Contact c){
        return new Select()
                .from(ContactEnhancedContract.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }

    public static List<ContactEnhancedContract> getAll(){
        return new Select()
                .from(ContactEnhancedContract.class)
                .execute();
    }
}
