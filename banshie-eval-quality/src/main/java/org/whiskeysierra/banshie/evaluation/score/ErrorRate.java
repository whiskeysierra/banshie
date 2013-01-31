package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.Span;

import java.util.List;

final class ErrorRate implements Score {

    @Override
    public void update(List<Span> references, List<Span> predictions) {
        // S+D+I/C+S+D+I
    }

    @Override
    public float getValue() {
        return Float.NaN;
    }

    @Override
    public String toString() {
        return "Error rate: " + getValue();
    }

}
