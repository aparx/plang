package io.github.sauranbone.plang.parsing;

import io.github.sauranbone.plang.specific.Language;

import java.util.List;

/**
 * A message parser interface that accepts already tokenized input in form
 * of tokens, validates and parses their syntax and order.
 *
 * @author Vinzent Zeband
 * @version 23:31 CET, 12.02.2022
 * @see <a href="https://en.wikipedia.org/wiki/Lexical_analysis">
 * https://en.wikipedia.org/wiki/Lexical_analysis</a>
 * @see <a href="https://en.wikipedia.org/wiki/Parsing#Parser">
 * https://en.wikipedia.org/wiki/Parsing#Parser</a>
 * @since 1.0
 */
@FunctionalInterface
public interface MessageParser {

    /**
     * Parses the given {@code tokens} using the processors contained in
     * the given {@code language} and returns the parsed and validated
     * tokens.
     *
     * @param language the target language containing all necessary
     *                 processors
     * @param tokens   the target tokens to be parsed
     * @return the parsed and validated tokens
     */
    ParsedTokens parse(Language language, List<MessageToken> tokens);

}
