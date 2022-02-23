package io.github.sauranbone.plang.core.factory;

import io.github.sauranbone.plang.core.exception.LanguageNotFoundException;
import io.github.sauranbone.plang.core.specific.Language;
import io.github.sauranbone.plang.core.specific.Lexicon;

import java.util.function.Function;

/**
 * @author Vinzent Zeband
 * @version 00:57 CET, 23.02.2022
 * @since 1.0
 */
public interface LanguageFactory {

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
     * @see #getOrBake(String, Function)
     */
    Language getOrBake(String name, String abbreviation);

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
     * @see #getOrBake(String, Function)
     */
    Language getOrBake(String name, String abbreviation, Lexicon lexicon);

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
     * @see #getOrBake(String, String)
     * @see Language#Language(String, String)
     */
    Language getOrBake(String name, Function<String, ? extends Language> creator);

    /**
     * Returns the first best language having given {@code abbreviation}.
     *
     * @param abbreviation the target abbreviation
     * @return the first concurrent registered language having given
     * {@code abbreviation} as an equal abbreviation
     * @throws LanguageNotFoundException if a language having equal
     *                                   {@code abbreviation} could not be
     *                                   located concurrently
     * @see #getOrBake(String, Function)
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
     * @see #getOrBake(String, Function)
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
     * @see #getOrBake(String, Function)
     */
    default Language getByName(String name) {
        Language language = getByName(name, null);
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
     * @see #getOrBake(String, Function)
     */
    Language getByName(String name, Language def);

    /**
     * Returns true if given {@code name} is cached as a language or not.
     *
     * @param name the target name to check for cache status
     * @return false if {@code name} has not been initialized yet
     */
    boolean isBaked(String name);

}
