package io.github.sauranbone.plang.core;

/**
 * Interface used and applied to implement the ability to update modifiers
 * and provide a bitmask, containing information about how something should
 * be processed considering the flow of information.
 *
 * @author Vinzent Zeband
 * @version 21:37 CET, 12.02.2022
 * @since 1.0
 */
public interface PlangBitmask {

    /**
     * Returns the settings bitmask containing information and variables
     * gathered using mathematical operations, describing how the target
     * should be processed.
     *
     * @return the settings bitmask
     */
    int getModifiers();

    /**
     * Updates this settings bitmask to the given {@code bitmask}.
     *
     * @param bitmask the new settings value
     * @see #getModifiers()
     * @see #setModifier(int, boolean)
     * @see #enable(int)
     * @see #disable(int)
     */
    void setModifiers(int bitmask);

    /**
     * Appends the target {@code modifier} to this settings bitmask if
     * {@code status} is true, otherwise removes it.
     *
     * @param modifier the modifier to be updated
     * @param status   the target status of the {@code modifier}, true is
     *                 equivalent to enabled while false is disabled
     * @see #getModifiers()
     * @see #setModifiers(int)
     */
    default void setModifier(int modifier, boolean status) {
        int bitmask = getModifiers();
        if (status) bitmask |= modifier;
        else bitmask &= ~modifier;
        setModifiers(bitmask);
    }

    /**
     * Enables the target {@code modifier} in this settings bitmask.
     *
     * @param modifier the target modifier
     * @see #setModifier(int, boolean)
     */
    default void enable(int modifier) {
        setModifier(modifier, true);
    }

    /**
     * Disables the target {@code modifier} in this settings bitmask.
     *
     * @param modifier the target modifier
     * @see #setModifier(int, boolean)
     * @see #enable(int)
     */
    default void disable(int modifier) {
        setModifier(modifier, false);
    }

    /**
     * Returns true if the target {@code modifier} has been enabled in this
     * settings bitmask.
     *
     * @param modifier the modifier to be tested
     * @return false if the {@code modifier} is not enabled
     */
    default boolean hasModifier(int modifier) {
        return (getModifiers() & modifier) != 0;
    }

}
