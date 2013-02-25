package org.whiskeysierra.banshie.evaluation.score;

import com.google.inject.Inject;
import org.whiskeysierra.banshie.evaluation.counter.Counts;

final class FMeasure implements Score {

    private final Precision precision;
    private final Recall recall;

    @Inject
    FMeasure(Precision precision, Recall recall) {
        this.precision = precision;
        this.recall = recall;
    }

    @Override
    public double calculate(Counts counts) {
        final double p = precision.calculate(counts);
        final double r = recall.calculate(counts);

        final double sum = p + r;

        if (sum > 0) {
            return 2 * (p * r) / sum;
        } else {
            // cannot divide by zero, return error code
            return Double.NaN;
        }
    }

}