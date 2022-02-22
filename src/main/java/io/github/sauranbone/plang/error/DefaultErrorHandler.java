package io.github.sauranbone.plang.error;

import org.apache.commons.lang3.Validate;

import java.time.temporal.ValueRange;
import java.util.Objects;

/**
 * Default language error handling implementation, that redirects any
 * handle into the default system output stream, so the console.
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
     * <p>This implementation is simply redirecting any warning into the
     * output stream which type is not breaking the current progression. If
     * the given {@code error} type is breaking the current flow, an
     * unchecked exception is thrown.
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
        System.out.println(type + ": " + message);
    }

}
