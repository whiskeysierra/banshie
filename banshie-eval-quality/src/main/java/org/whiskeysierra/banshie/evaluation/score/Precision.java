package org.whiskeysierra.banshie.evaluation.score;

import com.google.inject.Inject;
import org.whiskeysierra.banshie.evaluation.Span;
import org.whiskeysierra.banshie.evaluation.counter.Counter;

import java.util.List;

final class Precision implements Score {

    private final Counter counter;

    private float selected;
    private float truePositive;

    @Inject
    Precision(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void update(List<Span> references, List<Span> predictions) {
        truePositive += counter.count(references, predictions);
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