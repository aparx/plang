package io.github.sauranbone.plang.core.parsing;

import java.util.Objects;

/**
 * A token that focuses to a certain part of content or input and that has
 * an assignment of what the token is meaning.
 * <p>The token contains three primary attributes describing it.
 * Primarily the raw content and the value content, as well as the type
 * that describes what this token actually means.
 * <p>For example, if this token is a placeholder that has been tokenized
 * by a pre-determined syntax that requires curly brackets around the
 * target literal placeholder, the raw content will include the curly
 * brackets while the {@code value} should only contain the target
 * placeholder name;
 * <pre><code>
 *     new MessageToken("{fooBar}", "fooBar", TokenType.PLACEHOLDER);
 * </code></pre>
 * <p>If raw is equal value, value can be null and thus state that the
 * token has no special syntax contained.
 *
 * @author Vinzent Zeband
 * @version 23:32 CET, 12.02.2022
 * @since 1.0
 * <a href="https://en.wikipedia.org/wiki/Lexical_analysis#Token">
 * https://en.wikipedia.org/wiki/Lexical_analysis#Token</a>
 */
public class MessageToken {

    final String raw, value;

    private MessageTokenType type;

    /**
     * Allocates a new message token having the given {@code raw} as the
     * raw content of the token, {@code value} and {@code type} as the
     * assignment.
     * <p>If {@code raw} is equal {@code value}, {@code value} can be null
     * and thus states that the token has no special syntax contained.
     * <p>If {@code type} has a syntax, a value is required and thus
     * cannot be null anymore.
     *
     * @param raw   the raw value of the token, {@code not null}
     * @param value the target value of the token, {@code nullable}
     * @param type  the target meaning of the token, {@code not null}
     * @throws NullPointerException if {@code raw} or {@code type} is null,
     *                              or if {@code type} has a syntax and
     *                              {@code value} is null
     */
    public MessageToken(String raw, String value, MessageTokenType type) {
        Objects.requireNonNull(raw, "Raw");
        Objects.requireNonNull(type, "Type");
        if ((this.type = type).hasSyntax()) {
            Objects.requireNonNull(value,
                    "Token value must not be null on syntax type");
        }
        this.raw = raw;
        this.value = value;
    }

    /**
     * Returns the full raw content of this token, including the syntax of
     * its type, if there is any.
     *
     * @return the unmodified and full content of this token
     */
    public String getRaw() {
        return raw;
    }

    /**
     * Returns the actual value content of this {@code token}.
     *
     * @return the actual content of this token, {@code nullable}
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns true if this value is not null and thus has a value.
     *
     * @return false if this value is null
     */
    public boolean hasValue() {
        return getValue() != null;
    }

    /**
     * Returns true if this type is representing a placeholder and if this
     * value is also valid.
     *
     * @return if this type is equal to {@link MessageTokenType#PLACEHOLDER}
     * @see MessageTokenType#PLACEHOLDER
     */
    public synchronized final boolean isPlaceholder() {
        return hasValue() && type == MessageTokenType.PLACEHOLDER;
    }

    /**
     * Returns the type, or meaning, of this token.
     *
     * @return the type of this token
     */
    public synchronized final MessageTokenType getType() {
        return type;
    }

    /**
     * Updates this type meaning to the given {@code type}.
     *
     * @param type the new target type
     * @throws NullPointerException if {@code type} is null
     */
    public synchronized final void setType(MessageTokenType type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageToken{" + "raw='" + raw + '\'' + ", value='" + value + '\'' + ", type=" + type + '}';
    }
}
