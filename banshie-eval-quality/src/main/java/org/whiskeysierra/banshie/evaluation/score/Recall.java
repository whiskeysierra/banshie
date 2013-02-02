package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

/**
 * Also referred to as the True Positive Rate or Sensitivity.
 */
final class Recall implements Score {

    private float truePositives;
    private float falseNegatives;

    @Override
    public void update(Counts counts) {
        truePositives += counts.getTruePositives();
        falseNegatives += counts.getFalseNegatives();
    }

    @Override
    public float getValue() {
        final float sum = truePositives + falseNegatives;

        if (sum > 0) {
            return truePositives / sum;
        } else {
            // cannot divide by zero, return error code
            return Float.NaN;
        }
    }

    @Override
    public String toString() {
        return "Recall: " + getValue();
    }

}