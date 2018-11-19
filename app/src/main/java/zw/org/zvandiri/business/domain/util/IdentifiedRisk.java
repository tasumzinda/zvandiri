package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * @uthor Tasu Muzinda
 */
public enum IdentifiedRisk {

    DEPRESSION(1), POST_TRAUMA(2), SUICIDE(3), PSYCHOSIS(4);

    private final Integer code;

    IdentifiedRisk(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static final IdentifiedRisk get(Integer code){
        for(IdentifiedRisk item : values()){
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
