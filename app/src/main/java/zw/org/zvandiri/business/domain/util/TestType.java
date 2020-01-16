package zw.org.zvandiri.business.domain.util;

import zw.org.zvandiri.business.util.StringUtils;

public enum TestType {
    VIRAL_LOAD(1), CD4_COUNT(2);

    private final Integer code;

    TestType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static TestType get(Integer code) {
        for (TestType testType : values()) {
            if (testType.getCode().equals(code)) {
                return testType;
            }
        }
        throw new IllegalStateException("Illegal parameter passed to method : "+ code);
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}
