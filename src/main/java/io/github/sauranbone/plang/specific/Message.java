package io.github.sauranbone.plang.specific;

import io.github.sauranbone.plang.PlangUtils;
import io.github.sauranbone.plang.map.DataBinder;
import io.github.sauranbone.plang.parsing.MessageLexer;
import io.github.sauranbone.plang.parsing.MessageToken;
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
     * second data providing structure after this language's lexicon.
     * <p>The {@code data} can be used to add additional not globally
     * accessible or dynamic accessible placeholders to a message and
     * transform them, or to overwrite placeholders that are globally
     * accessible in this language's lexicon.  It acts like an extra
     * layer of potential information that might only be necessary on
     * this particular message or instance.
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
    @SuppressWarnings("unchecked")
    public String transform(DataBinder data) {
        Objects.requireNonNull(data);
        if (tokens.isEmpty()) return content;

        final StringBuilder builder = new StringBuilder();
        final Lexicon lexicon = language.getLexicon();

        //Iterate through every token and check the bindings
        for (int n = tokens.size(), i = 0, p = 0; i < n; i++) {
            MessageToken token = tokens.get(i);
            if (token.isPlaceholder()) {
                //Process the token and its content compared to this attribs
                final String val = token.getValue();
                final int idx = p++;
                if (data.isBound(idx)) {
                    builder.append(data.get(idx));
                    continue;
                } else if (data.isBound(val)) {
                    builder.append(data.get(val));
                    continue;
                } else if (lexicon.has(val)) {
                    //Access the placeholder directly and ask for its type
                    Placeholder<Object> ph = (Placeholder<Object>) lexicon.get(val);
                    if (ph == null) continue;   //Throw warning
                    Class<?> type = PlangUtils.getTopClass(ph.getAcceptingType());
                    if (ph.isTransformative() && data.isBound(type)) {
                        //TODO also add class boundary if index or string is
                        // set with a target value that is not null
                        builder.append(ph.transform(data.get(type)));
                        continue;
                    } else if (ph.isStatic() && ph.isNullable()) {
                        //Get static access or nullable access
                        builder.append(ph.transform(null));
                        continue;
                    }
                }
                //TODO Warn that the placeholder is not bound
//                System.out.println("Placeholder " + val + " not" +
//                        " bound");
            }
            //Just append the token as literal text
            builder.append(token.getRaw());
        }
        return builder.toString();
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
