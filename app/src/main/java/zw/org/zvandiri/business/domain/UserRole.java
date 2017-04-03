package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/10/2016.
 */
@Table(name = "role", id = "_id")
public class UserRole extends Model {

    @Expose
    @Column(name = "uuid")
    public String uuid;

    @Expose
    @Column(name = "created_by", onDelete = Column.ForeignKeyAction.CASCADE)
    public User createdBy;

    @Expose
    @Column(name = "modified_by", onDelete = Column.ForeignKeyAction.CASCADE)
    public User modifiedBy;

    @Expose
    @Column(name = "date_created")
    public Date dateCreated;

    @Expose
    @Column(name = "date_modified")
    public Date dateModified;

    @Expose
    @Column(name = "version")
    public Long version;

    @Expose
    @Column(name = "active")
    public Boolean active = Boolean.TRUE;

    @Expose
    @Column(name = "deleted")
    public Boolean deleted = Boolean.FALSE;

    @Expose
    @Column(name = "id")
    public String id;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "description")
    public String description;

    public UserRole() {
        super();
    }

    public static UserRole getItem(String id) {
        return new Select().from(UserRole.class).where("id = ?", id).executeSingle();
    }

    public static List<UserRole> getAll() {
        return new Select()
                .from(UserRole.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(UserRole.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(UserRole.class).execute();
    }

    public String toString() {
        return name;
    }
}
