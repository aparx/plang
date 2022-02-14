package io.github.sauranbone.plang.map;

import io.github.sauranbone.plang.PlangUtils;

import java.util.*;

/**
 * Thread-safe data binding implementation consisting of a map
 * implementation.
 *
 * @author Vinzent Zeband
 * @version 23:33 CET, 12.02.2022
 * @since 1.0
 */
public class DataBindMap extends AbstractPlangMap<Object, Object> implements DataBinder {

    final boolean caseSensitive;

    /**
     * Allocates a new binding map with no initial values that is
     * case-insensitive.
     * <p>Case-sensitivity is automatically disabled using this
     * {@link #DataBindMap(boolean)}.
     *
     * @see #DataBindMap(boolean)
     * @see #isCaseSensitive()
     */
    public DataBindMap() {
        this(false);
    }

    /**
     * Allocates a new binding map that is having {@code caseSensitive}
     * as the primary configuration attribute and no initial values.
     * <p>If {@code caseSensitive} is false, object keys that are bound
     * using string class type are made lowercase in order to provide a
     * case-insensitivity.
     *
     * @param caseSensitive false to enable case-insensitivity.
     * @see #isCaseSensitive()
     */
    public DataBindMap(boolean caseSensitive) {
        this(0, caseSensitive);
    }

    /**
     * Allocates a new binding map using given {@code initialCapacity}.
     * <p>Case-sensitivity is automatically disabled using this
     * {@link #DataBindMap(int, boolean)}.
     *
     * @param initialCapacity the target initial capacity of this map
     * @see #DataBindMap(int, boolean)
     * @see #isCaseSensitive()
     */
    public DataBindMap(int initialCapacity) {
        this(initialCapacity, false);
    }

    /**
     * Allocates a new binding map using given {@code map} as initial
     * values, which are copied over to this map reference.
     * <p>Case-sensitivity is automatically disabled using this
     * {@link #DataBindMap(Map, boolean)}.
     *
     * @param map the target initial values copied into this map
     * @see #DataBindMap(int, boolean)
     * @see #isCaseSensitive()
     */
    public DataBindMap(Map<Object, Object> map) {
        this(map, false);
    }

    /**
     * Allocates a new binding map using given {@code initialCapacity}.
     * <p>If {@code caseSensitive} is false, object keys that are bound
     * using string class type are made lowercase in order to provide a
     * case-insensitivity.
     *
     * @param initialCapacity the target initial capacity of this map
     * @param caseSensitive   false to enable case-insensitivity.
     * @see #isCaseSensitive()
     */
    public DataBindMap(int initialCapacity, boolean caseSensitive) {
        super(initialCapacity);
        this.caseSensitive = caseSensitive;
    }

    /**
     * Allocates a new binding map using given {@code map} as initial
     * values, which are copied over to this map reference.
     * <p>If {@code caseSensitive} is false, object keys that are bound
     * using string class type are made lowercase in order to provide a
     * case-insensitivity.
     *
     * @param map           the target initial values copied into this map
     * @param caseSensitive false to enable case-insensitivity.
     * @see #isCaseSensitive()
     */
    public DataBindMap(Map<Object, Object> map, boolean caseSensitive) {
        super(map);
        this.caseSensitive = caseSensitive;
    }

    @Override
    public final boolean isCaseSensitive() {
        return caseSensitive;
    }

    @Override
    public void bindToObjectKey(Object key, Object value) {
        super.set(computeKey(key), value);
    }

    @Override
    public boolean unbind(Object key) {
        return super.remove(computeKey(key)) != null;
    }

    @Override
    public boolean isBound(Object key) {
        return super.containsKey(computeKey(key));
    }

    @Override
    public Object get(Object key) {
        return super.get(computeKey(key));
    }

    @Override
    public Object computeKey(Object key) {
        Objects.requireNonNull(key);
        if (key instanceof String && !isCaseSensitive()) {
            return ((String) key).toLowerCase(Locale.ROOT);
        } else if (key instanceof Number) {
            return checkIndex(((Number) key).intValue());
        } else if (key instanceof Class) {
            return PlangUtils.getContravariant((Class<?>) key);
        }
        return key;
    }

    @Override
    public Collection<Object> keys() {
        return super.keys();
    }

    @Override
    public Collection<Object> values() {
        return super.values();
    }

    @Override
    public Set<Map.Entry<Object, Object>> entries() {
        return super.entries();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    protected final Map<Object, Object> createMap(Map<Object, Object> input) {
        return input == null ? createMap(0) : new HashMap<>(input);
    }

    @Override
    protected final Map<Object, Object> createMap(int initialCapacity) {
        return new HashMap<>(initialCapacity);
    }

    private int checkIndex(int intValue) {
        if (intValue < 0)
            throw new IndexOutOfBoundsException
                    (String.valueOf(intValue));
        return intValue;
    }

}
