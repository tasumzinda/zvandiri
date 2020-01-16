package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

@Table(name = "contact_lab_task", id = "_id")
public class ContactLabTaskContact extends Model {
    @Column(name = "contact_id")
    public Contact contact;

    @Expose
    @Column(name = "lab_task_id")
    public LabTask labTask;

    @Expose
    @Column(name = "id")
    public String id;

    public ContactLabTaskContact() {
        super();
    }

    public static List<ContactLabTaskContact> findByContact(Contact c){
        return new Select()
                .from(ContactLabTaskContact.class)
                .where("contact_id = ?", c.getId())
                .execute();
    }

    public static List<ContactLabTaskContact> getAll(){
        return new Select()
                .from(ContactLabTaskContact.class)
                .execute();
    }
}
