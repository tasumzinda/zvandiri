package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum HIVStatus {

    POSITIVE(1), NEGATIVE(2), NOT_KNOWN(3);
    private final Integer code;

    private HIVStatus(Integer code) {
        this.code = code;
    }

    public static HIVStatus get(Integer code) {
        switch (code) {
            case 1:
                return POSITIVE;
            case 2:
                return NEGATIVE;
            case 3:
                return NOT_KNOWN;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method exp {1-3} :" + code);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
