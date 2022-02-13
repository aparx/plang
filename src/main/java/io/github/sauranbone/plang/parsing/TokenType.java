package io.github.sauranbone.plang.parsing;

import io.github.sauranbone.plang.lang.Placeholder;

/**
 * Enumeration containing constants describing the type of a token and what
 * use and goal they have for a processor.
 *
 * @author Vinzent Zeband
 * @version 23:32 CET, 12.02.2022
 * @since 1.0
 */
public enum TokenType {

    /**
     * The literal token type is the default and most common, that indicates
     * that the target token must not be processed and thus can easily be
     * added to the resulting stack without any further computation.
     */
    LITERAL,

    /**
     * The placeholder token type forces the token to be computed within a
     * processor, in order to be mutated or differentiated in the resulting
     * outcome.
     * <p>The placeholder is mainly like a variable, that can be dynamically
     * transformed or injected using different language methods.
     *
     * @see Placeholder
     */
    PLACEHOLDER

}