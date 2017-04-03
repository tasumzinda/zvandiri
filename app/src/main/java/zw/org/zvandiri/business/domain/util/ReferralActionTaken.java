package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum ReferralActionTaken {

    SERVICES_PROVIDED(1), SERVICES_NOT_PROVIDED(2), REFERRED_TO_ANOTHER_AGENCY(3);

    private final Integer code;

    private ReferralActionTaken(Integer code) {
        this.code = code;
    }

    public static ReferralActionTaken get(Integer code) {
        switch (code) {
            case 1:
                return SERVICES_PROVIDED;
            case 2:
                return SERVICES_NOT_PROVIDED;
            case 3:
                return REFERRED_TO_ANOTHER_AGENCY;
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
