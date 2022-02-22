package io.github.sauranbone.plang.error;

/**
 * Parsing error type that can occur when parsing messages, languages or
 * general features.
 *
 * @author Vinzent Zeband
 * @version 00:25 CET, 23.02.2022
 * @since 1.0
 */
public enum ParseErrorType {

    /**
     * Breaks the current parsing progression.
     */
    FATAL,

    /**
     * May break the current parsing progression.
     */
    ISSUE,

    /**
     * Only a visual warning that does not break the parsing progression.
     */
    WARNING,

    /**
     * Only a notification and must neither count as warning nor as
     * breaking the current flow.
     */
    NOTIFY;

    /**
     * Returns true if this error type is a breaking error type, meaning it
     * may cause an exception or breaks the current process flow.
     *
     * @return false if this type is of visual nature (like WARNING)
     */
    public boolean isBreaking() {
        return this != WARNING && isError();
    }

    /**
     * Returns true if this error type is really an error or not.
     *
     * @return false if this is NOTIFY
     */
    public boolean isError() {
        return this != NOTIFY;
    }

}
