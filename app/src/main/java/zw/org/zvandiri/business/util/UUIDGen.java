package zw.org.zvandiri.business.util;

/**
 * Created by Tasu Muzinda on 12/12/2016.
 */

import java.util.UUID;

public class UUIDGen {

    public UUIDGen() {
        throw new IllegalStateException("Class not meant to be instantiated");
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
