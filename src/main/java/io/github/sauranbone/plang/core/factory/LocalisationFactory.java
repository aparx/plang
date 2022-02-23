package io.github.sauranbone.plang.core.factory;

import io.github.sauranbone.plang.core.map.HashPlangMap;
import io.github.sauranbone.plang.core.specific.Language;
import io.github.sauranbone.plang.core.specific.Lexicon;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Default language factory class, containing utilities to create, cache
 * and access languages, their features and their attributes.
 *
 * @author Vinzent Zeband
 * @version 01:00 CET, 23.02.2022
 * @since 1.0
 */
public class LocalisationFactory extends HashPlangMap<String, Language> implements LanguageFactory {

    /**
     * Allocates a new default localisation factory, that creates, caches
     * and lists languages and their attributes.
     */
    public LocalisationFactory() {
        super(0);
    }

    @Override
    public void set(Language language) {
        Objects.requireNonNull(language, "Language");
        set(processKey(language.getName()), language);
    }

    @Override
    public boolean unsetByName(String name) {
        return remove(processKey(name)) != null;
    }

    @Override
    public synchronized boolean unsetById(String identifier) {
        boolean unset = false;
        for (Language language : cache()) {
            if (language != null && language.isEqualIdentifier(identifier)) {
                unset |= unsetByName(language.getName());
            }
        }
        return unset;
    }

    @Override
    public synchronized boolean unsetByAbbreviation(String abbreviation) {
        boolean unset = false;
        for (Language language : cache()) {
            if (language != null && language.isEqualAbbreviation(abbreviation)) {
                unset |= unsetByAbbreviation(language.getName());
            }
        }
        return unset;
    }

    @Override
    public synchronized Language getOrCreate(String name, String abbreviation) {
        Objects.requireNonNull(abbreviation, "Abbreviation");
        return getOrCreate(name, (n) -> new Language(n, abbreviation));
    }

    @Override
    public Language getOrCreate(String name, String abbreviation, Lexicon lexicon) {
        Objects.requireNonNull(abbreviation, "Abbreviation");
        Objects.requireNonNull(lexicon, "Lexicon");
        return getOrCreate(name, (n) -> new Language(n, abbreviation, lexicon));
    }

    @Override
    public Language getOrCreate(String name, Function<String, ? extends Language> creator) {
        String key = processKey(name);
        Objects.requireNonNull(creator, "Creator");
        //Create language in case it is not yet set
        if (!isset(key, true)) {
            set(key, Objects.requireNonNull(creator.apply(name), "created language cannot be null"));
        }
        return Objects.requireNonNull(get(key), "null returned as language");
    }

    @Override
    public Language getByAbbreviation(String abbreviation, Language def) {
        if (abbreviation == null) return def;
        for (Language language : values()) {
            if (language.isEqualAbbreviation(abbreviation))
                return language;
        }
        return def;
    }

    @Override
    public Language getByName(String name, Language def) {
        if (name == null) return def;
        return get(processKey(name));
    }

    @Override
    public Language getById(String identifier, Language def) {
        if (identifier == null) return def;
        for (Language language : values()) {
            if (language.isEqualIdentifier(identifier)) return language;
        }
        return def;
    }

    @Override
    public Collection<Language> findById(String identifier) {
        if (identifier == null) return new ArrayList<>();
        return find(lang -> lang.isEqualIdentifier(identifier));
    }

    @Override
    public Collection<Language> findByAbbreviation(String abbreviation) {
        if (abbreviation == null) return new ArrayList<>();
        return find(lang -> lang.isEqualAbbreviation(abbreviation));
    }

    @Override
    public Collection<Language> findByName(String name) {
        if (name == null) return new ArrayList<>();
        return find(lang -> lang.isEqualName(name));
    }

    @Override
    public synchronized Collection<Language> find(Predicate<? super Language> predicate) {
        ArrayList<Language> languages = new ArrayList<>(size());
        for (Language lang : cache()) {
            if (lang != null && predicate.test(lang)) languages.add(lang);
        }
        languages.trimToSize();
        return languages;
    }

    @Override
    public boolean isCached(String name) {
        return isset(name, false);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public Collection<Language> cache() {
        return values();
    }

    protected Language createLanguage(String name, String abb) {
        return new Language(name, abb);
    }

    private boolean isset(String name, boolean processed) {
        return containsKey(processed ? name : processKey(name));
    }

    private String processKey(String name) {
        Objects.requireNonNull(name, "Name");
        return name.toUpperCase(Locale.ROOT);
    }

    @Override
    public Iterator<Language> iterator() {
        return cache().iterator();
    }
}
