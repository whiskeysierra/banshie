package org.whiskeysierra.banshie.evaluation.counter;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.whiskeysierra.banshie.evaluation.Span;

import java.util.List;
import java.util.Set;

final class DefaultCounter implements Counter {

    @Override
    public Counts count(List<Span> references, List<Span> predictions) {
        final Set<Span> truePositives = Sets.newHashSet();
        final Set<Span> falsePositives = Sets.newHashSet(predictions);
        final Set<Span> falseNegatives = Sets.newHashSet(references);

        loop:
        for (Span prediction : predictions) {
            for (Span reference : references) {
                final boolean equalTypes = Objects.equal(reference.getType(), prediction.getType());
                if (equalTypes && reference.overlap(prediction)) {
                    truePositives.add(reference);
                    falsePositives.remove(prediction);
                    falseNegatives.remove(reference);

                    continue loop;
                }
            }
        }

        return new DefaultCounts(
            truePositives.size(),
            falsePositives.size(),
            falseNegatives.size()
        );
    }

}
