package io.github.sauranbone.plang.map;

import io.github.sauranbone.plang.PlangUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * Thread-safe data binding implementation consisting of a map
 * implementation.
 *
 * @author Vinzent Zeband
 * @version 23:33 CET, 12.02.2022
 * @since 1.0
 */
public class DataBindMap extends HashPlangMap<Object, Object> implements DataBinder {

    transient public static final boolean DEFAULT_CASE_SENSITIVITY = false;

    //Highest and second-highest index inserted
    private int high = -1, midst = -1;

    final boolean caseSensitive;

    /**
     * Allocates a new binding map with no initial values that is
     * case-insensitive.
     * <p>Case-sensitivity is automatically set to this
     * {@link #DEFAULT_CASE_SENSITIVITY} using
     * {@link #DataBindMap(Map, boolean)}.
     *
     * @see #DataBindMap(boolean)
     * @see #isCaseSensitive()
     */
    public DataBindMap() {
        this(DEFAULT_CASE_SENSITIVITY);
    }

    /**
     * Allocates a new binding map that is having {@code caseSensitive} as
     * the primary configuration attribute and no initial values.
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
     * <p>Case-sensitivity is automatically set to this
     * {@link #DEFAULT_CASE_SENSITIVITY} using
     * {@link #DataBindMap(Map, boolean)}.
     *
     * @param initialCapacity the target initial capacity of this map
     * @see #DataBindMap(int, boolean)
     * @see #isCaseSensitive()
     */
    public DataBindMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_CASE_SENSITIVITY);
    }

    /**
     * Allocates a new binding map using given {@code map} as initial
     * values, which are copied over to this map reference.
     * <p>Case-sensitivity is automatically set to this
     * {@link #DEFAULT_CASE_SENSITIVITY} using
     * {@link #DataBindMap(Map, boolean)}.
     *
     * @param map the target initial values copied into this map
     * @see #DataBindMap(int, boolean)
     * @see #isCaseSensitive()
     */
    public DataBindMap(Map<Object, Object> map) {
        this(map, DEFAULT_CASE_SENSITIVITY);
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
        evalall();
    }

    /**
     * Allocates a new binding map having the given different {@code types}
     * bound to their corresponding highest superclasses using key
     * computation on their classes.
     * <p>If {@code types} contains any null value it is skipped and not
     * bound.
     * <p>Case-sensitivity is automatically set to this
     * {@link #DEFAULT_CASE_SENSITIVITY} using
     * {@link #typesSensitivity(boolean, Object...)}.
     *
     * @param types the target objects whose types are bound to the
     *              returning map
     * @see #typesSensitivity(boolean, Object...)
     * @see #computeKey(Object)
     * @see DataBindMap#bindType(Object)
     * @see DataBindMap#bindType(Object...)
     * @see #indexSensitivity(boolean, Object...)
     */
    public static DataBindMap types(Object... types) {
        return typesSensitivity(DEFAULT_CASE_SENSITIVITY, types);
    }

    /**
     * Allocates a new binding map having the given different {@code types}
     * bound to their corresponding highest superclasses using key
     * computation on their classes.
     * <p>If {@code types} contains any null value it is skipped and not
     * bound.
     *
     * @param caseSensitive false to enable case-insensitivity
     * @param types         the target objects whose types are bound to the
     *                      returning map
     * @see #computeKey(Object)
     * @see DataBindMap#bindType(Object)
     * @see DataBindMap#bindType(Object...)
     * @see #indexSensitivity(boolean, Object...)
     */
    public static DataBindMap typesSensitivity(boolean caseSensitive, Object... types) {
        if (ArrayUtils.isEmpty(types)) return new DataBindMap();
        DataBindMap map = new DataBindMap();
        for (Object type : types) {
            if (type == null) continue;
            map.bindType(type);
        }
        return map;
    }


    /**
     * Allocates a new binding map having every element within
     * {@code array} bound to their corresponding index.
     * <p>Case-sensitivity is automatically set to this
     * {@link #DEFAULT_CASE_SENSITIVITY} using
     * {@link #indexSensitivity(boolean, Object...)}
     *
     * @param array the target values to be pushed to the final map
     * @see #indexSensitivity(boolean, Object...)
     * @see #DataBindMap(int, boolean)
     * @see #push(Object...)
     */
    public static DataBindMap index(Object... array) {
        return index(DEFAULT_CASE_SENSITIVITY, array);
    }

    /**
     * Allocates a new binding map having every element within
     * {@code array} bound to their corresponding index.
     *
     * @param caseSensitive false to enable case-insensitivity
     * @param array         the target values to be pushed to the final
     *                      map
     * @see #DataBindMap(int, boolean)
     * @see #push(Object...)
     */
    public static DataBindMap indexSensitivity(boolean caseSensitive, Object... array) {
        int length = ArrayUtils.getLength(array);
        DataBindMap map = new DataBindMap(length, caseSensitive);
        map.push(array);    //Finally, push the actual values
        return map;         //Simply return the polished map
    }

    @Override
    public final boolean isCaseSensitive() {
        return caseSensitive;
    }

    @Override
    public void bindToObjectKey(Object key, Object value) {
        Object obj = computeKey(key);
        if (obj instanceof Integer) {
            evalidx((int) obj, true);
        }
        super.set(obj, value);
    }

    private int getNumber(Object v) {
        return v instanceof Number ? ((Number) v).intValue() : -1;
    }

    @Override
    public void push(Object... values) {
        arraypush(values, 1 + high);
    }

    @Override
    public boolean unbind(Object key) {
        Object obj = computeKey(key);
        if (!isBound(obj)) return false;
        evalidx((int) obj, false);
        super.remove(obj);
        return true;
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
            return PlangUtils.getTopSuperclass((Class<?>) key);
        }
        return key;
    }

    @Override
    public int getHighestIndex() {
        return high;
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

    private int checkIndex(int intValue) {
        if (intValue < 0)
            throw new IndexOutOfBoundsException(String.valueOf(intValue));
        return intValue;
    }

    private void arraypush(Object[] array, int offset) {
        if (ArrayUtils.isEmpty(array)) return;
        offset = Math.max(offset, 0);
        for (int i = 0; i < array.length; i++) {
            //Bind the array value at index
            this.bind(offset + i, array[i]);
        }
    }

    private void evalidx(int i, boolean put) {
        if (i == Integer.MAX_VALUE) {
            throw new IndexOutOfBoundsException("Index has reached " + "MAX_VALUE and thus is illegal");
        }
        if (i > high) {
            if (put) high = i;
            else high = midst;
        } else if (i > midst) {
            if (put) i = midst;
            else synchronized (this) {
                //Re-evaluate every entry
                high = -1;
                midst = -1;
                evalall();
            }
        }
    }

    private void evalall() {
        for (Map.Entry<Object, Object> obj : entries()) {
            Object v = obj.getValue();
            if (v == null) continue;
            evalidx(getNumber(v), true);
        }
    }

}
