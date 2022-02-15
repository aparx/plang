package io.github.sauranbone.plang.specific;

import io.github.sauranbone.plang.exception.MessageNotFoundException;
import io.github.sauranbone.plang.map.HashPlangMap;
import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Vinzent Zeband
 * @version 03:16 CET, 15.02.2022
 * @since 1.0
 */
public class MessageRegistry extends HashPlangMap<String, Message> implements Serializable {

    private final Language signature;

    /**
     * Allocates a new message registry having no initial values.
     *
     * @param signature the target language of this registry
     */
    public MessageRegistry(Language signature) {
        this(signature, 0);
    }

    /**
     * Allocates a new message registry having the given
     * {@code initialCapacity}.
     *
     * @param signature       the target language of this registry
     * @param initialCapacity the initial capacity of this map
     */
    public MessageRegistry(Language signature, int initialCapacity) {
        super(initialCapacity);
        Objects.requireNonNull(signature, "Language");
        this.signature = signature;
    }

    /**
     * Allocates a new message registry containing the given {@code map} as
     * initial values, whose are copied to this map reference.
     *
     * @param signature the target language of this registry
     * @param map       the target initial values that are copied over
     * @throws NullPointerException if any value in {@code map} contains
     *                              null
     */
    public MessageRegistry(Language signature, Map<String, Message> map) {
        super(map);
        Objects.requireNonNull(signature, "Language");
        this.signature = signature;
    }

    /**
     * Allocates a new pair instance having given {@code index} as its key
     * name and {@code content} as its message content.
     *
     * @param index   the target key index name
     * @param content the target content of the message
     * @return the newly allocated message pair, {@code not null}
     * @throws NullPointerException     if {@code index} is null
     * @throws IllegalArgumentException if {@code index} is empty
     * @apiNote This method must be overridden when the default message
     * implementation is different compared to the normal plain message.
     */
    public MessagePair createPair(String index, String content) {
        return new MessagePair(index, new Message(content, signature));
    }

    /**
     * Binds the given {@code pair} to its key index into this registry and
     * overwrites any previous equal {@code key} index pair.
     *
     * @param pair the target pair to be bound
     * @throws IllegalArgumentException if the language of {@code pair}'s
     *                                  language is not equal to this
     *                                  signature
     * @throws NullPointerException     if {@code pair}, its key or message
     *                                  is null
     */
    public synchronized void set(MessagePair pair) {
        Objects.requireNonNull(pair);
        String index = Objects.requireNonNull(pair.getKey());
        Message message = Objects.requireNonNull(pair.getMessage());
        checkLangEqual(message.getLanguage());
        set(index, message);
    }

    /**
     * Allocates a new pair instance having given {@code index} as its key
     * name and {@code content} as its message content and binds it to the
     * {@code index} as key into this registry.
     *
     * @param index   the target key index name
     * @param content the target content of the message
     * @return the newly allocated message pair, {@code not null}
     * @throws NullPointerException     if {@code index} is null
     * @throws IllegalArgumentException if {@code index} is empty
     * @see #createPair(String, String)
     * @see #set(MessagePair)
     */
    public synchronized MessagePair set(String index, String content) {
        MessagePair pair = createPair(index, content);
        Objects.requireNonNull(pair, "Allocated pair");
        set(pair.getKey(), pair.getMessage());
        return pair;
    }

    /**
     * Returns the message value which key index is equal to the given
     * {@code index} index, otherwise calls {@code def} and returns the
     * returned value if the {@code index} cannot be resolved.
     * <p>The supplied {@code def} value can be used as sort of an error
     * message, that is shown if {@code index} cannot be resolved.
     *
     * @param index the target key to be retrieved, {@code not null}
     * @param def   the default value if {@code index} cannot be resolved,
     *              {@code nullable}
     * @return the target message that is bound at {@code index}
     * @throws NullPointerException     if {@code index} is null
     * @throws MessageNotFoundException if {@code def} is null and
     *                                  {@code index} cannot be located
     * @see #set(MessagePair)
     * @see #set(String, String)
     */
    public synchronized Message get(String index, Supplier<? extends Message> def) {
        Message message;
        if ((message = super.get(index)) == null) {
            if (def != null) return def.get();
            //Throw exception as default consumer is null
            throw new MessageNotFoundException(index, this);
        }
        return message;
    }

    /**
     * Returns the message value which key index is equal to the given
     * {@code index} index.
     *
     * @param index the target key to be retrieved
     * @return the target message that is bound at {@code index}
     * @throws NullPointerException     if {@code index} is null
     * @throws MessageNotFoundException if {@code index} cannot be
     *                                  resolved
     * @see #set(MessagePair)
     * @see #set(String, String)
     * @see #get(String, Supplier)
     */
    @Override
    public synchronized Message get(String index) {
        return get(index, null);
    }

    /**
     * Returns the language this registry is bound to.
     *
     * @return this language property, {@code not null}
     */
    public Language getSignature() {
        return signature;
    }

    /**
     * Returns the identifier of this signature.
     *
     * @return the language identifier of this registry, {@code not null}
     * @see #getSignature()
     * @see Language#getIdentifier(Language)
     */
    public final String getIdentifier() {
        return Language.getIdentifier(signature);
    }

    protected final void checkLangEqual(Language lang) {
        Validate.isTrue(this.signature.isEqual(lang),
                "Language identifier (\"" + Language.getIdentifier(lang)
                        + "\") must be equal to registry's language " +
                        "identifier (\"" + Language.getIdentifier(signature) +
                        "\")");
    }

}
