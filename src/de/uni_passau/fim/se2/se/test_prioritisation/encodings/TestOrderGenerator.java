package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

import java.util.Random;

public class TestOrderGenerator implements EncodingGenerator<TestOrder> {

    private final Random randomGenerator;
    private final Mutation<TestOrder> mutationOperator;
    private final int totalTestCases;

    public TestOrderGenerator(Random random, Mutation<TestOrder> mutation, int testCases) {
        this.randomGenerator = random;
        this.mutationOperator = mutation;
        this.totalTestCases = testCases;
    }

    @Override
    public TestOrder get() {
        int[] testOrder = generateSequentialOrder();

        shuffleOrder(testOrder);

        return new TestOrder(mutationOperator, testOrder);
    }

    private int[] generateSequentialOrder() {
        int[] order = new int[totalTestCases];
        for (int index = 0; index < totalTestCases; index++) {
            order[index] = index;
        }
        return order;
    }

    private void shuffleOrder(int[] order) {
        for (int i = totalTestCases - 1; i > 0; i--) {
            int randomIndex = randomGenerator.nextInt(i + 1);
            swap(order, i, randomIndex);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
