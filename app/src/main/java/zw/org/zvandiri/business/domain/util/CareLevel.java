package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum CareLevel {

    STABLE(1), ENHANCED(2);

    private final Integer code;

    private CareLevel(Integer code) {
        this.code = code;
    }

    public static CareLevel get(Integer code) {
        switch (code) {
            case 1:
                return STABLE;
            case 2:
                return ENHANCED;
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
