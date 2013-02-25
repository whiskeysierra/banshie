package org.whiskeysierra.banshie.evaluation.counter;

import org.whiskeysierra.banshie.evaluation.Span;

import java.util.List;

final class DefaultCounter implements Counter {

    @Override
    public Counts count(List<Span> references, List<Span> predictions) {
        int truePositives = 0;
        int falsePositives = predictions.size();
        int falseNegatives = references.size();

        loop: for (Span prediction : predictions) {
            for (Span reference : references) {
                if (reference.overlap(prediction)) {
                    truePositives++;
                    falsePositives--;
                    falseNegatives--;
                    continue loop;
                }
            }
        }

        return new DefaultCounts(truePositives, falsePositives, falseNegatives);
    }

}
