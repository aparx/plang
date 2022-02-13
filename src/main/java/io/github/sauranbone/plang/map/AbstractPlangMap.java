package io.github.sauranbone.plang.map;

import io.github.sauranbone.plang.specific.Lexicon;

import java.util.*;

/**
 * Abstract class containing a map composition of any map implementation
 * and forwarding internal utilities.
 * <p>All methods contained in this class are protected in order to change
 * the visibility to the third party developer in the underlying
 * implementation, depending on the underlying needs and requirements.
 *
 * @author Vinzent Zeband
 * @version 01:55 CET, 13.02.2022
 * @see Lexicon
 * @since 1.0
 */
public abstract class AbstractPlangMap<K, V> {

    private final Map<K, V> map;

    /**
     * Allocates a new map having no initial values.
     */
    public AbstractPlangMap() {
        this(0);
    }

    /**
     * Allocates a new map having the given {@code initialCapacity}.
     *
     * @param initialCapacity the initial capacity of this map
     */
    public AbstractPlangMap(int initialCapacity) {
        this.map = createMap(initialCapacity);
    }

    /**
     * Allocates a new map containing the given {@code map} as initial
     * values, whose are copied to this map reference.
     *
     * @param map the target initial values that are copied over
     * @throws NullPointerException if any value in {@code map} contains
     *                              null
     */
    public AbstractPlangMap(Map<K, V> map) {
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<K, V> m : map.entrySet()) {
                Objects.requireNonNull(m.getKey(), "Map Key");
                Objects.requireNonNull(m.getValue(), "Map Value");
            }
            this.map = createMap(map);
        } else this.map = createMap(0);
    }

    protected abstract Map<K, V> createMap(Map<K, V> input);

    protected abstract Map<K, V> createMap(int initialCapacity);

    /**
     * Returns true if {@code key} is already bound.
     *
     * @param key the target key to check
     * @return false if {@code key} is not bound
     * @see Map#containsKey(Object)
     */
    protected synchronized boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    /**
     * Returns true if {@code value} is already bound to any key.
     *
     * @param value the target value to check
     * @return false if {@code value} is not bound
     * @see Map#containsValue(Object)
     */
    protected synchronized boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    /**
     * Retrieves the value bound to {@code key}.
     *
     * @param key the target key to be retrieved
     * @return the value that is bound to {@code key}.
     * @throws NullPointerException if {@code key} is null
     */
    protected synchronized V get(K key) {
        Objects.requireNonNull(key);
        return map.get(key);
    }

    /**
     * Binds the given {@code value} to the {@code key} and overwrites any
     * equal {@code key} boundary.
     *
     * @param key   the target key path for {@code value}
     * @param value the target value of {@code key}
     * @return this instance for an easier development flow
     * @throws NullPointerException if {@code key} or {@code value} is
     *                              null
     */
    protected synchronized AbstractPlangMap<K, V> set(K key, V value) {
        Objects.requireNonNull(key, "Key");
        Objects.requireNonNull(value, "Value");
        map.put(key, value);
        return this;
    }

    /**
     * Unbinds and removes the given {@code key} and its bound value from
     * this map and returns the bound value of the {@code key} that got
     * removed.
     *
     * @param key the target key to be removed
     * @return the bound value of {@code key}, or null if not bound
     */
    protected synchronized V remove(K key) {
        return map.remove(key);
    }

    /**
     * Unbinds and removes the given {@code key} and {@code value} pair
     * from this map and returns true if that pair is removed.
     *
     * @param key   the target key to be removed
     * @param value the target value to be removed
     * @return true if the given pair is removed
     */
    protected synchronized boolean remove(K key, V value) {
        return map.remove(key, value);
    }

    /**
     * Returns all the keys of this map.
     *
     * @return the keys of this map
     */
    protected synchronized Set<K> keys() {
        return map.keySet();
    }

    /**
     * Returns all values contained in this map.
     *
     * @return the values of this map
     */
    protected synchronized Collection<V> values() {
        return map.values();
    }

    /**
     * Returns all entries concurrently contained in this map.
     *
     * @return all concurrent entry pairs of this map
     */
    protected synchronized Set<Map.Entry<K, V>> entries() {
        return map.entrySet();
    }

    /**
     * Returns true if this map is empty.
     *
     * @return false if this map contains at least one valid entry
     */
    protected synchronized boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns the concurrent size of this map.
     *
     * @return the amount of entries concurrently contained
     */
    protected synchronized int size() {
        return map.size();
    }

    /**
     * Clears every entry of this map.
     */
    protected synchronized void clear() {
        map.clear();
    }

    /**
     * Returns a new immutable map having all the same entries as this
     * map.
     *
     * @return the immutable map copy
     */
    protected synchronized Map<K, V> getMap() {
        return createMap(map);
    }


}
