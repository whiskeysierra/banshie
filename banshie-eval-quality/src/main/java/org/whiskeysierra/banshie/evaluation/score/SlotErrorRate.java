package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

final class SlotErrorRate implements Score {

    @Override
    public double calculate(Counts counts) {
        final double sum = counts.getTruePositives() + counts.getFalseNegatives();

        if (sum > 0) {
            final double errors = counts.getFalseNegatives() + counts.getFalsePositives();
            return errors / sum;
        } else {
            // cannot divide by zero, return error code
            return Double.NaN;
        }
    }

}
