package io.github.sauranbone.plang.map;

import io.github.sauranbone.plang.placeholder.Placeholder;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Vinzent Zeband
 * @version 23:33 CET, 12.02.2022
 * @since 1.0
 */
public interface DataBinder {

    /**
     * Returns true if placeholder name keys are bound as is, meaning that
     * they are case-sensitive.
     *
     * @return false if bound placeholders are not case-sensitive and
     * their binding names are made lowercase (default: false)
     * @see #bind(String, Object)
     * @see #computeKey(Object)
     */
    boolean isCaseSensitive();

    /**
     * Binds the given {@code value} to the given literal {@code
     * placeholder} name and overwrites any previous linked value to
     * it.
     *
     * @param placeholder the target placeholder to assign {@code value} to
     * @param value       the target value that is assigned to the given
     *                    {@code placeholder}
     * @throws NullPointerException if {@code placeholder} is null
     * @see #computeKey(Object)
     * @see #bindToObjectKey(Object, Object)
     */
    default void bind(String placeholder, Object value) {
        bindToObjectKey(placeholder, value);
    }

    /**
     * Binds the given {@code value} to the given {@code index} and
     * overwrites any previous linked value to {@code index}.
     *
     * @param index the target index {@code value} is assigned to
     * @param value the value that is assigned to {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is below zero
     * @see #computeKey(Object)
     * @see #bindToObjectKey(Object, Object)
     */
    default void bind(int index, Object value) {
        bindToObjectKey(index, value);
    }

    /**
     * Binds the given {@code value} to its class and thus addresses any
     * placeholder accepting the given {@code value} class as an
     * accepting target class for transformation.
     *
     * @param value the value that is assigned to its own class
     * @throws NullPointerException if {@code value} is null
     * @apiNote A better alternative is {@link #bind(Class, Object)},
     * that requires the class type of {@code value} to be set, but
     * allows the {@code value} to be null and thus adds an extra layer
     * of less maintenance onto the development.
     * @see #bind(Class, Object)
     */
    default void bind(Object value) {
        Objects.requireNonNull(value, "Cannot identify value class type");
        bindToObjectKey(value.getClass(), value);
    }

    /**
     * Binds the given {@code value} to the given {@code type} class type
     * and overwrites any previous linked value to the given class type.
     * <p>This is very useful to address any placeholder which is not
     * static and accepts the given {@code type} as accepting target class
     * type in its transformative utility.
     *
     * @param type  the target key {@code value} is assigned to
     * @param value the value that is assigned to {@code type}
     * @param <T>   the target type to ensure {@code value} is of same
     *              type as {@code type}
     * @throws NullPointerException if {@code type} is null
     * @apiNote The only difference between {@link #bind(Object)} and
     * this method is, that this method allows nullable values to exist
     * due to the target type already being specified.
     * @see #computeKey(Object)
     * @see #bindToObjectKey(Object, Object)
     * @see Placeholder#isStatic()
     * @see Placeholder#getAcceptingType()
     * @see Placeholder#transform(Object)
     */
    default <T> void bind(Class<T> type, T value) {
        bindToObjectKey(type, value);
    }

    /**
     * Binds the given {@code value} to the given unidentified {@code
     * key}, that is used as key and overwrites any previous
     * linked value to it.
     * <p>The given {@code key} is automatically computed using this
     * {@link #computeKey(Object)} and thus must not be computed
     * beforehand.
     *
     * @param key   the target object to assign {@code value} to
     * @param value the target value that is assigned to {@code key} as
     *              key
     * @throws NullPointerException      if {@code key} is null
     * @throws IndexOutOfBoundsException if {@code key} is a number and
     *                                   below zero
     * @see #computeKey(Object)
     * @see #isCaseSensitive()
     */
    void bindToObjectKey(Object key, Object value);

    /**
     * Unbinds the given {@code key} as key from this binder.
     * <p>The given {@code key} is computed depending on its type as
     * well as this binder's configuration, using this containing
     * {@link #computeKey(Object)} utility.
     *
     * @param key the target key object that is unbound
     * @return true if {@code key} had a value bound and is now
     * unbound and thus removed successfully
     * @see #computeKey(Object)
     * @see #isCaseSensitive()
     */
    boolean unbind(Object key);

    /**
     * Returns true if the computed version of {@code key} is already
     * bound within this binder.
     * <p>The given {@code key} is computed depending on its type as
     * well as this binder's configuration, using this containing
     * {@link #computeKey(Object)} utility.
     *
     * @param key the target key to be checked if bound
     * @return false if {@code key} is not bound concurrently
     */
    boolean isBound(Object key);

    /**
     * Returns the value that is bound to the given {@code key} and
     * computes the key depending on this configuration.
     * <p>The given {@code key} is computed depending on its type as
     * well as this binder's configuration, using this containing
     * {@link #computeKey(Object)} utility.
     *
     * @param key the target key object that is targeted
     * @see #computeKey(Object)
     * @see #isCaseSensitive()
     */
    Object get(Object key);

    /**
     * Computes the given {@code key} and testing and maybe modifies it,
     * so it is fitting this binder's configuration and finally returns
     * the computed key.
     * <p>If the given {@code key} is of type string, it will be
     * handled as a literal placeholder name using the here defined
     * configuration in {@link #isCaseSensitive()}.
     * <p>If the given {@code key} is of type number, it will be
     * handled as an integer index, that is validated and checked.
     * <p>If the given {@code key} is a class, the class's highest
     * superclass, except object, is determined and used to allow
     * contravariance.
     * <p>If {@code key} is anything but of type string, number or a
     * class, it is computed as is, meaning the returning key is equal
     * and indistinguishable to {@code key}.
     *
     * @param key the target to be computed
     * @throws NullPointerException if {@code key} is null
     */
    Object computeKey(Object key);

    /**
     * Returns all the keys of this binder.
     *
     * @return the keys of this binder
     */
    Collection<Object> keys();

    /**
     * Returns all values contained in this binder.
     *
     * @return the values of this binder
     */
    Collection<Object> values();

    /**
     * Returns all entries concurrently contained in this binder.
     *
     * @return all entry pairs of this map
     */
    Set<Map.Entry<Object, Object>> entries();

    /**
     * Returns true if no key or value has been bound yet.
     *
     * @return false if this binder contains at least one valid entry
     */
    boolean isEmpty();

    /**
     * Returns the concurrent size of this binder.
     *
     * @return the amount of entries concurrently contained
     */
    int size();

}
