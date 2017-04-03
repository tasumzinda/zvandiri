package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/10/2016.
 */
public enum UserType {

    ZVANDIRI_STAFF(1), CATS(2), ADMINISTRATOR(3);

    private final Integer code;

    private UserType(Integer code) {
        this.code = code;
    }

    public static UserType get(Integer code) {
        switch (code) {
            case 1:
                return ZVANDIRI_STAFF;
            case 2:
                return CATS;
            case 3:
                return ADMINISTRATOR;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method :" + code);
        }
    }

    public static List<UserType> getZvandiriStaff() {
        List<UserType> items = new ArrayList<>();
        items.add(ZVANDIRI_STAFF);
        items.add(CATS);
        return items;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
