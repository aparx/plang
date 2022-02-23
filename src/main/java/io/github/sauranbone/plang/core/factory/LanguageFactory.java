package io.github.sauranbone.plang.core.factory;

import com.sun.scenario.effect.impl.prism.ps.PPSBlend_REDPeer;
import io.github.sauranbone.plang.core.exception.LanguageNotFoundException;
import io.github.sauranbone.plang.core.parsing.impl.DefaultParser;
import io.github.sauranbone.plang.core.parsing.impl.DefaultTransformer;
import io.github.sauranbone.plang.core.parsing.impl.NormalLexer;
import io.github.sauranbone.plang.core.specific.Language;
import io.github.sauranbone.plang.core.specific.Lexicon;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Vinzent Zeband
 * @version 00:57 CET, 23.02.2022
 * @since 1.0
 */
public interface LanguageFactory extends Iterable<Language> {

    /**
     * Saves the given {@code language} instance using into this factory.
     *
     * @param language the target language to be cached
     * @throws NullPointerException if {@code language} or its name is
     *                              null
     * @see #getOrCreate(String, Function)
     * @see #getOrCreate(String, String)
     * @see #getOrCreate(String, String, Lexicon)
     * @see #getById(String, Language)
     * @see #getByAbbreviation(String, Language)
     */
    void set(Language language);

    /**
     * Removes a previously cached language having given {@code name}.
     *
     * @param name the target language to be removed from the cache
     * @return true if a language with equal {@code name} was removed
     * @implNote This method is faster than {@link #unsetById(String)} or
     * {@link #unsetByAbbreviation(String)}, as it does not need to iterate
     * through the concurrent list of cached languages, as the name is
     * already processed and used as a key within the cache, which directly
     * identifies the target language. Also, this method is safer than
     * iterating it, as the name process algorithm is defined in the
     * underlying implementation and may not be accessible to the outside
     * average use-case. Altogether, the time complexity of this method is
     * equal to O(1), which is faster than O(n) in the other alternatives.
     */
    boolean unsetByName(String name);

    /**
     * Removes a previously cached language having given
     * {@code identifier}.
     *
     * @param identifier the target language to be removed from the cache
     * @return true if a language with equal {@code identifier} was removed
     */
    boolean unsetById(String identifier);

    /**
     * Removes a previously cached language having given
     * {@code abbreviation}.
     *
     * @param abbreviation the target language to be removed from the
     *                     cache
     * @return true if a language with equal {@code abbreviation} was
     * removed
     */
    boolean unsetByAbbreviation(String abbreviation);

    /**
     * Allocates a new language with default processors and given
     * parameters as attributes if the name is not already cached as
     * language.
     * <p>This method checks whether there is a language cached
     * concurrently that may match using given input and returns if there
     * is any cached instance, otherwise it caches a newly allocated
     * instance and returns it.
     *
     * @param name         the target name of the language
     * @param abbreviation the target abbreviation (short-handle)
     * @throws NullPointerException if either {@code name} or
     *                              {@code creator} is null
     * @see Language#Language(String, String)
     * @see #getOrCreate(String, Function)
     * @see DefaultTransformer
     * @see DefaultParser
     * @see NormalLexer#DEFAULT_LEXER
     */
    Language getOrCreate(String name, String abbreviation);

    /**
     * Allocates a new language with default processors and given
     * parameters as attributes if the name is not already cached as
     * language.
     * <p>This method checks whether there is a language cached
     * concurrently that may match using given input and returns if there
     * is any cached instance, otherwise it caches a newly allocated
     * instance and returns it.
     *
     * @param name         the target name of the language
     * @param abbreviation the target abbreviation (short-handle)
     * @param lexicon      the target lexicon of the language
     * @throws NullPointerException if either {@code name} or
     *                              {@code creator} is null
     * @see Language#Language(String, String)
     * @see #getOrCreate(String, Function)
     * @see DefaultTransformer
     * @see DefaultParser
     * @see NormalLexer#DEFAULT_LEXER
     */
    Language getOrCreate(String name, String abbreviation, Lexicon lexicon);

    /**
     * Returns the by {@code name} cached language otherwise caches the
     * returning language from an applying call of {@code creator}.
     *
     * @param name    the target name of the language
     * @param creator the function that is called with {@code name} in case
     *                {@code name} is not baked as language yet
     * @throws NullPointerException if either {@code name}, {@code creator}
     *                              or the returning language of
     *                              {@code creator} is null.
     * @see #getOrCreate(String, String)
     * @see Language#Language(String, String)
     */
    Language getOrCreate(String name, Function<String, ? extends Language> creator);

    /**
     * Returns the first best language having given {@code abbreviation}.
     *
     * @param abbreviation the target abbreviation
     * @return the first concurrent registered language having given
     * {@code abbreviation} as an equal abbreviation
     * @throws LanguageNotFoundException if a language having equal
     *                                   {@code abbreviation} could not be
     *                                   located concurrently
     * @see #getByAbbreviation(String, Language)
     * @see #getOrCreate(String, Function)
     */
    default Language getByAbbreviation(String abbreviation) {
        Language language = getByAbbreviation(abbreviation, null);
        if (language != null) return language;
        throw LanguageNotFoundException.byAbbreviation(abbreviation);
    }

