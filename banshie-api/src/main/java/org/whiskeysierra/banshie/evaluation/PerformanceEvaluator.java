package org.whiskeysierra.banshie.evaluation;

import java.io.File;
import java.util.Map;

public interface PerformanceEvaluator {

    Map<Dimension, Value> evaluate(File logFile);

}
