package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum ReferralStatus {

    ROUTINE(1), URGENT(2), EMERGENCY(3);

    private final Integer code;

    private ReferralStatus(Integer code) {
        this.code = code;
    }

    public static ReferralStatus get(Integer code) {
        switch (code) {
            case 1:
                return ROUTINE;
            case 2:
                return URGENT;
            case 3:
                return EMERGENCY;
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
