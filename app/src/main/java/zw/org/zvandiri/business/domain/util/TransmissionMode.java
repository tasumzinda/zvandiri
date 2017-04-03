package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum TransmissionMode {

    VERTICAL(1), HORIZONTAL(2), SEXUAL_ABUSE(3), NOT_KNOWN(4);

    private final Integer code;

    private TransmissionMode(Integer code) {
        this.code = code;
    }

    public static TransmissionMode get(Integer code) {
        switch (code) {
            case 1:
                return VERTICAL;
            case 2:
                return HORIZONTAL;
            case 3:
                return SEXUAL_ABUSE;
            case 4:
                return NOT_KNOWN;
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
