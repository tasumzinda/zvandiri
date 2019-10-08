package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

public enum ContactPhoneOption {

    CALL(1), SMS(2);

    private final Integer code;

    private ContactPhoneOption(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static ContactPhoneOption get(Integer code) {
        for (ContactPhoneOption item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Un recognised code passed to method : " + code);
    }

    public String getName() {
        return StringUtils.toCamelCase3(super.name());
    }
}
