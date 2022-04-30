package io.github.sauranbone.plang.core.error;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * Default language error handling implementation that can be used to be
 * extended to create custom error solutions using existing validation.
 *
 * @author Vinzent Zeband
 * @version 00:30 CET, 23.02.2022
 * @since 1.0
 */
public class DefaultErrorHandler implements LanguageErrorHandler {

    public static final DefaultErrorHandler SINGLETON = new DefaultErrorHandler();

    protected DefaultErrorHandler() {
        //Hide default constructor from public
    }

    /**
     * {@inheritDoc}
     *
     * @param error the target error to be logged
     * @throws RuntimeException     if {@code error} is a breaking type
     * @throws NullPointerException if {@code error} is null
     */
    @Override
    public void handle(ParseError error) {
        Objects.requireNonNull(error);
        final ParseErrorType type = error.getErrorType();
        final String message = error.getMessage();
        Validate.isTrue(type != null && !type.isBreaking(), message);
    }

}
