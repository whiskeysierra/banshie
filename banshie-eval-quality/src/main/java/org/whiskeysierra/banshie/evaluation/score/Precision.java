package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

/**
 * Also referred to as the Positive Predictive Value.
 */
final class Precision implements Score {

    private float truePositives;
    private float falsePositives;

    @Override
    public void update(Counts counts) {
        truePositives += counts.getTruePositives();
        falsePositives += counts.getFalsePositives();
    }

    @Override
    public float getValue() {
        final float sum = truePositives + falsePositives;

        if (sum > 0) {
            return truePositives / sum;
        } else {
            // cannot divide by zero, return error code
            return Float.NaN;
        }
    }

    @Override
    public String toString() {
        return "Precision: " + getValue();
    }

}