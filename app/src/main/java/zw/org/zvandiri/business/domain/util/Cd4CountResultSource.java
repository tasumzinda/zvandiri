package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum Cd4CountResultSource {

    SELF_REPORT(1), CARD_REVIEW(2), CLINIC(3);

    private final Integer code;

    private Cd4CountResultSource(Integer code) {
        this.code = code;
    }

    public static Cd4CountResultSource get(Integer code) {
        switch (code) {
            case 1:
                return SELF_REPORT;
            case 2:
                return CARD_REVIEW;
            case 3:
                return CLINIC;
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
