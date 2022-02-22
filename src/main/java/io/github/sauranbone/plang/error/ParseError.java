package io.github.sauranbone.plang.error;

import java.util.Objects;

/**
 * Error class containing attributes and utilities describing a parsing or
 * parsing subdivision issue, warning or general error.
 * <p>This class can also be used to transmit user input issues or other
 * errors, that shall be transformed and processed using the underlying
 * language's error handling, which may trigger further nested parsing
 * algorithms.
 *
 * @author Vinzent Zeband
 * @version 00:24 CET, 23.02.2022
 * @since 1.0
 */
public class ParseError {

    private final ParseErrorType errorType;
    private final String message;

    /**
     * Allocates a new parser error instance containing given constructive
     * parameters as private attributes within the instance.
     *
     * @param errorType the target type of the error
     * @param message   the target message of the error
     * @throws NullPointerException if {@code errorType} is null
     */
    public ParseError(ParseErrorType errorType, String message) {
        Objects.requireNonNull(errorType);
        this.errorType = errorType;
        this.message = message;
    }

    public ParseErrorType getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

}
