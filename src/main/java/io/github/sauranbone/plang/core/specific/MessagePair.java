package io.github.sauranbone.plang.core.specific;

import io.github.sauranbone.plang.core.parsing.MessageLexer;
import io.github.sauranbone.plang.core.parsing.MessageParser;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;

/**
 * Message composition adding a target key index to the message to uniquely
 * identify a message in language context.
 *
 * @see MessageRegistry
 */
public class MessagePair {

    /* The target key of this message pair */
    final String key;

    final Message message;

    /**
     * Allocates a new message pair having the given {@code index} bound to
     * the given {@code message}, that represents a target message.
     *
     * @param index   the target key index of this messaging pair
     * @param message the target message that is bound to {@code index}
     * @throws NullPointerException     if any argument is null
     * @throws IllegalArgumentException if {@code index} is empty
     * @see Message#Message(String, Language)
     * @see Language#parse(String)
     * @see Language#getLexer()
     * @see Language#getParser()
     * @see MessageLexer#tokenize(Language, String)
     * @see MessageParser#parse(Language, List)
     * @see MessageRegistry#createPair(String, String)
     */
    public MessagePair(String index, Message message) {
        Objects.requireNonNull(index, "Key");
        Objects.requireNonNull(message, "Message");
        Validate.isTrue(!index.isEmpty(), "Key cannot be empty");
        this.key = index;
        this.message = message;
    }

    /**
     * Returns the message value off this pair.
     *
     * @return the message stored in this pair
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Returns the key this pair is bound at.
     *
     * @return the target key to this message
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns true if {@code o} is equal to this instance or has the same
     * message pair key index.
     *
     * @param o the target object to be compared
     * @return false if {@code o} is not equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessagePair that = (MessagePair) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, message);
    }
}
