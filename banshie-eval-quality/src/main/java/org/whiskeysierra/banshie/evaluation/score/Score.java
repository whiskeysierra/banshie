package org.whiskeysierra.banshie.evaluation.score;

import org.whiskeysierra.banshie.evaluation.counter.Counts;

public interface Score {

    double calculate(Counts counts);

}
