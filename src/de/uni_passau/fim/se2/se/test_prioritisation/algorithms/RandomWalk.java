package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

import java.util.Random;

/**
 * Implements a random walk through the search space.
 *
 * @param <E> the type of encoding
 */
public final class RandomWalk<E extends Encoding<E>> implements SearchAlgorithm<E> {
    private final StoppingCondition stoppingCondition;
    private final EncodingGenerator<E> encodingGenerator;
    private final FitnessFunction<E> fitnessFunction;

    public RandomWalk(
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
        E currentSolution = encodingGenerator.get();  // Use get() instead of generate()
        double bestFitness = fitnessFunction.maximise(currentSolution);
        E bestSolution = currentSolution;

        stoppingCondition.notifySearchStarted();  // Notify the stopping condition that search has started

        while (!stoppingCondition.searchMustStop()) {  // Use searchMustStop() instead of shouldStop()
            E neighbor = currentSolution.mutate(new Random(), 1); // Mutate slightly
            double fitness = fitnessFunction.maximise(neighbor);

            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestSolution = neighbor;
            }

            currentSolution = neighbor;  // Take a random step
            stoppingCondition.notifyFitnessEvaluation();  // Notify after each fitness evaluation
        }

        return bestSolution;
    }

    @Override
    public StoppingCondition getStoppingCondition() {
        return stoppingCondition;
    }
}