package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum MedicineInterval {
    DAILY(1), WEEKLY(2), BI_WEEKLY(3), MONTHLY(4);

    private final Integer code;

    private MedicineInterval(Integer code) {
        this.code = code;
    }

    public static MedicineInterval get(Integer code) {
        switch (code) {
            case 1:
                return DAILY;
            case 2:
                return WEEKLY;
            case 3:
                return BI_WEEKLY;
            case 4:
                return MONTHLY;
            default:
                throw new IllegalArgumentException("Illegal parmeter passed to method :" + code);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
