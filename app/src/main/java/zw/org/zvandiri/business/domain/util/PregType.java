package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum PregType {

    VAGINAL(1), CESAREAN_SECTION(2);

    private final Integer code;

    private PregType(Integer code) {
        this.code = code;
    }

    public static PregType get(Integer code) {
        switch (code) {
            case 1:
                return VAGINAL;
            case 2:
                return CESAREAN_SECTION;
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
