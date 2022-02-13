package io.github.sauranbone.plang.specific;

import io.github.sauranbone.plang.parsing.MessageLexer;
import io.github.sauranbone.plang.parsing.ParsedTokens;
import io.github.sauranbone.plang.parsing.MessageParser;

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
public final class Message {

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
