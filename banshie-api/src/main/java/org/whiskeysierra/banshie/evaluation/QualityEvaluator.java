package org.whiskeysierra.banshie.evaluation;

import java.io.File;
import java.util.Map;

public interface QualityEvaluator {

    Map<Dimension, Value> evaluate(File reference, File prediction);

}
