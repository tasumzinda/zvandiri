package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

@Table(name = "contact_service_offered", id = "_id")
public class ContactServiceOfferedContract extends Model {

    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "service_id")
    public ServiceOffered serviceOffered;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactServiceOfferedContract() {
        super();
    }

    public static List<ContactServiceOfferedContract> findByContact(Contact c){
        return new Select()
                .from(ContactServiceOfferedContract.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }

    public static void deleteItem(String id){
    }
}
