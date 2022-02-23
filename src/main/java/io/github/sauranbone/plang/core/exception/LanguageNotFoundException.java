package io.github.sauranbone.plang.core.exception;

/**
 * @author Vinzent Zeband
 * @version 01:17 CET, 23.02.2022
 * @since 1.0
 */
public class LanguageNotFoundException extends RuntimeException {

    public LanguageNotFoundException() {
        super();
    }

    public LanguageNotFoundException(String message) {
        super(message);
    }

    public LanguageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LanguageNotFoundException(Throwable cause) {
        super(cause);
    }

    public LanguageNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static LanguageNotFoundException byAbbreviation(String abbreviation) {
        return new LanguageNotFoundException("Cannot find language with " +
                "abbreviation \"" + abbreviation + "\" (case sensitive?)");
    }

    public static LanguageNotFoundException byName(String name) {
        return new LanguageNotFoundException("Cannot find language with " +
                "name \"" + name + "\"");
    }
}
