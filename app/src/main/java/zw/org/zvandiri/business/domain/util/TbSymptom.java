package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

public enum TbSymptom {

    COUGH(1), NIGHT_SWEATS(2), WEIGHT_LOSS(3), FEVER(4);

    private final Integer code;

    private TbSymptom(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    public static TbSymptom get(Integer code){
        for (TbSymptom item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Illegal parameter passed to method :"+code);
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}
