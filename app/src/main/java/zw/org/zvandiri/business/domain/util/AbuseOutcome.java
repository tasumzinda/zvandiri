package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum AbuseOutcome {

    CHILD_REMOVAL_FROM_HOME(1), STILL_ONGOING(2), STOPPED(3), PERPETRATOR_ARRESTED(4);

    private final Integer code;

    private AbuseOutcome(Integer code) {
        this.code = code;
    }

    public static AbuseOutcome get(Integer code) {
        switch (code) {
            case 1:
                return CHILD_REMOVAL_FROM_HOME;
            case 2:
                return STILL_ONGOING;
            case 3:
                return STOPPED;
            case 4:
                return PERPETRATOR_ARRESTED;
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
