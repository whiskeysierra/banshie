package org.whiskeysierra.banshie.evaluation.counter;

final class DefaultCounts implements Counts {

    private final int truePositives;
    private final int falsePositives;
    private final int falseNegatives;

    DefaultCounts(int truePositives, int falsePositives, int falseNegatives) {
        this.truePositives = truePositives;
        this.falsePositives = falsePositives;
        this.falseNegatives = falseNegatives;
    }

    @Override
    public int getTruePositives() {
        return truePositives;
    }

    @Override
    public int getFalsePositives() {
        return falsePositives;
    }

    @Override
    public int getFalseNegatives() {
        return falseNegatives;
    }

}
