package io.github.sauranbone.plang.core.error;

import io.github.sauranbone.plang.core.parsing.impl.DefaultParser;

/**
 * Interface implemented to apply parsing and general error handling
 * utility that is loggable.
 * <p>The error handler can not only be used to log, store or identify
 * issues during parsing or processing, but also to log and notify the
 * end-user for a possible wrong user input or issue.
 * <p>The error handler is essentially used and useful whenever a
 * parsing action has failed or wants to notify the end-user or developer
 * that something might cause troubles or issues or may even be fatal in
 * perspective.
 * <p>The language error handler is used by default by the
 * {@link DefaultParser} or general processing tools, that are responsible
 * for analyzing, parsing (including tokenizing) or processing strings of
 * data in context of a message or language type input or scenario.
 *
 * @author Vinzent Zeband
 * @version 00:23 CET, 23.02.2022
 * @since 1.0
 */
public interface LanguageErrorHandler {

    /**
     * Handles the given {@code error} on the underlying implementation and
     * potentially logs the issue that has been caused.
     *
     * @param error the target error to be logged
     */
    void handle(final ParseError error);

}
