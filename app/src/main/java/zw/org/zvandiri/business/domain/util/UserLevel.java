package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum UserLevel {

    NATIONAL(1), PROVINCE(2), DISTRICT(3), FACILITY(4);

    private final Integer code;

    private UserLevel(Integer code) {
        this.code = code;
    }

    public static UserLevel get(Integer code) {
        switch (code) {
            case 1:
                return NATIONAL;
            case 2:
                return PROVINCE;
            case 3:
                return DISTRICT;
            case 4:
                return FACILITY;
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
