package de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;

public final class APLC implements FitnessFunction<TestOrder> {

    private final boolean[][] lineCoverageMatrix;

    public APLC(boolean[][] coverageMatrix) {
        if (coverageMatrix == null || coverageMatrix.length == 0 || coverageMatrix[0].length == 0) {
            throw new IllegalArgumentException("Invalid coverage matrix provided.");
        }
        this.lineCoverageMatrix = coverageMatrix;
    }

    @Override
    public double applyAsDouble(TestOrder testOrder) {
        if (testOrder == null) {
            throw new NullPointerException("Test order cannot be null.");
        }

        int totalTests = lineCoverageMatrix.length;
        int totalLines = lineCoverageMatrix[0].length;

        int[] firstCoverageIndices = computeFirstCoverageIndices(testOrder.getPositions(), totalLines);

        int penaltySum = calculatePenaltySum(firstCoverageIndices, totalTests);

        return calculateAPLCValue(totalTests, totalLines, penaltySum);
    }

    private int[] computeFirstCoverageIndices(int[] testOrder, int totalLines) {
        int[] indices = new int[totalLines];
        for (int i = 0; i < totalLines; i++) {
            indices[i] = -1;
        }

        for (int testIndex = 0; testIndex < testOrder.length; testIndex++) {
            int test = testOrder[testIndex];
            for (int line = 0; line < totalLines; line++) {
                if (lineCoverageMatrix[test][line] && indices[line] == -1) {
                    indices[line] = testIndex + 1;
                }
            }
        }
        return indices;
    }

    private int calculatePenaltySum(int[] firstCoverageIndices, int totalTests) {
        int sum = 0;
        for (int index : firstCoverageIndices) {
            sum += (index == -1) ? (totalTests + 1) : index;
        }
        return sum;
    }

    private double calculateAPLCValue(int totalTests, int totalLines, int penaltySum) {
        return 1.0 - ((double) penaltySum / (totalTests * totalLines)) + (0.5 / totalTests);
    }

    @Override
    public double maximise(TestOrder encoding) {
        return applyAsDouble(encoding);
    }

    @Override
    public double minimise(TestOrder encoding) {
        return -applyAsDouble(encoding);
    }
}
