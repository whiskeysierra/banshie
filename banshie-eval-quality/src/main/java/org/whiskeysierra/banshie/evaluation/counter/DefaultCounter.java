package org.whiskeysierra.banshie.evaluation.counter;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.whiskeysierra.banshie.evaluation.Span;
import org.whiskeysierra.banshie.evaluation.similarity.Similarity;

import java.util.List;

final class DefaultCounter implements Counter {

    private final Similarity similarity;

    @Inject
    DefaultCounter(Similarity similarity) {
        this.similarity = similarity;
    }

    @Override
    public Counts count(List<Span> references, List<Span> predictions) {
        int truePositives = 0;
        int falsePositives = predictions.size();
        int falseNegatives = references.size();

        // TODO associate references to predications!

        for (Span reference : references) {
            for (Span prediction : predictions) {
                // TODO use span location
                // TODO use overlap mode?!
                if (similarity.similar(reference.getValue(), prediction.getValue())) {
                    truePositives++;
                    falsePositives--;
                    falseNegatives--;
                }
            }
        }


        return new DefaultCounts(truePositives, falsePositives, falseNegatives);
    }

}
