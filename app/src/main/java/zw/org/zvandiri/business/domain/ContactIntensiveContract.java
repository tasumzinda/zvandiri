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
@Table(name = "contact_intensive", id = "_id")
public class ContactIntensiveContract extends Model {

    //@Expose
    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "intensive_id")
    public Intensive intensive;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactIntensiveContract() {
        super();
    }

    public static List<ContactIntensiveContract> findByContact(Contact c){
        return new Select()
                .from(ContactIntensiveContract.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }
}
