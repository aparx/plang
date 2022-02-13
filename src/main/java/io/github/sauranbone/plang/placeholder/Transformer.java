package io.github.sauranbone.plang.placeholder;

import java.util.function.Function;

/**
 * Functional interface that accepts a single parameter and returns any
 * object.
 * <p>This interface is usually called by {@link Placeholder}, which
 * transforms receiving data into an output, that is then appended to the
 * output within any processor.
 * <p>This functional interface is relatively equal to {@link Function},
 * but this interface requires other namings which is why the transformer
 * does not inherit {@link Function}.
 *
 * @author Vinzent Zeband
 * @version 23:34 CET, 12.02.2022
 * @see Function
 * @since 1.0
 */
@FunctionalInterface
public interface Transformer<T> {

    /**
     * Transforms the receiving {@code data} into an returning object that
     * can be used to replace a placeholder signature in literal text.
     *
     * @param data the receiving data that shall be transformed
     * @return the displayable data, that might replace the corresponding
     * placeholder or transformable value itself in literal context
     */
    Object transform(T data);

}
