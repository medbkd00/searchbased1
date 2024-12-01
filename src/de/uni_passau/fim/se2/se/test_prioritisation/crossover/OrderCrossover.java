package de.uni_passau.fim.se2.se.test_prioritisation.crossover;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;

import java.util.*;

public class OrderCrossover implements Crossover<TestOrder> {

    /**
     * The internal source of randomness.
     */
    private final Random random;

    /**
     * Creates a new order crossover operator.
     *
     * @param random the internal source of randomness
     */
    public OrderCrossover(final Random random) {
        this.random = random;
    }

    /**
     * Combines two parent encodings to create a new offspring encoding using the order crossover operation.
     * The order crossover corresponds to a two-point crossover where the section between two random indices is copied
     * from the first parent and the remaining alleles are added in the order they appear in the second parent.
     * The resulting children must correspond to a valid test order encoding of size n that represents a permutation of tests
     * where each test value in the range [0, n-1] appears exactly once.
     *
     * @param parent1 the first parent encoding
     * @param parent2 the second parent encoding
     * @return the offspring encoding
     */
    @Override
    public TestOrder apply(TestOrder parent1, TestOrder parent2) {
        int totalSize = parent1.size();

        // Generate two distinct crossover points
        int start = random.nextInt(totalSize);
        int end = random.nextInt(totalSize);

        // Ensure start is less than or equal to end
        if (start > end) {
            int swap = start;
            start = end;
            end = swap;
        }

        // Initialize the offspring array with default invalid values
        int[] childPositions = new int[totalSize];
        Arrays.fill(childPositions, -1);

        // Inherit the segment between the crossover points from the first parent
        for (int idx = start; idx <= end; idx++) {
            childPositions[idx] = parent1.getPositions()[idx];
        }

        // Fill the remaining positions with elements from the second parent
        int childIdx = 0;
        for (int element : parent2.getPositions()) {
            if (containsElement(childPositions, element)) {
                continue;
            }
            // Find the next empty position outside the copied segment
            while (childIdx >= start && childIdx <= end) {
                childIdx++;
            }
            childPositions[childIdx++] = element;
        }

        // Return the newly created test order
        return new TestOrder(parent1.getMutation(), childPositions);
    }

    /**
     * Utility method to check if an element exists in the array.
     *
     * @param array   the array to search
     * @param element the element to find
     * @return true if the element exists, false otherwise
     */
    private boolean containsElement(int[] array, int element) {
        for (int value : array) {
            if (value == element) {
                return true;
            }
        }
        return false;
    }

}
