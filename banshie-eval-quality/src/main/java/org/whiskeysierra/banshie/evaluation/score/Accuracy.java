package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.Span;

import java.util.List;

final class Accuracy implements Score {

    @Override
    public void update(List<Span> references, List<Span> predictions) {

    }

    @Override
    public float getValue() {
        return Float.NaN;
    }

    @Override
    public String toString() {
        return "Accuracy: " + getValue();
    }

}
