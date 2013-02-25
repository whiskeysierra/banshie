package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

final class ErrorPerResponseFill implements Score {

    @Override
    public double calculate(Counts counts) {
        final double errors = counts.getFalseNegatives() + counts.getFalsePositives();
        final double sum = counts.getTruePositives() + errors;

        if (sum < 0) {
            return errors / sum;
        } else {
            // cannot divide by zero, return error code
            return Double.NaN;
        }
    }

}
