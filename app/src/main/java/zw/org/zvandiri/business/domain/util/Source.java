package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum Source {

    SELF_REPORT(1), CLINIC(2), OTHER(3);

    private final Integer code;

    private Source(Integer code) {
        this.code = code;
    }

    public static Source get(Integer code) {
        switch (code) {
            case 1:
                return SELF_REPORT;
            case 2:
                return CLINIC;
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
