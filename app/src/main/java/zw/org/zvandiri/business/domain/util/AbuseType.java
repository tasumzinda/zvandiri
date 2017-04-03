package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum AbuseType {

    PHYSICAL_ABUSE(1), SEXUAL_ABUSE(2), EMOTIONAL_ABUSE(3);

    private final Integer code;

    private AbuseType(Integer code) {
        this.code = code;
    }

    public static AbuseType get(Integer code) {
        switch (code) {
            case 1:
                return PHYSICAL_ABUSE;
            case 2:
                return SEXUAL_ABUSE;
            case 3:
                return EMOTIONAL_ABUSE;
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
