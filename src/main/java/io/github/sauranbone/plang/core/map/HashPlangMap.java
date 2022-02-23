package io.github.sauranbone.plang.core.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract plang map implementation automatically allocating new hashmap
 * instances whenever this map is allocated.
 *
 * @param <K> the target key type
 * @param <V> the target value type
 * @see AbstractPlangMap
 * @see AbstractPlangMap#createMap(Map)
 * @see AbstractPlangMap#createMap(int)
 */
public abstract class HashPlangMap<K, V> extends AbstractPlangMap<K, V> {

    /**
     * Allocates a new map having no initial values.
     */
    public HashPlangMap() {
        super();
    }

    /**
     * Allocates a new map having the given {@code initialCapacity}.
     *
     * @param initialCapacity the initial capacity of this map
     */
    public HashPlangMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Allocates a new map containing the given {@code map} as initial
     * values, whose are copied to this map reference.
     *
     * @param map the target initial values that are copied over
     * @throws NullPointerException if any value in {@code map} contains
     *                              null
     */
    public HashPlangMap(Map<K, V> map) {
        super(map);
    }

    @Override
    protected final Map<K, V> createMap(Map<K, V> input) {
        return input == null ? new HashMap<>() : new HashMap<>(input);
    }

    @Override
    protected final Map<K, V> createMap(int initialCapacity) {
        return new HashMap<>(initialCapacity);
    }

}
