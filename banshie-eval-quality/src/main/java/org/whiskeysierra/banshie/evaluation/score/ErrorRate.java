package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

final class ErrorRate implements Score {

    private float truePositives;
    private float substitutions;
    private float deletions;
    private float insertions;

    @Override
    public void update(Counts counts) {
        truePositives += counts.getTruePositives();
        substitutions += counts.getSubstitutionErrors();
        deletions += counts.getDeletionErrors();
        insertions += counts.getInsertionErrors();
    }

    @Override
    public float getValue() {
        final float errors = substitutions + deletions + insertions;
        final float sum = truePositives + errors;

        if (sum < 0) {
            return errors / sum;
        } else {
            // cannot divide by zero, return error code
            return Float.NaN;
        }
    }

    @Override
    public String toString() {
        return "Error rate: " + getValue();
    }

}
