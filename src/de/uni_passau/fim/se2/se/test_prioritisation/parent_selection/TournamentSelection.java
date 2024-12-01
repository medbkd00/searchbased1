package de.uni_passau.fim.se2.se.test_prioritisation.parent_selection;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;

import java.util.*;

public class TournamentSelection implements ParentSelection<TestOrder> {

    private final int selectionSize;
    private final APLC fitnessEvaluator;
    private final Random randomizer;

    public TournamentSelection(int size, APLC fitnessFunction, Random random) {
        if (size <= 0 || fitnessFunction == null || random == null) {
            throw new IllegalArgumentException("Invalid arguments for tournament selection.");
        }
        this.selectionSize = size;
        this.fitnessEvaluator = fitnessFunction;
        this.randomizer = random;
    }

    public TournamentSelection(APLC fitnessFunction, Random random) {
        this(5, fitnessFunction, random);
    }

    @Override
    public TestOrder selectParent(List<TestOrder> population) {
        if (population == null || population.isEmpty()) {
            throw new IllegalArgumentException("Population cannot be null or empty.");
        }

        List<TestOrder> candidates = pickRandomSubset(population);

        return findBestCandidate(candidates);
    }

    private List<TestOrder> pickRandomSubset(List<TestOrder> population) {
        List<TestOrder> subset = new ArrayList<>();
        for (int i = 0; i < selectionSize; i++) {
            subset.add(population.get(randomizer.nextInt(population.size())));
        }
        return subset;
    }

    private TestOrder findBestCandidate(List<TestOrder> candidates) {
        return candidates.stream()
                .max(Comparator.comparingDouble(fitnessEvaluator::applyAsDouble))
                .orElseThrow(() -> new IllegalStateException("No candidates available."));
    }
}
