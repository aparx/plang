package io.github.sauranbone.plang.lang;

import io.github.sauranbone.plang.parsing.MessageLexer;
import io.github.sauranbone.plang.parsing.MessageParser;

/**
 * Language class containing utilities to represent a language with preset
 * placeholder lexicon, messages and processors for parsing and caching.
 *
 * @author Vinzent Zeband
 * @version 23:33 CET, 12.02.2022
 * @since 1.0
 */
public class Language {

    final String name, abbreviation;
    final MessageLexer lexer;
    final MessageParser parser;

    public Language(String name, String abbreviation,
                    MessageLexer lexer, MessageParser parser) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.lexer = lexer;
        this.parser = parser;
    }

}
