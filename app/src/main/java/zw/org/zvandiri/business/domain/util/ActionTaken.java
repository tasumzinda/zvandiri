package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum ActionTaken {

    INTERNAL_REFERRAL(1), EXTERNAL_REFERRAL(2), RESOLVED(3);

    private final Integer code;

    private ActionTaken(Integer code) {
        this.code = code;
    }

    public static ActionTaken get(Integer code) {
        switch (code) {
            case 1:
                return INTERNAL_REFERRAL;
            case 2:
                return EXTERNAL_REFERRAL;
            case 3:
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
