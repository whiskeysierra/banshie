package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.Span;

import java.util.List;

public interface Score {

    void update(List<Span> references, List<Span> predictions);

    float getValue();

}
