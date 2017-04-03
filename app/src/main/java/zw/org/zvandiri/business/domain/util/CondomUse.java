package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum CondomUse {

    ALWAYS(1), SOMETIMES(2), NEVER(3);

    private final Integer code;

    private CondomUse(Integer code) {
        this.code = code;
    }

    public static CondomUse get(Integer code) {
        switch (code) {
            case 1:
                return ALWAYS;
            case 2:
                return SOMETIMES;
            case 3:
                return NEVER;
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
