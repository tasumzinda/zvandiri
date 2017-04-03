package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum AgeGroup {

    UNDER_FIVE(0, 4), FIVE_TO_NINE(5, 9), TEN_TO_FOURTEEN(10, 14),
    FIFTEEN_TO_NINETEEN(15, 19), TWENTY_TO_TWENTY_FOUR(20, 24);

    private final Integer start;
    private final Integer end;

    private AgeGroup(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public static AgeGroup get(Integer start) {
        switch (start) {
            case 0:
                return UNDER_FIVE;
            case 5:
                return FIVE_TO_NINE;
            case 10:
                return TEN_TO_FOURTEEN;
            case 15:
                return FIFTEEN_TO_NINETEEN;
            case 20:
                return TWENTY_TO_TWENTY_FOUR;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method :" + start);
        }
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
