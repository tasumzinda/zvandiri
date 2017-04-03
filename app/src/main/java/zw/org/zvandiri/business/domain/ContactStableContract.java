package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by jackie muzinda on 16/1/2017.
 */
@Table(name = "contact_stable", id = "_id")
public class ContactStableContract extends Model {

    //@Expose
    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "stable_id")
    public Stable stable;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactStableContract() {
        super();
    }

    public static List<ContactStableContract> findByContact(Contact c){
        return new Select()
                .from(ContactStableContract.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }
}
