package org.whiskeysierra.banshie.evaluation.counter;

import org.whiskeysierra.banshie.evaluation.Span;

import java.util.List;

public interface Counter {

    Counts count(List<Span> references, List<Span> predictions);

}
