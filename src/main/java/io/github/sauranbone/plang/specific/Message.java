package io.github.sauranbone.plang.specific;

import io.github.sauranbone.plang.parsing.MessageLexer;
import io.github.sauranbone.plang.parsing.ParsedTokens;
import io.github.sauranbone.plang.parsing.MessageParser;

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

    public Message(String content, Language language) {
        this.content = content;
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public ParsedTokens getTokens() {
        return tokens;
    }

    public Language getLanguage() {
        return language;
    }
}
