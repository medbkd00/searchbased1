package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

/**
 * Implements a random search space exploration. To this end, a number of solutions are sampled in a
 * random fashion and the best encountered solution is returned.
 *
 * @param <E> the type of encoding
 */
public final class RandomSearch<E extends Encoding<E>> implements SearchAlgorithm<E> {
    private final StoppingCondition stoppingCondition;
    private final EncodingGenerator<E> encodingGenerator;
    private final FitnessFunction<E> fitnessFunction;

    public RandomSearch(
            final StoppingCondition stoppingCondition,
            final EncodingGenerator<E> encodingGenerator,
            final FitnessFunction<E> fitnessFunction) {
        if (stoppingCondition == null || encodingGenerator == null || fitnessFunction == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        this.stoppingCondition = stoppingCondition;
        this.encodingGenerator = encodingGenerator;
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public E findSolution() {
        E bestSolution = null;
        double bestFitness = Double.NEGATIVE_INFINITY;

        stoppingCondition.notifySearchStarted();  // Notify the stopping condition that search has started

        while (!stoppingCondition.searchMustStop()) {
            E candidate = encodingGenerator.get();  // Use get() method from EncodingGenerator
            double fitness = fitnessFunction.maximise(candidate);

            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestSolution = candidate;
            }

            stoppingCondition.notifyFitnessEvaluation();  // Notify after each fitness evaluation
        }

        return bestSolution;
    }

    @Override
    public StoppingCondition getStoppingCondition() {
        return stoppingCondition;
    }
}

