package io.github.sauranbone.plang.parsing;

import io.github.sauranbone.plang.placeholder.Placeholder;

/**
 * Enumeration containing constants describing the type of a token and what
 * use and goal they have for a processor.
 *
 * @author Vinzent Zeband
 * @version 23:32 CET, 12.02.2022
 * @since 1.0
 */
public enum MessageTokenType {

    /**
     * The literal token type is the default and most common, that
     * indicates that the target token must not be processed and thus can
     * easily be added to the resulting stack without any further
     * computation.
     */
    LITERAL,

    /**
     * The placeholder token type forces the token to be computed within a
     * processor, in order to be mutated or differentiated in the resulting
     * outcome.
     * <p>The placeholder is mainly like a variable, that can be
     * dynamically transformed or injected using different language
     * methods.
     *
     * @see Placeholder
     */
    PLACEHOLDER;

    /**
     * Returns true if this type is caused in tokenization by checking the
     * syntax in the current literal context.
     *
     * @return false if this type is literal and not a placeholder
     */
    public final boolean hasSyntax() {
        return this == PLACEHOLDER;
    }

}