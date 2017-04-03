package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum FollowUp {

    STABLE(1), ENHANCED(2), INTENSIVE(3), VST(4), YOUNG_MOTHERS_GROUP(5), YOUTH_GROUP(6);

    private final Integer code;

    private FollowUp(Integer code) {
        this.code = code;
    }

    public static FollowUp get(Integer code) {
        switch (code) {
            case 1:
                return STABLE;
            case 2:
                return ENHANCED;
            case 3:
                return INTENSIVE;
            case 4:
                return VST;
            case 5:
                return YOUNG_MOTHERS_GROUP;
            case 6:
                return YOUTH_GROUP;
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
