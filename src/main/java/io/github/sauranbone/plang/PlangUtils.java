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

    private static final String ESCAPE_STRING = "\\";

    /**
     * Escapes the given {@code cp} if that is one of regular expression
     * special characters and returns the mutated and regex escaped
     * string.
     * <p>If {@code str} is null or empty, an empty string is returned.
     *
     * @param cp the character to be escaped, if required
     * @return the escaped string
     * @apiNote Alternative to {@link Pattern#quote(String)}.
     * @see #REGEX_SPECIAL_CHARACTERS
     */
    public static String escapeRegex(char cp) {
        return (isSpecialRegexCharacter(cp)
                ? ESCAPE_STRING : StringUtils.EMPTY)
                + cp;
    }

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
        final char escp = '\\';
        if (n == 1) {
            //Fastpath regex escaping for single character
            return escapeRegex(str.charAt(0));
        }
        StringBuilder builder = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            char ch = str.charAt(i);
            if (isSpecialRegexCharacter(ch)) {
                //Append escaping to this character
                builder.append(escp);
            }
            builder.append(ch);
        }
        return builder.toString();
    }

    /**
     * Returns true if {@code ch} is one of the regular expressions special
     * characters that are defined in this constant.
     *
     * @param ch the character to test
     * @return false if {@code ch} is not a special character that can be
     * escaped
     * @see #REGEX_SPECIAL_CHARACTERS
     */
    public static boolean isSpecialRegexCharacter(char ch) {
        if (ch < 21 || ch > 0x7d)
            return false;
        return REGEX_SPECIAL_CHARACTERS.indexOf(ch) != -1;
    }

    public static Class<?> getTopClass(Class<?> acceptingType) {
        //TODO
        return acceptingType;
    }
}
