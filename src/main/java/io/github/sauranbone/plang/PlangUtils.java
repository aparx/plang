package io.github.sauranbone.plang;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Collection of general utilities for the Plang-Library.
 *
 * @author Vinzent Zeband
 * @version 05:30 CET, 13.02.2022
 * @since 1.0
 */
public class PlangUtils {

    /**
     * All special characters of regular expression.
     */
    public static final String REGEX_SPECIAL_CHARACTERS = "<([{\\^-=$!|]})?*+.>";


    /**
     * Escapes every character in {@code str} that is one of regular
     * expression special characters and returns the mutated and regex
     * escaped string.
     * <p>If {@code str} is null or empty, an empty string is returned.
     *
     * @param str the string to be escaped
     * @return the escaped string
     * @apiNote Alternative to {@link Pattern#quote(String)}.
     * @see #REGEX_SPECIAL_CHARACTERS
     */
    public static String escapeRegex(String str) {
        if (StringUtils.isEmpty(str))
            return StringUtils.EMPTY;
        final int n = str.length();
        StringBuilder builder = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            char ch = str.charAt(i);
            if (REGEX_SPECIAL_CHARACTERS.indexOf(ch) != -1) {
                builder.append('\\');
            }
            builder.append(ch);
        }
        return builder.toString();
    }


}
