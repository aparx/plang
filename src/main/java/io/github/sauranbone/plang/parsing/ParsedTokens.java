package io.github.sauranbone.plang.parsing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Thread-safe list composition implementation that allows to read and
 * cache and already validated message tokens.
 *
 * @author Vinzent Zeband
 * @version 23:37 CET, 12.02.2022
 * @see Builder
 * @since 1.0
 */
public class ParsedTokens implements Iterable<MessageToken> {

    private final List<MessageToken> tokens;

    /**
     * Allocates an empty parsed token list that cannot contain any
     * content.
     */
    public ParsedTokens() {
        this(new ArrayList<>());
    }

    /**
     * Allocates a new parsed token list instance having the given {@code
     * values} as initial values, meaning that any mutations in the given
     * {@code values} list have no affect on this instance.
     *
     * @param values the target initial values
     * @throws NullPointerException if {@code values} is null
     */
    public ParsedTokens(List<MessageToken> values) {
        this.tokens = new ArrayList<>(values);
    }

    private ParsedTokens(ArrayList<MessageToken> reference) {
        Objects.requireNonNull(reference);
        this.tokens = reference;
    }

    /**
     * Returns the message token that is stored at given {@code index}.
     *
     * @param index the target index to be received
     * @return the token at {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is negative or
     *                                   above this size (inclusive)
     */
    public synchronized MessageToken get(int index) {
        return tokens.get(index);
    }

    /**
     * Returns true if no token has been concurrently added yet.
     *
     * @return false if any valid token has been added
     */
    public synchronized boolean isEmpty() {
        return tokens.isEmpty();
    }

    /**
     * Returns the amount of tokens contained concurrently.
     *
     * @return the amount of tokens contained
     */
    public synchronized int size() {
        return tokens.size();
    }

    /**
     * Returns a new immutable list of tokens concurrently containing all
     * the other tokens that are contained in this instance.
     *
     * @return the immutable token list
     */
    public synchronized List<MessageToken> getTokens() {
        return new ArrayList<>(tokens);
    }

    @Override
    public synchronized Iterator<MessageToken> iterator() {
        return tokens.iterator();
    }

    /**
     * Thread-safe parsed token list builder that allows mutations to
     * happen before the instance is created.
     */
    public static class Builder {

        private final ArrayList<MessageToken> ref;

        /**
         * Allocates a new parsed token list builder.
         */
        public Builder() {
            ref = new ArrayList<>();
        }

        /**
         * Builds the current list of added tokens and returns the new
         * immutable parsed token list instance.
         *
         * @return the newly allocated token list instance
         */
        public synchronized ParsedTokens build() {
            return new ParsedTokens(ref);
        }

        /**
         * Returns true if this parsed token list contains the given {@code
         * token}.
         *
         * @param token the target token to be tested
         * @return false if {@code token} is not contained
         * @throws NullPointerException if {@code token} is null
         */
        public synchronized boolean contains(MessageToken token) {
            Objects.requireNonNull(token);
            return this.ref.contains(token);
        }

        /**
         * Adds the given {@code token} to this list if not null.
         *
         * @param token the target token to be added, {@code not null}
         * @return this instance so method chains are able to exist
         * @throws NullPointerException if {@code token} is null
         * @apiNote It is not recommended to mutate this list outside of
         * actual parsing algorithms.
         */
        public synchronized ParsedTokens.Builder add(MessageToken token) {
            Objects.requireNonNull(token);
            this.ref.add(token);
            return this;
        }

    }

}
