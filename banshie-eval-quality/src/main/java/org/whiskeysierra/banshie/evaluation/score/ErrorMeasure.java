package org.whiskeysierra.banshie.evaluation.score;

import com.google.inject.Inject;
import org.whiskeysierra.banshie.evaluation.Span;
import org.whiskeysierra.banshie.evaluation.counter.Counts;

import java.util.List;

final class ErrorMeasure implements Score {

    private final FMeasure measure;

    @Inject
    ErrorMeasure(FMeasure measure) {
        this.measure = measure;
    }

    @Override
    public void update(List<Span> references, List<Span> predictions, Counts counts) {
        measure.update(references, predictions, counts);
    }

    @Override
    public float getValue() {
        return 1f - measure.getValue();
    }

    @Override
    public String toString() {
        return "Error measure: " + getValue();
    }

}
