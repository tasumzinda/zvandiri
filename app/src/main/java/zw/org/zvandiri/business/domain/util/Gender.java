package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/10/2016.
 */
public enum Gender {
    MALE(1), FEMALE(2), OTHER(3);

    private final Integer code;

    private Gender(Integer code) {
        this.code = code;
    }

    public static Gender get(Integer code) {
        switch (code) {
            case 1:
                return MALE;
            case 2:
                return FEMALE;
            case 3:
                return OTHER;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method :" + code);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
