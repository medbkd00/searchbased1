package de.uni_passau.fim.se2.se.test_prioritisation.mutations;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;

import java.util.Random;

public class ShiftToBeginningMutation implements Mutation<TestOrder> {

    private final Random randomizer;

    public ShiftToBeginningMutation(Random random) {
        this.randomizer = random;
    }

    @Override
    public TestOrder apply(TestOrder encoding) {
        int numberOfTests = encoding.size();

        int randomIndex = randomizer.nextInt(numberOfTests);

        int testToMove = encoding.getPositions()[randomIndex];

        int[] updatedOrder = createShiftedOrder(encoding.getPositions(), randomIndex, testToMove);

        return new TestOrder(encoding.getMutation(), updatedOrder);
    }

    private int[] createShiftedOrder(int[] originalOrder, int targetIndex, int valueToMove) {
        int[] newOrder = new int[originalOrder.length];

        newOrder[0] = valueToMove;
        int currentIndex = 1;

        for (int value : originalOrder) {
            if (value != valueToMove) {
                newOrder[currentIndex++] = value;
            }
        }

        return newOrder;
    }
}
