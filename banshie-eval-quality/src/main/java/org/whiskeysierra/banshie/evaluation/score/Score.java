package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

public interface Score {

    void update(Counts counts);

    float getValue();

}
