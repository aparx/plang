package io.github.sauranbone.plang.core.specific;

import io.github.sauranbone.plang.core.map.DataBindMap;
import io.github.sauranbone.plang.core.map.DataBinder;
import io.github.sauranbone.plang.core.parsing.*;

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

    final ParsedTokens tokens;

    final Language language;

    /**
     * Allocates a new message having given {@code content} and parses it
     * directly using the given {@code language} processors.
     *
     * @param content  the target content of this message
     * @param language the target language of this message
     * @throws NullPointerException if {@code language} is null
     * @see Language#parse(String)
     * @see Language#getLexer()
     * @see Language#getParser()
     * @see MessageLexer#tokenize(Language, String)
     * @see MessageParser#parse(Language, List)
     */
    public Message(String content, Language language) {
        Objects.requireNonNull(language, "Language");
        this.content = content;
        this.language = language;
        this.tokens = parse();
        Objects.requireNonNull(tokens, "The tokens of a message cannot be null");
    }

    /**
     * Transforms this message using no given data binding map, thus only
     * relying on the concurrent globally accessible placeholders contained
     * in this language's lexicon.
     * <p>This language's {@link MessageTransformer transformer} is used,
     * and thus its {@code transform} is the method that is automatically
     * invoked given this attributes.
     * <p>This method is equivalent to the following code:
     * <pre><code>
     *      String str = transform(new DataBindMap());
     * </code></pre>
     *
     * @see #transform(DataBinder)
     */
    public synchronized String transform() {
        return transform(new DataBindMap());
    }

    /**
     * Transforms this message using the given {@code data} as the second
     * data providing structure after this language's lexicon by using this
     * language's transformer.
     * <p>The {@code data} can be used to add additional not globally
     * accessible or dynamic accessible placeholders to a message and
     * transform them, or to overwrite placeholders that are globally
     * accessible in this language's lexicon.  It acts like an extra layer
     * of potential information that might only be necessary on this
     * particular message or instance.
     * <p>This language's {@link MessageTransformer transformer} is used,
     * and thus its {@code transform} is the method that is automatically
     * invoked given this attributes.
     *
     * @param data the target binding information
     * @return the resulting and live transformed string
     * @throws NullPointerException if {@code data} or this language's
     *                              transformer is null
     * @see DataBindMap
     * @see Language#getTransformer()
     * @see MessageTransformer#transform(ParsedTokens, Language, String,
     * DataBinder)
     */
    public synchronized String transform(DataBinder data) {
        Objects.requireNonNull(data);
        //Automatically bind all available types
        bindKnowledge(data);
        MessageTransformer transformer = language.getTransformer();
        Objects.requireNonNull(transformer);    //Nullcheck for safety
        return transformer.transform(tokens, language, content, data);
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
    protected ParsedTokens parse() {
        return language.parse(content);
    }

    /**
     * Binds default types that are known at type of execution about this
     * message or language to the given {@code data}, if not bound
     * already.
     *
     * @param data the target data to bind to
     * @throws NullPointerException if {@code data} is null
     */
    protected void bindKnowledge(DataBinder data) {
        Objects.requireNonNull(data);
        if (!data.isTypeBound(language)) data.bindType(language);
        if (!data.isTypeBound(this)) data.bindType(this);
    }
}
