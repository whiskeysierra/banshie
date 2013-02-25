package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

/**
 * Also referred to as the True Positive Rate or Sensitivity.
 */
final class Recall implements Score {

    @Override
    public double calculate(Counts counts) {
        final double sum = counts.getTruePositives() + counts.getFalseNegatives();

        if (sum > 0) {
            return counts.getTruePositives() / sum;
        } else {
            // cannot divide by zero, return error code
            return Double.NaN;
        }
    }

}