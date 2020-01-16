package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * @uthor Tasu Muzinda
 */
public enum IdentifiedRisk {

    DEPRESSION(1), ANXIETY(2), POST_TRAUMATIC_STRESS(3), SUBSTANCE_ABUSE(4), SUICIDE(5), PSYCHOSIS(6);

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

    @Override
    public String toString() {
        return super.name().replace("_", " ");
    }
}
