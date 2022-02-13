package io.github.sauranbone.plang.lang;

import io.github.sauranbone.plang.parsing.MessageLexer;
import io.github.sauranbone.plang.parsing.MessageParseResult;
import io.github.sauranbone.plang.parsing.MessageParser;

/**
 * Message class representing a parsed message containing its content and tokens,
 * that were once parsed using the message's language processors.
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

    private MessageParseResult tokens;

    private Language language;

    public Message(String content, Language language) {
        this.content = content;
        this.language = language;
    }

}
