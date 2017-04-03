package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum Reason {

    SELF_REFERRAL(1), EXTERNAL_REFERRAL(2), INTERNAL_REFERRAL(3), SCHEDULED_CONTACT(4);

    private final Integer code;

    private Reason(Integer code) {
        this.code = code;
    }

    public static Reason get(Integer code) {
        switch (code) {
            case 1:
                return SELF_REFERRAL;
            case 2:
                return EXTERNAL_REFERRAL;
            case 3:
                return INTERNAL_REFERRAL;
            case 4:
                return SCHEDULED_CONTACT;
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
