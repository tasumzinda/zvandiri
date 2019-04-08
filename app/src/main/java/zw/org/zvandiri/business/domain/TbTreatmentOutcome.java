package zw.org.zvandiri.business.domain;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * @uthor Tasu Muzinda
 */
public enum TbTreatmentOutcome {
    SUCCESSFUL(1), FAILED(2);
    private final Integer code;

    private TbTreatmentOutcome(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static TbTreatmentOutcome get(Integer code) {
        for(TbTreatmentOutcome item : values()){
            if(item.getCode().equals(code))
                return item;
        }
        throw new IllegalArgumentException("Unknown parameter passes to method: " + code);
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
