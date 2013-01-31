package org.whiskeysierra.banshie.evaluation.score;

import com.google.inject.Inject;
import org.whiskeysierra.banshie.evaluation.Span;
import org.whiskeysierra.banshie.evaluation.counter.Counter;

import java.util.List;

final class Recall implements Score {

    private final Counter counter;

    private float truePositives;
    private float target;

    @Inject
    Recall(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void update(List<Span> references, List<Span> predictions) {
        truePositives += counter.count(references, predictions);
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