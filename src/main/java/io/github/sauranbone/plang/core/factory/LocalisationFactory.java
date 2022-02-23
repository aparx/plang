package io.github.sauranbone.plang.core.factory;

import io.github.sauranbone.plang.core.map.AbstractPlangMap;
import io.github.sauranbone.plang.core.specific.Language;
import io.github.sauranbone.plang.core.specific.Lexicon;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Default language factory class, containing utilities to create, cache
 * and access languages, their features and their attributes.
 *
 * @author Vinzent Zeband
 * @version 01:00 CET, 23.02.2022
 * @since 1.0
 */
public class LocalisationFactory extends AbstractPlangMap<String, Language> implements LanguageFactory {

    @Override
    public synchronized Language getOrBake(String name, String abbreviation) {
        Objects.requireNonNull(abbreviation, "Abbreviation");
        return getOrBake(name, (n) -> new Language(n, abbreviation));
    }

    @Override
    public Language getOrBake(String name, String abbreviation, Lexicon lexicon) {
        Objects.requireNonNull(abbreviation, "Abbreviation");
        Objects.requireNonNull(lexicon, "Lexicon");
        return getOrBake(name, (n) -> new Language(n, abbreviation, lexicon));
    }

    @Override
    public Language getOrBake(String name, Function<String, ? extends Language> creator) {
        String key = processName(name);
        Objects.requireNonNull(creator, "Creator");
        //Create language in case it is not yet set
        if (!isset(key, true)) {
            set(key, Objects.requireNonNull(creator.apply(name),
                    "created language cannot be null"));
        }
        return Objects.requireNonNull(get(key),
                "null returned as language");
    }

    @Override
    public Language getByAbbreviation(String abbreviation, Language def) {
        for (Language language : values()) {
            if (language.getAbbreviation().equals(abbreviation))
                return language;
        }
        return def;
    }

    @Override
    public Language getByName(String name, Language def) {
        for (Language language : values()) {
            if (language.getName().equalsIgnoreCase(name))
                return language;
        }
        return def;
    }

    @Override
    public boolean isBaked(String name) {
        return isset(name, false);
    }

    private boolean isset(String name, boolean processed) {
        return containsKey(processed ? name : processName(name));
    }

    protected Language createLanguage(String name, String abb) {
        return new Language(name, abb);
    }

    @Override
    protected final Map<String, Language> createMap(Map<String, Language> input) {
        return new HashMap<>(input);
    }

    @Override
    protected final Map<String, Language> createMap(int initialCapacity) {
        return new HashMap<>(initialCapacity);
    }

    private String processName(String name) {
        Objects.requireNonNull(name, "Name");
        return name.toUpperCase(Locale.ROOT);
    }
}
