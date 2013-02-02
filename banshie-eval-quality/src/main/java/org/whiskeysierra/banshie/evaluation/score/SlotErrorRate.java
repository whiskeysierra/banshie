package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

final class SlotErrorRate implements Score {

    private float truePositives;
    private float falseNegatives;
    private float falsePositives;

    @Override
    public void update(Counts counts) {
        truePositives += counts.getTruePositives();
        falseNegatives += counts.getFalseNegatives();
        falsePositives += counts.getFalsePositives();
    }

    @Override
    public float getValue() {
        final float sum = truePositives + falseNegatives;

        if (sum > 0) {
            final float errors = falseNegatives + falsePositives;
            return errors / sum;
        } else {
            // cannot divide by zero, return error code
            return Float.NaN;
        }
    }

    @Override
    public String toString() {
        return "Slot error rate: " + getValue();
    }

}
