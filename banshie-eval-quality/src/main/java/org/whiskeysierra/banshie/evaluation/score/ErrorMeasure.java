package org.whiskeysierra.banshie.evaluation.score;

import com.google.inject.Inject;
import org.whiskeysierra.banshie.evaluation.counter.Counts;

final class ErrorMeasure implements Score {

    private final FMeasure measure;

    @Inject
    ErrorMeasure(FMeasure measure) {
        this.measure = measure;
    }

    @Override
    public double calculate(Counts counts) {
        return 1d - measure.calculate(counts);
    }

}
