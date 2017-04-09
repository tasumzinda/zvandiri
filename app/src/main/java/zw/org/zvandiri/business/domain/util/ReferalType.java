package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

/**
 * Created by User on 4/7/2017.
 */
public enum ReferalType {

    HIV_STI_PREVENTION(1), OI_ART_SERVICES(2), SRH_SERVICES(3),
    PSYCHO_SOCIAL_SUPPORT(4), LABORATORY_DIAGNOSES(5),
    TB_SERVICES(6), LEGAL_SUPPORT(7);

    private final Integer code;

    private ReferalType(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    public static ReferalType get(Integer code){
        for(ReferalType item : values()){
            if(code.equals(item.getCode())) return item;
        }
        throw new IllegalArgumentException("Illegal parameter passed to method :"+code);
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}