    /**
     * Returns the first best language having given {@code abbreviation}.
     *
     * @param abbreviation the target abbreviation
     * @param def          the default language in case no
     *                     {@code abbreviation} matching language can be
     *                     found concurrently
     * @return the first concurrent registered language having given
     * {@code abbreviation} as an equal abbreviation
     * @see #getOrCreate(String, Function)
     */
    Language getByAbbreviation(String abbreviation, Language def);

    /**
     * Returns the first best language having given {@code name}.
     *
     * @param name the target name
     * @return the first concurrent registered language having given
     * {@code name} as an equal name
     * @throws LanguageNotFoundException if a language having equal
     *                                   {@code name} could not be located
     *                                   concurrently
     * @see #getByName(String, Language)
     * @see #getOrCreate(String, Function)
     */
    default Language getByName(String name) {
        Language language = getById(name, null);
        if (language != null) return language;
        throw LanguageNotFoundException.byName(name);
    }

    /**
     * Returns the first best language having given {@code name}.
     *
     * @param name the target name
     * @param def  the default language in case no {@code name} matching
     *             language can be found concurrently
     * @return the first concurrent registered language having given
     * {@code name} as an equal name
     * @apiNote Depending on the underlying implementation, {@code name}
     * may be case-insensitive.
     * @see #getOrCreate(String, Function)
     */
    Language getByName(String name, Language def);

    /**
     * Returns the first best language having given {@code identifier}.
     *
     * @param identifier the target identifier
     * @return the first concurrent registered language having given
     * {@code identifier} as an equal identifier
     * @throws LanguageNotFoundException if a language having equal
     *                                   {@code identifier} could not be
     *                                   located concurrently
     * @see #getById(String, Language)
     * @see #getOrCreate(String, Function)
     */
    default Language getById(String identifier) {
        Language language = getById(identifier, null);
        if (language != null) return language;
        throw LanguageNotFoundException.byId(identifier);
    }

    /**
     * Returns the first best language having given {@code identifier}.
     *
     * @param identifier the target identifier
     * @param def        the default language in case no {@code identifier}
     *                   matching language can be found concurrently
     * @return the first concurrent registered language having given
     * {@code identifier} as an equal identifier
     * @apiNote Depending on the underlying implementation,
     * {@code identifier} may be case-insensitive.
     * @see #getOrCreate(String, Function)
     */
    Language getById(String identifier, Language def);

    /**
     * Returns a collection of all languages concurrently cached, that have
     * an equal given {@code identifier}.
     * <p>The {@code identifier} is compared using the iterated
     * language's {@link Language#isEqualIdentifier(String)}.
     *
     * @param identifier the target identifier to be found
     * @return all concurrently cached language having an equal
     * {@code identifier}
     */
    Collection<Language> findById(String identifier);

    /**
     * Returns a collection of all languages concurrently cached, that have
     * an equal given {@code abbreviation}.
     * <p>The {@code abbreviation} is compared using the iterated
     * language's {@link Language#isEqualAbbreviation(String)}.
     *
     * @param abbreviation the target abbreviation to be found
     * @return all concurrently cached language having an equal
     * {@code abbreviation}
     */
    Collection<Language> findByAbbreviation(String abbreviation);

    /**
     * Returns a collection of all languages concurrently cached, that have
     * an equal given {@code name}.
     * <p>The {@code name} is compared using the iterated
     * language's {@link Language#isEqualAbbreviation(String)}.
     *
     * @param name the target name to be found
     * @return all concurrently cached language having an equal
     * {@code name}
     * @implNote Depending on the underlying implementation, the returning
     * list may always have only none or one element contained, as the
     * {@code name} is normally processed as a key and a language assigned
     * to that name, meaning that there can only be one language with equal
     * name cached at the same time.
     */
    Collection<Language> findByName(String name);

    /**
     * Returns a collection of all languages that have been tested
     * {@code true} using given {@code predicate} on each concurrently
     * cached language.
     * <p>Every concurrently saved language is iterated and added to the
     * resulting collection whenever {@code predicate} returns true on that
     * language.
     *
     * @param predicate the target predicate to test each language
     * @return all the concurrently cached languages testing {@code true}
     * using given {@code predicate}
     */
    Collection<Language> find(Predicate<? super Language> predicate);

    /**
     * Returns true if given {@code name} is cached as a language or not.
     *
     * @param name the target name to check for cache status
     * @return false if {@code name} has not been initialized yet
     */
    boolean isCached(String name);

    /**
     * Returns true if no languages have been cached concurrently.
     *
     * @return false if any language has been cached or set
     */
    boolean isEmpty();

    /**
     * Returns the amount of languages that have been cached concurrently.
     *
     * @return the size of this factory's cache
     */
    int size();

    /**
     * Clears the cache of this factory and thus every language set.
     *
     * @see #set(Language)
     * @see #unsetByName(String)
     * @see #unsetByAbbreviation(String)
     * @see #unsetById(String)
     * @see #size()
     */
    void clear();

    /**
     * Returns an immutable list of languages that have been cached
     * concurrently.
     *
     * @return all concurrent cached languages
     */
    Collection<Language> cache();


}
