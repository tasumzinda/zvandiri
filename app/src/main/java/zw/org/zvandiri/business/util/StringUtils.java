package zw.org.zvandiri.business.util;

import zw.org.zvandiri.toolbox.Log;

/**
 * Created by Tasu Nuzinda on 12/10/2016.
 */
public class StringUtils {

    public static String toCamelCase3(String c) {
        if (c == null || c.trim().length() == 0) {
            return c;
        }

        if (!c.trim().contains("_")) {
            return capitalizeWord(c);
        }

        String[] arrayWords = c.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arrayWords.length; i++) {
            sb.append(capitalizeWord(arrayWords[i].concat(" ")));
        }

        return sb.toString();
    }

    private static String capitalizeWord(String word) {

        if (word != null && !word.trim().equals("")) {
            return word.substring(0, 1).toUpperCase() + word.substring(1, word.length()).toLowerCase();
        } else {
            return word;
        }
    }
}
