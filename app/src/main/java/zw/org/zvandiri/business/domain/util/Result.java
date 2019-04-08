package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * @uthor Tasu Muzinda
 */
public enum Result {
    POSITIVE(1), NEGATIVE(2);
    private final Integer code;

    Result(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static Result get(Integer code) {
        for(Result item : values()){
            if(item.getCode().equals(code))
                return item;
        }
        throw new IllegalArgumentException("Unknown parameter passes to method: " + code);
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
