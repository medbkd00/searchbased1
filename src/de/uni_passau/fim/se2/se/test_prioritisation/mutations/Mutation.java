package de.uni_passau.fim.se2.se.test_prioritisation.mutations;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;

import java.util.function.UnaryOperator;

/**
 * A mutation is an elementary transformation that can be applied to an encoding to generate a new encoding.
 * Mutations are used in search algorithms to explore the solution space.
 *
 * @param <E> the type of the solution encoding that this mutation operates on
 */
public interface Mutation<E extends Encoding<E>> extends UnaryOperator<E> {

    /**
     * Applies the mutation to the given encoding by performing an elementary transformation.
     *
     * @param encoding the encoding to apply the mutation to
     * @return the new encoding generated by the mutation
     * @throws NullPointerException if {@code null} is given
     */
    @Override
    E apply(final E encoding);

}
