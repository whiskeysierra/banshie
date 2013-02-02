package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.Span;
import org.whiskeysierra.banshie.evaluation.counter.Counts;

import java.util.List;

final class Precision implements Score {

    private float selected;
    private float truePositive;

    @Override
    public void update(List<Span> references, List<Span> predictions, Counts counts) {
        truePositive += counts.getTruePositives();
        selected += predictions.size();
    }

    @Override
    public float getValue() {
        return selected > 0 ? truePositive / selected : Float.NaN;
    }

    @Override
    public String toString() {
        return "Precision: " + getValue();
    }

}