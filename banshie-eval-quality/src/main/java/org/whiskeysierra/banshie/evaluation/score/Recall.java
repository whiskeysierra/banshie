package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.Span;
import org.whiskeysierra.banshie.evaluation.counter.Counts;

import java.util.List;

final class Recall implements Score {

    private float truePositives;
    private float target;

    @Override
    public void update(List<Span> references, List<Span> predictions, Counts counts) {
        truePositives += counts.getTruePositives();
        target += references.size();
    }

    @Override
    public float getValue() {
        return target > 0 ? truePositives / target : Float.NaN;
    }

    @Override
    public String toString() {
        return "Recall: " + getValue();
    }

}