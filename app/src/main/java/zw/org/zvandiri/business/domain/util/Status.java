package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum Status {
    ON_GOING(1), RESOLVED(2);

    private final Integer code;

    private Status(Integer code) {
        this.code = code;
    }

    public static Status get(Integer code) {
        switch (code) {
            case 1:
                return ON_GOING;
            case 2:
                return RESOLVED;
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
