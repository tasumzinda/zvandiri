package zw.org.zvandiri.business.domain;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * @uthor Tasu Muzinda
 */
public enum TbTreatmentStatus {
    ACTIVE_ON_TB_TREATMENT(1), DEFAULTED_ON_TB_TREATMENT(2), COMPLETED_TREATMENT(3);
    private final Integer code;

    private TbTreatmentStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static TbTreatmentStatus get(Integer code) {
        for(TbTreatmentStatus item : values()){
            if(item.getCode().equals(code))
                return item;
        }
        throw new IllegalArgumentException("Unknown parameter passes to method: " + code);
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
