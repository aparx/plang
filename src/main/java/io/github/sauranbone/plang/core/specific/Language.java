package io.github.sauranbone.plang.core.specific;

import io.github.sauranbone.plang.core.error.DefaultErrorHandler;
import io.github.sauranbone.plang.core.error.LanguageErrorHandler;
import io.github.sauranbone.plang.core.parsing.*;
import io.github.sauranbone.plang.core.parsing.impl.NormalLexer;
import io.github.sauranbone.plang.core.parsing.impl.DefaultParser;
import io.github.sauranbone.plang.core.parsing.impl.DefaultTransformer;

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
    private LanguageErrorHandler errorHandler;

    MessageRegistry registry;

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
     * @param errorHandler the target language based error handler
     * @throws NullPointerException if any argument is null
     */
    public Language(String name, String abbreviation, Lexicon lexicon,
                    MessageLexer lexer, MessageParser parser,
                    MessageTransformer transformer,
                    LanguageErrorHandler errorHandler) {
        Objects.requireNonNull(name, "Name");
        Objects.requireNonNull(abbreviation, "Abbreviation");
        Objects.requireNonNull(lexicon, "Lexicon");
        Objects.requireNonNull(lexer, "Lexer");
        Objects.requireNonNull(parser, "Parser");
        Objects.requireNonNull(transformer, "Transformer");
        Objects.requireNonNull(errorHandler, "Error Handler");
        this.name = name;
        this.abbreviation = abbreviation;
        this.lexicon = lexicon;
        this.lexer = lexer;
        this.parser = parser;
        this.transformer = transformer;
        this.registry = new MessageRegistry(this);
        this.errorHandler = errorHandler;
    }

    /**
     * Allocates a new language having an entire {@code name}, an
     * {@code abbreviation} and default processors.
     *
     * @param name         the target full name of this language
     * @param abbreviation this language's abbreviation
     * @param lexicon      the lexicon that is used
     * @throws NullPointerException if any argument is null
     * @see #Language(String, String, Lexicon, MessageLexer, MessageParser,
     * MessageTransformer, LanguageErrorHandler)
     * @see NormalLexer#DEFAULT_LEXER
     * @see DefaultParser#SINGLETON
     * @see DefaultTransformer#SINGLETON
     * @see DefaultErrorHandler#SINGLETON
     * @see MessageRegistry
     */
    public Language(String name, String abbreviation, Lexicon lexicon) {
        this(name, abbreviation, lexicon, NormalLexer.DEFAULT_LEXER,
                DefaultParser.SINGLETON, DefaultTransformer.SINGLETON,
                DefaultErrorHandler.SINGLETON);
    }

    /**
     * Allocates a new language having an entire {@code name}, an
     * {@code abbreviation} and default processors and lexicon.
     *
     * @param name         the target full name of this language
     * @param abbreviation this language's abbreviation
     * @throws NullPointerException if any argument is null
     * @see #Language(String, String, Lexicon)
     * @see #Language(String, String, Lexicon, MessageLexer, MessageParser,
     * MessageTransformer, LanguageErrorHandler)
     * @see Lexicon
     * @see NormalLexer#DEFAULT_LEXER
     * @see DefaultParser#SINGLETON
     * @see DefaultTransformer#SINGLETON
     * @see DefaultErrorHandler#SINGLETON
     */
    public Language(String name, String abbreviation) {
        this(name, abbreviation, new Lexicon());
    }

    /**
     * Returns the string of identifier of {@code language}.
     * <p>If {@code language} is null, null is returned.
     * <p>If the resolving {@link #getIdentifier()} is null, a string
     * with content "null" is returned.
     *
     * @param language the target language to resolve
     */
    public static String getIdentifier(Language language) {
        if (language == null) return null;
        return Objects.toString(language.getIdentifier());
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
     * Returns the identifier that is used in hashing or equal comparison
     * to uniquely identify a given language.
     * <p>The identifier is by default a combination or mixture of the
     * language's properties {@code name} and {@code abbreviation}.
     * <p>The default identifier is equal to the following scheme:
     * <pre><code>
     *     String id = name + '/' + abbreviation;
     * </code></pre>
     * <p>The identifier is used to uniquely identify a language and is
     * also used to specify whenever a language is equal to another
     * language, even if its registered content is not equal.
     *
     * @return the identifier of this language, {@code not null}
     * @apiNote It is by design, that this method is overridable, so third
     * party developers can change the way the identifier is meant.
     */
    public String getIdentifier() {
        return name + '/' + abbreviation;
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

    /**
     * Returns the language based error handler.
     *
     * @return this error handler, {@code not null}
     */
    public LanguageErrorHandler getErrorHandler() {
        return errorHandler;
    }

    /**
     * Updates the error handler for this language to the given
     * {@code errorHandler}.
     *
     * @param errorHandler the new error handler, {@code not null}
     * @throws NullPointerException if {@code errorHandler} is null
     */
    public final synchronized void setErrorHandler(LanguageErrorHandler errorHandler) {
        Objects.requireNonNull(errorHandler);
        this.errorHandler = errorHandler;
    }

    /**
     * Returns the messaging registry, containing and keeping all the
     * messages that are accessible in the language's scope.
     *
     * @return the target registry of this language, {@code not null}
     * @throws NullPointerException if this registry is null
     */
    public synchronized final MessageRegistry getRegister() {
        return Objects.requireNonNull(registry);
    }

    /**
     * Updates the registry for this language scope.
     *
     * @param registry the new registry, {@code not null}
     * @throws NullPointerException     if {@code registry} is null
     * @throws IllegalArgumentException if the {@code registry}'s language
     *                                  is not equal to this
     * @apiNote Due to the {@code registry} having to accept a language it
     * adds another level of unnecessary complexity to the language and
     * might be removed in future versions far away.  Thus, it is yet not
     * deprecated but might be in the future.
     */
    public synchronized void setContent(MessageRegistry registry) {
        Objects.requireNonNull(registry, "Registry");
        registry.checkLangEqual(this);
        this.registry = registry;
    }

    /**
     * Returns true if this language identifier is equal to the given
     * {@code language}'s identifier.
     * <p>This method is used in {@link #equals(Object)} by default.
     *
     * @param language the language to be compared to
     * @return false if {@code language} does not have the same identifier
     * or is null compared to this language
     */
    public boolean isEqual(Language language) {
        if (language == null) return false;
        return getIdentifier().equals(language.getIdentifier());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return isEqual((Language) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }
}
