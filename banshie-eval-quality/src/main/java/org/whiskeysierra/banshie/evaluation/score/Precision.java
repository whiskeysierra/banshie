package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

/**
 * Also referred to as the Positive Predictive Value.
 */
final class Precision implements Score {

    @Override
    public double calculate(Counts counts) {
        final double sum = counts.getTruePositives() + counts.getFalsePositives();

        if (sum > 0) {
            return counts.getTruePositives() / sum;
        } else {
            // cannot divide by zero, return error code
            return Double.NaN;
        }
    }

}