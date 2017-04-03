package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by User on 4/3/2017.
 */
@Table(name = "contact_action_taken", id = "_id")
public class ContactActionTakenContract extends Model {

    //@Expose
    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "action_taken")
    public ActionTaken actionTaken;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactActionTakenContract() {
        super();
    }

    public static List<ContactActionTakenContract> findByContact(Contact c){
        return new Select()
                .from(ContactActionTakenContract.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }

    public static void deleteItem(String id){
    }
}
