package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * @uthor Tasu Muzinda
 */
public enum MentalScreenResult {

    IMPROVEMENT(1), NO_IMPROVEMENT(2);

    private final Integer code;

    MentalScreenResult(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static final MentalScreenResult get(Integer code){
        for(MentalScreenResult item : values()){
            if(item.getCode().equals(code)){
                return item;
            }
        }
        throw new IllegalArgumentException("Parameter passed to method not recognized:" + code);
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}
