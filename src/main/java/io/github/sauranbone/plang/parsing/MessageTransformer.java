package io.github.sauranbone.plang.parsing;

import io.github.sauranbone.plang.map.DataBinder;
import io.github.sauranbone.plang.placeholder.Placeholder;
import io.github.sauranbone.plang.specific.Language;
import io.github.sauranbone.plang.specific.Lexicon;

/**
 * Interface acting like a message parser taking parsed tokens and using
 * semantic analysis to transform them into literal content.
 */
public interface MessageTransformer {

    /**
     * Transforms the given {@code tokens} using the given {@code data} as
     * the second data providing structure after the given {@code language}
     * and its processors.
     * <p>The {@code data} can be used to add additional not globally
     * accessible or dynamic accessible placeholders to a message and
     * transform them, or to overwrite placeholders that are globally
     * accessible in this language's lexicon.  It acts like an extra layer
     * of potential information that might only be necessary on this
     * particular message or instance.
     *
     * @param tokens   the target already parsed tokens of a message
     * @param language the target language that should be converted into
     * @param message  the target message that is transformed
     * @param data     the target binding information
     * @return the resulting and live transformed string
     * @throws NullPointerException if any argument is null
     * @implNote Depending on the underlying implementation, the actual
     * usage and syntax or meaning of tokens changes, thus no further
     * statements can be made here but must be documented in the underlying
     * implementations to let third party developers understand their
     * semantic better.
     */
    String transform(ParsedTokens tokens, Language language, String message, DataBinder data);

}
