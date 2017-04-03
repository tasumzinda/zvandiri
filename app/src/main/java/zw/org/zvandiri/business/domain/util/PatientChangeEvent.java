package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
public enum PatientChangeEvent {

    DECEASED(1), LOST_TO_FOLOWUP(2), GRADUATED(3), CHANGE_LOCATION(4), OPT_OUT(5), ACTIVE(6), REINSTATED(7);

    private final Integer code;

    private PatientChangeEvent(Integer code) {
        this.code = code;
    }

    public static PatientChangeEvent get(Integer code) {
        switch (code) {
            case 1:
                return DECEASED;
            case 2:
                return LOST_TO_FOLOWUP;
            case 3:
                return GRADUATED;
            case 4:
                return CHANGE_LOCATION;
            case 5:
                return OPT_OUT;
            case 6:
                return ACTIVE;
            case 7:
                return REINSTATED;
            default:
                throw new IllegalArgumentException("Illegal parameter passed tp method :" + code);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }

    public List<PatientChangeEvent> getDropItems() {
        List<PatientChangeEvent> items = new ArrayList<>();
        items.add(DECEASED);
        items.add(GRADUATED);
        items.add(OPT_OUT);
        items.add(REINSTATED);
        return items;
    }
}
