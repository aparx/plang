package io.github.sauranbone.plang.specific;

import io.github.sauranbone.plang.map.DataBinder;
import io.github.sauranbone.plang.parsing.MessageLexer;
import io.github.sauranbone.plang.parsing.ParsedTokens;
import io.github.sauranbone.plang.parsing.MessageParser;
import io.github.sauranbone.plang.placeholder.Placeholder;

import java.util.List;
import java.util.Objects;

/**
 * Message class representing a parsed message containing its content and
 * tokens, that were once parsed using the message's language processors.
 *
 * @author Vinzent Zeband
 * @version 23:31 CET, 12.02.2022
 * @see Language
 * @see MessageLexer
 * @see MessageParser
 * @since 1.0
 */
public class Message {

    final String content;

    private ParsedTokens tokens;

    private Language language;

    /**
     * Allocates a new message having given {@code content} and parses it
     * directly using the given {@code language} processors.
     *
     * @param content  the target content of this message
     * @param language the target language of this message
     * @see Language#parse(String)
     * @see Language#getLexer()
     * @see Language#getParser()
     * @see MessageLexer#tokenize(Language, String)
     * @see MessageParser#parse(Language, List)
     */
    public Message(String content, Language language) {
        this.content = content;
        this.language = language;
        parse();
    }

    /**
     * Transforms this message using the given {@code data} as the
     * primary data providing structure.
     * <p>Only bound string-, integer- and class-type keys are used off of
     * the provided {@code data} binding map.
     * <p>A literal placeholder is only transformed once.
     * <p>The following table explains the precedences of boundaries and
     * their object key types:
     * <table>
     *     <tr>
     *         <th>Type</th>
     *         <th>Precedence</th>
     *         <th>Description</th>
     *     </tr>
     *     <tr>
     *         <th>Integer</th>
     *         <th>3 - highest</th>
     *         <th>An integer is used as an index and thus has the
     *         most precedence as it is directly addressing an exact and
     *         certain placeholder in literal context.</th>
     *     </tr>
     *     <tr>
     *         <th>String</th>
     *         <th>2 - middle</th>
     *         <th>A string is mainly used to bind literal
     *         placeholders to a certain value and thus have second
     *         highest precedence.</th>
     *     </tr>
     *     <tr>
     *         <th>Class</th>
     *         <th>1 - lowest</th>
     *         <th>A class is mainly used whenever a literal
     *         placeholder in literal context is neither bound to its
     *         corresponding position (index) nor to its representing
     *         targeted literal placeholder name.  The class is used
     *         to only pass a certain object that should then be
     *         transformed using {@link Placeholder#transform(Object)}.
     *         </th>
     *     </tr>
     * </table>
     *
     * @param data the target binding information
     * @return the resulting and live transformed string
     * @throws NullPointerException if {@code data} is null
     */
    public String transform(DataBinder data) {
        Objects.requireNonNull(data);
        return "";
    }

    /**
     * Returns the raw content of this message.
     *
     * @return the raw and unformatted content of this message
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the parsed tokens of this message
     *
     * @return the parsed tokens
     */
    public ParsedTokens getTokens() {
        return tokens;
    }

    /**
     * Returns the language of this message.
     *
     * @return this bound language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Parses this message and validates the tokens to be not null.
     */
    protected final void parse() {
        tokens = language.parse(content);
        Objects.requireNonNull(tokens, "The tokens of a message cannot be null");
    }
}
