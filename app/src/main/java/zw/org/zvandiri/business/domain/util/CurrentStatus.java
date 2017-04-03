package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum CurrentStatus {

    RESOLVED(1), STABLE_ON_MEDICATION(2), UNSTABLE(3);

    private final Integer code;

    private CurrentStatus(Integer code) {
        this.code = code;
    }

    public static CurrentStatus get(Integer code) {
        switch (code) {
            case 1:
                return RESOLVED;
            case 2:
                return STABLE_ON_MEDICATION;
            case 3:
                return UNSTABLE;
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
