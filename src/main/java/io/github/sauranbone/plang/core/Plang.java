package io.github.sauranbone.plang.core;

import io.github.sauranbone.plang.core.factory.LanguageFactory;
import io.github.sauranbone.plang.core.factory.LocalisationFactory;

/**
 * Plang library class containing specified default factories and
 * attributes describing a default and widely used behaviour in their
 * instances and singletons.
 *
 * @author Vinzent Zeband
 * @version 23:31 CET, 12.02.2022
 * @since 1.0
 */
public final class Plang {

    private static final LanguageFactory LANGUAGE_FACTORY =
            new LocalisationFactory();

    /**
     * Returns the default language factory of Plang.
     *
     * @return the default language factory singleton instance,
     * {@code not null}
     */
    public static LanguageFactory getLanguageFactory() {
        return LANGUAGE_FACTORY;
    }

}
