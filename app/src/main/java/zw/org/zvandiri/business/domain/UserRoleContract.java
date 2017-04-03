package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by Tasunungurwa Muzinda on 12/10/2016.
 */
@Table(name = "user_role", id = "_id")
public class UserRoleContract extends Model {

    @Expose
    @Column(name = "user")
    public User user;

    @Expose
    @Column(name = "role")
    public UserRole userRole;

    public UserRoleContract() {
        super();
    }
}
