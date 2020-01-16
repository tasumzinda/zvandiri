package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

public enum DisabilitySeverity {

    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    private final Integer code;

    private DisabilitySeverity(Integer code) {
        this.code = code;
    }

    public Integer getCode () {
        return code;
    }

    public static DisabilitySeverity get(Integer code) {
        for(DisabilitySeverity item : values()) {
            if(code.equals(item.getCode())){
                return item;
            }
        }
        throw new IllegalArgumentException("Illegal parameter passed to method : "+ code);
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}
