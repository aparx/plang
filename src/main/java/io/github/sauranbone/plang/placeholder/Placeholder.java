package io.github.sauranbone.plang.placeholder;

import io.github.sauranbone.plang.PlangBitmask;
import io.github.sauranbone.plang.map.DataBinder;
import jdk.nashorn.internal.objects.NativeUint8Array;

import static io.github.sauranbone.plang.placeholder.PlaceholderModifier.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Placeholder class containing methods to transform incoming data and
 * translating it to a resulting and displayable value.
 * <p>A placeholder is essentially like a dynamic variable that can be
 * used in literal text to point to a placeholder instance (like this)
 * using a specified syntax for it.  The literal placeholder will then be
 * replaced by the content of the pointed placeholder, thus resulting into
 * a replacement of a certain signature that triggers the placeholder that
 * is replaced by the content of the pointed placeholder instance.
 * <p>An example for the described above would be this:
 * <pre><code>
 *     String s = "Hello {playerName}";
 * </code></pre>
 * Whereas "{playerName}" is pointing to a placeholder named "playerName",
 * which then can be invoked and the string replaced using various methods.
 * The syntax in the given example is relatively easy, as it only requires
 * curly brackets to surround the target literal placeholder in order to
 * point to an actual placeholder object in memory.
 *
 * @author Vinzent Zeband
 * @version 23:34 CET, 12.02.2022
 * @since 1.0
 */
public abstract class Placeholder<T> implements Serializable, PlangBitmask, Transformer<T> {

    final String name;

    //The modifiers of the placeholder
    private int modifiers;

    //The T parameter type of the placeholder
    private final Class<T> target;

    /**
     * Allocates a new super message placeholder with the given
     * {@code name} as the callable name of the placeholder.
     *
     * @param name   the callable name of this placeholder
     * @param target the target class type of this placeholder, null for
     *               any type
     * @throws NullPointerException if {@code name} is null
     */
    protected Placeholder(String name, Class<T> target) {
        Objects.requireNonNull(name, "Name");
        this.name = name;
        this.target = target;
    }

    /**
     * Allocates a new message placeholder with given callable {@code name}
     * and a static value transformer returning {@code object}.
     *
     * @param name   the target name of the placeholder
     * @param object the target value that is returned whenever this
     *               {@code transform} method is called
     * @param <T>    the target type of the placeholder
     * @return the newly allocated placeholder instance
     * @throws NullPointerException if {@code name} is null
     */
    public static <T> Placeholder<T> of(final String name, final T object) {
        Placeholder<T> placeholder = createInstance(name, s -> object, null);
        placeholder.enable(STATIC | NULLABLE /* TODO may be redundant */);
        return placeholder;
    }

    /**
     * Allocates a new message placeholder with given callable {@code name}
     * and {@code transformer}, which accepts receiving data and transforms
     * it into literal content that can be any object.
     * <p>The given {@code type} is mostly used in connection to a
     * {@link DataBinder} implementation that has a class as a bound key
     * and the target value of it that can trigger this placeholder if the
     * literal placeholder name is pointing towards this instance.
     *
     * @param name        the target name of the placeholder
     * @param transformer the target transformer of the placeholder
     * @param type        the target data type that is {@code transformer}
     *                    accepts
     * @param <T>         the target type of the placeholder
     * @return the newly allocated placeholder instance
     * @throws NullPointerException if any argument is null
     */
    public static <T> Placeholder<T> of(final String name, final Transformer<T> transformer, Class<T> type) {
        Objects.requireNonNull(name, "Name");
        Objects.requireNonNull(transformer, "Transformer");
        return createInstance(name, transformer, type);
    }

    /**
     * Returns the callable name of this placeholder, that can be used in
     * literal text to point to this placeholder in order to be replaced
     * using this and the processors transformative utilities.
     *
     * @return the callable name of this placeholder
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns true if this placeholder has a target class and is not
     * static.
     *
     * @return false if this placeholder is static
     * @see #getAcceptingType()
     * @see #hasModifier(int)
     * @see PlaceholderModifier#STATIC
     */
    public final boolean isTransformative() {
        return target != null && !hasModifier(STATIC);
    }

    /**
     * Returns the nullable target class type that this placeholder can
     * accept using this transformative method.
     *
     * @return the target class type, {@code nullable}
     */
    public final Class<T> getAcceptingType() {
        return target;
    }

    /**
     * Returns true if this placeholder contains a static value.
     *
     * @return false if this placeholder is not static
     * @see PlaceholderModifier#STATIC
     * @see PlaceholderModifier#isStatic(int)
     */
    public synchronized final boolean isStatic() {
        return PlaceholderModifier.isStatic(modifiers);
    }

    /**
     * Returns true if this placeholder can accept a null value using this
     * transformative method and utility or is static.
     *
     * @return false if this placeholder is not nullable
     * @see PlaceholderModifier#NULLABLE
     * @see PlaceholderModifier#isNullable(int)
     */
    public synchronized final boolean isNullable() {
        return PlaceholderModifier.isStatic(modifiers);
    }

    /**
     * Returns the modifiers bitmask that contains information about this
     * placeholder, and how the placeholder should be handled in
     * processors.
     *
     * @return this placeholder's descriptive modifiers
     * @see PlaceholderModifier
     */
    @Override
    public synchronized final int getModifiers() {
        return modifiers;
    }

    /**
     * Updates this modifiers bitmask to the given {@code bitmask}.
     *
     * @param bitmask the new bitmask modifiers
     * @see PlaceholderModifier
     */
    @Override
    public synchronized final void setModifiers(int bitmask) {
        this.modifiers = bitmask;
    }

    /**
     * {@inheritDoc}
     * <p>The target {@code modifier} must be from any static accessible
     * constant declared in {@link PlaceholderModifier}, otherwise fatal
     * errors can occur.
     *
     * @param modifier the modifier to be updated
     * @param status   the target status of the {@code modifier}, true is
     *                 equivalent to enabled while false is disabled
     * @see #setModifiers(int)
     * @see PlaceholderModifier
     */
    @Override
    public synchronized final void setModifier(int modifier, boolean status) {
        PlangBitmask.super.setModifier(modifier, status);
    }

    /**
     * {@inheritDoc}
     * <p>The target {@code modifier} must be from any static accessible
     * constant declared in {@link PlaceholderModifier}, otherwise fatal
     * errors can occur.
     *
     * @param modifier the target modifier
     * @see #setModifiers(int)
     * @see PlaceholderModifier
     */
    @Override
    public final void enable(int modifier) {
        PlangBitmask.super.enable(modifier);
    }

    /**
     * {@inheritDoc}
     * <p>The target {@code modifier} must be from any static accessible
     * constant declared in {@link PlaceholderModifier}, otherwise fatal
     * errors can occur.
     *
     * @param modifier the target modifier
     * @see #setModifiers(int)
     * @see PlaceholderModifier
     */
    @Override
    public final void disable(int modifier) {
        PlangBitmask.super.disable(modifier);
    }

    private static <T> Placeholder<T> createInstance(final String name, final Transformer<T> transformer, Class<T> type) {
        Objects.requireNonNull(transformer);
        return new Placeholder<T>(name, type) {
            @Override
            public Object transform(T data) {
                return transformer.transform(data);
            }
        };
    }

}
