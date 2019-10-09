package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

public enum TbIdentificationOutcome {

    REFERRED_FOR_TB_TREATMENT(1), ON_TB_TREATMENT(2);
    private final Integer code;

    private TbIdentificationOutcome(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static TbIdentificationOutcome get(Integer code) {
        for(TbIdentificationOutcome item : values()){
            if(item.getCode().equals(code))
                return item;
        }
        throw new IllegalArgumentException("Unknown parameter passes to method: " + code);
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
