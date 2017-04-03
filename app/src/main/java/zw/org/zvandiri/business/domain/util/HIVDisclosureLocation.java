package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum HIVDisclosureLocation {

    CLINIC(1), HOSPITAL(2), ZVANDIRI(3), HOME(4), OTHER(5);

    private final Integer code;

    private HIVDisclosureLocation(Integer code) {
        this.code = code;
    }

    public static HIVDisclosureLocation get(Integer code) {
        switch (code) {
            case 1:
                return CLINIC;
            case 2:
                return HOSPITAL;
            case 3:
                return ZVANDIRI;
            case 4:
                return HOME;
            case 5:
                return OTHER;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method  expected {1-5} :" + code);

        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
