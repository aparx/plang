package io.github.sauranbone.plang.core.exception;

import io.github.sauranbone.plang.core.specific.MessageRegistry;

/**
 * Unchecked exception stating that a target key index cannot be resolved
 * within a message context.
 *
 * @author Vinzent Zeband
 * @version 04:06 CET, 15.02.2022
 * @since 1.0
 */
public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(String index, MessageRegistry registry) {
        super("Cannot find message: \"" + index + "\" in \"" + (registry == null ? "null" : registry.getIdentifier()) + '\"');
    }

    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageNotFoundException(Throwable cause) {
        super(cause);
    }

    public MessageNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
