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
    public void update(Counts counts) {
        precision.update(counts);
        recall.update(counts);
    }

    @Override
    public float getValue() {
        final float p = precision.getValue();
        final float r = recall.getValue();

        final float sum = p + r;

        if (sum > 0) {
            return 2 * (p * r) / sum;
        } else {
            // cannot divide by zero, return error code
            return Float.NaN;
        }
    }

    @Override
    public String toString() {
        return "F-Measure: " + getValue();
    }

}