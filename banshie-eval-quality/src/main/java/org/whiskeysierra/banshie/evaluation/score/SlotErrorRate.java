package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.Span;
import org.whiskeysierra.banshie.evaluation.counter.Counts;

import java.util.List;

final class SlotErrorRate implements Score {

    private float target;

    @Override
    public void update(List<Span> references, List<Span> predictions, Counts counts) {
        target += references.size();

        // S+D+I/N
    }

    @Override
    public float getValue() {
        return Float.NaN;
    }

    @Override
    public String toString() {
        return "Slot error rate: " + getValue();
    }

}
