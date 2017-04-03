package zw.org.zvandiri.business.domain.util;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum PeriodQuarter {

    FIRST_QUARTER(1), SECOND_QUARTER(2), THIRD_QUARTER(3), LAST_QUARTER(4);

    private final Integer code;

    private PeriodQuarter(Integer code) {
        this.code = code;
    }

    public static PeriodQuarter get(Integer code) {
        switch (code) {
            case 1:
                return FIRST_QUARTER;
            case 2:
                return SECOND_QUARTER;
            case 3:
                return THIRD_QUARTER;
            case 4:
                return LAST_QUARTER;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method :" + code);
        }
    }

    public Integer getCode() {
        return code;
    }
}
