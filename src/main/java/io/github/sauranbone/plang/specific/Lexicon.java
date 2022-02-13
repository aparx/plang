package io.github.sauranbone.plang.specific;

import com.sun.org.apache.bcel.internal.generic.FADD;
import io.github.sauranbone.plang.map.AbstractPlangMap;
import io.github.sauranbone.plang.placeholder.Placeholder;

import javax.xml.stream.FactoryConfigurationError;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Placeholder lexicon containing all the globally accessible placeholders
 * that can be accessed and modified using this utilities.
 *
 * @author Vinzent Zeband
 * @version 23:33 CET, 12.02.2022
 * @since 1.0
 */
public final class Lexicon extends AbstractPlangMap<String, Placeholder<?>> {

    private final boolean caseSensitive;

    /**
     * Allocates a new lexicon having case insensitivity.
     * <p>When a placeholder is set, it is automatically bound to its own
     * name but all in lowercase, in order to keep a case insensitivity.
     * This case sensitivity can be changed by allocating the {@link
     * #Lexicon(boolean)} constructor, thus this constructor is equivalent
     * to the following:
     * <pre><code>
     *     Lexicon lexicon = new Lexicon(false);
     * </code></pre>
     *
     * @see #Lexicon(boolean)
     */
    public Lexicon() {
        this(false);
    }

    /**
     * Allocates a new lexicon having {@code caseSensitive} as
     * configuration attribute.
     * <p>The case sensitivity, if set to false, decides whether
     * placeholder names should be made lowercase when mapped, in order to
     * keep placeholder names case insensitive.
     *
     * @param caseSensitive the target sensitivity
     * @apiNote It is recommended to make placeholder names case
     * insensitive, to be easier accessed by the third party developer.
     */
    public Lexicon(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Returns true if placeholder names are mapped as is, meaning that
     * they are case sensitive.
     *
     * @return false if mapped placeholders are not case sensitive and
     * their binding names are made lowercase (default: false)
     */
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * Returns true if a placeholder with given {@code key} is concurrently
     * bound.
     *
     * @param key the target placeholder name to check, {@code not null}
     * @return false if there is no placeholder having given {@code key} as
     * its names
     * @throws NullPointerException if {@code key} is null
     */
    public synchronized boolean has(String key) {
        Objects.requireNonNull(key);
        return super.containsKey(key);
    }

    /**
     * Binds the given {@code placeholder} to it's name into this lexicon.
     *
     * @param placeholder the placeholder to be bind
     */
    public synchronized void set(Placeholder<?> placeholder) {
        Objects.requireNonNull(placeholder);
        this.set(placeholder.getName(), placeholder);
    }

    /**
     * Retrieves the contained placeholder that is having the given {@code
     * name} as its literal name.
     *
     * @param name the target name to be retrieved
     * @return the target placeholder that is bound at {@code name}
     */
    @Override
    public synchronized Placeholder<?> get(String name) {
        return super.get(name);
    }

    /**
     * Removes the contained placeholder having the given {@code name} as
     * its name.
     *
     * @param name the target name to be removed
     * @return the placeholder that got removed
     */
    @Override
    public synchronized Placeholder<?> remove(String name) {
        return super.remove(name);
    }

    /**
     * Returns a collection of all concurrently contained placeholders.
     *
     * @return all concurrent placeholders
     */
    @Override
    public synchronized Collection<Placeholder<?>> values() {
        return super.values();
    }

    /**
     * Returns true if no placeholder has been registered yet.
     *
     * @return false if at least one placeholder has been registered
     */
    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Returns the concurrent amount of placeholders contained.
     *
     * @return the size of this lexicon
     */
    @Override
    public synchronized int size() {
        return super.size();
    }

    /**
     * Clears this lexicon by removing all placeholders.
     */
    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public synchronized Map<String, Placeholder<?>> getMap() {
        return super.getMap();
    }

    @Override
    protected final Map<String, Placeholder<?>> createMap(Map<String, Placeholder<?>> input) {
        return new HashMap<>(input);
    }

    @Override
    protected final Map<String, Placeholder<?>> createMap(int initialCapacity) {
        return new HashMap<>(initialCapacity);
    }

}
