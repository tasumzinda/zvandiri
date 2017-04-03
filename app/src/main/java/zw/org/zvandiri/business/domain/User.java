package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.zvandiri.business.domain.util.Gender;
import zw.org.zvandiri.business.domain.util.UserType;

import java.util.Date;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/10/2016.
 */
@Table(name = "user", id = "_id")
public class User extends Model {

    @Expose
    @Column(name = "uuid")
    public String uuid;

    @Expose
    @Column(name = "created_by")
    public User createdBy;

    @Expose
    @Column(name = "modified_by")
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
    @Column(name = "first_name")
    public String firstName;

    @Expose
    @Column(name = "last_name")
    public String lastName;

    @Expose
    @Column(name = "user_name")
    public String userName;

    @Expose
    @Column(name = "password")
    public String password;

    @Expose
    @Column(name = "gender")
    public Gender gender;
    @Expose
    @Column(name = "user_type")
    public UserType userType;

    public User() {
        super();
    }

    public static boolean checkDuplicate(String userName) {
        boolean userExists = true;
        User obj = new Select().from(User.class).where("user_name = ?", userName).executeSingle();
        if (obj != null) {
            userExists = false;
        }
        return userExists;
    }

    public static User userExists(String userName) {
        return new Select().from(User.class).where("user_name = ?", userName).executeSingle();
    }

    public static User getItem(String id) {
        return new Select().from(User.class).where("id = ?", id).executeSingle();
    }

    public static List<User> getAll() {
        return new Select()
                .from(User.class)
                .orderBy("last_name ASC")
                .execute();
    }

    public static void deleteAll() {
        new Delete().from(User.class).execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(User.class).where("id = ?", id).executeSingle();
    }

    public static User getFromServer(JSONObject jsonObject) {
        User user = new User();
        try {
            user.active = jsonObject.getBoolean("active");
            user.createdBy = getItem(jsonObject.getString("id"));
            //user.dateCreated = jsonObject.
        } catch (JSONException exc) {

        }
        return user;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
