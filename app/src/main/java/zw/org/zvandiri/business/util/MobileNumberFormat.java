package zw.org.zvandiri.business.util;

/**
 * Created by tasu on 8/29/17.
 */
public class MobileNumberFormat {

    public static String ZIMBABWE="\\d{10}";

    private MobileNumberFormat(){
        throw new IllegalStateException("Class not meant to be initialized");
    }
}
