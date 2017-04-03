package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum ARVDrugRegimen {
    FIRST_LINE(1), SECOND_LINE(2), THIRD_LINE(3);
    private final Integer code;

    private ARVDrugRegimen(Integer code) {
        this.code = code;
    }

    public static ARVDrugRegimen get(Integer code) {
        switch (code) {
            case 1:
                return FIRST_LINE;
            case 2:
                return SECOND_LINE;
            case 3:
                return THIRD_LINE;
            default:
                throw new IllegalArgumentException("Unknown parameter passed to method :" + code);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
