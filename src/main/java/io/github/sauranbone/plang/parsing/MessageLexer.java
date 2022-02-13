package io.github.sauranbone.plang.parsing;

import io.github.sauranbone.plang.specific.Language;
import io.github.sauranbone.plang.placeholder.Placeholder;

import java.util.List;

/**
 * A message lexing interface containing utilities to tokenize a certain
 * message or content to be parsed afterwards.
 * <p>Tokenization is the process of demarcating and possibly classifying
 * sections of a string of input characters. The resulting tokens are then
 * passed onto another {@linkplain MessageParser parser}, which must be
 * done outside of this lexer itself.
 *
 * @author Vinzent Zeband
 * @version 23:31 CET, 12.02.2022
 * @see MessageParser
 * @see Language
 * @see <a href="https://en.wikipedia.org/wiki/Lexical_analysis#Tokenization">
 * https://en.wikipedia.org/wiki/Lexical_analysis#Tokenization</a>
 * @since 1.0
 */
@FunctionalInterface
public interface MessageLexer {

    /**
     * Tokenizes the given {@code content} using the in the {@code
     * language} contained processors and returns the tokens that have been
     * found.
     *
     * @param language the target language containing the necessary
     *                 processors
     * @param content  the target content that is tokenized
     * @return the tokenized tokens of the {@code content}
     * @see Language
     * @see MessageToken
     * @see MessageTokenType
     * @see Placeholder
     */
    List<MessageToken> tokenize(Language language, String content);

}
