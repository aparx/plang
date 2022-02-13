package io.github.sauranbone.plang.specific;

import com.sun.istack.internal.localization.Localizer;
import io.github.sauranbone.plang.parsing.MessageLexer;
import io.github.sauranbone.plang.parsing.MessageParser;

import java.io.Serializable;
import java.util.Objects;

/**
 * Language class containing utilities to represent a language with preset
 * placeholder lexicon, messages and processors for parsing and caching.
 *
 * @author Vinzent Zeband
 * @version 23:33 CET, 12.02.2022
 * @since 1.0
 */
public class Language implements Serializable {

    final String name, abbreviation;
    final MessageLexer lexer;
    final MessageParser parser;

    /**
     * Allocates a new language having an entire {@code name}, an {@code
     * abbreviation} and further more constant attributes and processors.
     *
     * @param name         the target full name of this language
     * @param abbreviation this language's abbreviation
     * @param lexer        the target lexer that is used
     * @param parser       the target parser that is used
     * @throws NullPointerException if any argument is null
     */
    public Language(String name, String abbreviation,
                    MessageLexer lexer, MessageParser parser) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(abbreviation);
        Objects.requireNonNull(lexer);
        Objects.requireNonNull(parser);
        this.name = name;
        this.abbreviation = abbreviation;
        this.lexer = lexer;
        this.parser = parser;
    }

    /**
     * Returns the full name of this language.
     *
     * @return this language's full name, {@code not null}
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the abbreviation of this language
     *
     * @return this language's abbreviation, {@code not null}
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Returns the lexer constant this language uses.
     *
     * @return this lexer, {@code not null}
     */
    public MessageLexer getLexer() {
        return lexer;
    }

    /**
     * Returns the parser constant this language uses.
     *
     * @return this parser, {@code not null}
     */
    public MessageParser getParser() {
        return parser;
    }

}
