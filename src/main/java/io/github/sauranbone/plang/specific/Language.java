package io.github.sauranbone.plang.specific;

import com.sun.istack.internal.localization.Localizer;
import io.github.sauranbone.plang.parsing.*;

import java.io.Serializable;
import java.util.List;
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

    private final String name, abbreviation;
    private final Lexicon lexicon;
    private final MessageLexer lexer;
    private final MessageParser parser;
    private final MessageTransformer transformer;

    /**
     * Allocates a new language having an entire {@code name}, an
     * {@code abbreviation} and further more constant attributes and
     * processors.
     *
     * @param name         the target full name of this language
     * @param abbreviation this language's abbreviation
     * @param lexicon      the lexicon that is used
     * @param lexer        the target lexer that is used
     * @param parser       the target parser that is used
     * @param transformer  the target message transforming utility
     * @throws NullPointerException if any argument is null
     */
    public Language(String name, String abbreviation, Lexicon lexicon,
                    MessageLexer lexer, MessageParser parser,
                    MessageTransformer transformer) {
        Objects.requireNonNull(name, "Name");
        Objects.requireNonNull(abbreviation, "Abbreviation");
        Objects.requireNonNull(lexicon, "Lexicon");
        Objects.requireNonNull(lexer, "Lexer");
        Objects.requireNonNull(parser, "Parser");
        Objects.requireNonNull(transformer, "Transformer");
        this.name = name;
        this.abbreviation = abbreviation;
        this.lexicon = lexicon;
        this.lexer = lexer;
        this.parser = parser;
        this.transformer = transformer;
    }

    /**
     * Tokenizes the given {@code content} and passes those tokens two this
     * corresponding parser and returns the resulting and validated token
     * list.
     *
     * @param content the target content to be parsed
     * @return the parsed and validated tokens of {@code content}
     * @see #getLexer()
     * @see #getParser()
     * @see MessageLexer#tokenize(Language, String)
     * @see MessageParser#parse(Language, List)
     */
    public ParsedTokens parse(String content) {
        MessageLexer lexer = getLexer();
        MessageParser parser = getParser();
        Objects.requireNonNull(lexer, "Lexer");
        Objects.requireNonNull(parser, "Parser");
        return parser.parse(this, lexer.tokenize(this, content));
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
     * Returns the primary lexicon that is used in this language.
     *
     * @return this language's target placeholder lexicon
     */
    public Lexicon getLexicon() {
        return lexicon;
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

    /**
     * Returns the transformer constant this language uses.
     *
     * @return the transformer, {@code not null}
     */
    public MessageTransformer getTransformer() {
        return transformer;
    }
}
