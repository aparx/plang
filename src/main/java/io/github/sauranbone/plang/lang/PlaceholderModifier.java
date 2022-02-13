package io.github.sauranbone.plang.lang;

/**
 * Class containing constants and utilities to modify and change a bitmask
 * using the modifiers that describe the flow of a placeholder within
 * processors.
 *
 * @author Vinzent Zeband
 * @version 23:46 CET, 12.02.2022
 * @since 1.0
 */
public final class PlaceholderModifier {

    /**
     * Constant that allows the placeholder to accept a null value as data
     * that then can be transformed.
     */
    public static final int NULLABLE = 2;

    /**
     * Constant that describes the placeholder to be a static value and thus
     * does not need to handle incoming or receiving data and transform it.
     */
    public static final int STATIC = 4;

    /**
     * Returns true if the given {@code modifiers} contain {@link #STATIC}.
     *
     * @param modifiers the bitmask to be checked
     * @return false if {@code modifiers} does not contain STATIC
     * @see #STATIC
     */
    public static boolean isStatic(int modifiers) {
        return (modifiers & STATIC) != 0;
    }


    /**
     * Returns true if the given {@code modifiers} contain {@link #NULLABLE}.
     *
     * @param modifiers the bitmask to be checked
     * @return false if {@code modifiers} does not contain NULLABLE
     * @see #NULLABLE
     */
    public static boolean isNullable(int modifiers) {
        return (modifiers & NULLABLE) != 0;
    }

}
