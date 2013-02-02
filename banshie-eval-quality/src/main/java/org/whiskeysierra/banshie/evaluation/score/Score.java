package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.Span;
import org.whiskeysierra.banshie.evaluation.counter.Counts;

import java.util.List;

public interface Score {

    void update(List<Span> references, List<Span> predictions, Counts counts);

    float getValue();

}
