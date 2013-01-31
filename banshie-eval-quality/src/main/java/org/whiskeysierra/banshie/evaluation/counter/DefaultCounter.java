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

        final List<Span> r = Lists.newArrayList(references);
        final List<Span> p = Lists.newArrayList(predictions);

        for (Span reference : references) {
            for (Span prediction : predictions) {
                if (similarity.similar(reference.getValue(), prediction.getValue())) {
                    truePositives++;

                    r.remove(reference);
                    p.remove(prediction);
                }
            }
        }

        int falsePositives = p.size();
        int falseNegatives = r.size();

        return new DefaultCounts(truePositives, falsePositives, falseNegatives);
    }

}
