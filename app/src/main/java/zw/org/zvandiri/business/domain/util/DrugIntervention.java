package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum DrugIntervention {

    REHABILITATION(1), COUNSELLING(2);

    private final Integer code;

    private DrugIntervention(Integer code) {
        this.code = code;
    }

    public static DrugIntervention get(Integer code) {
        switch (code) {
            case 1:
                return REHABILITATION;
            case 2:
                return COUNSELLING;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method");
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
