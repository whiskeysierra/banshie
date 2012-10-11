package org.whiskeysierra.banshie;

import org.easymock.EasyMock;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.evaluation.Dimension;
import org.whiskeysierra.banshie.evaluation.PerformanceEvaluator;
import org.whiskeysierra.banshie.evaluation.QualityEvaluator;
import org.whiskeysierra.banshie.evaluation.Value;
import org.whiskeysierra.banshie.execution.Engine;
import org.whiskeysierra.banshie.execution.ExecutionResult;

import java.util.Map;

public class Usage {

    public void test() {
        // get from storages
        final Extractor extractor = EasyMock.createMock(Extractor.class);
        final Corpus corpus = EasyMock.createMock(Corpus.class);

        // via dependency injection
        final Engine engine = EasyMock.createMock(Engine.class);
        final PerformanceEvaluator performanceEvaluator = EasyMock.createMock(PerformanceEvaluator.class);
        final QualityEvaluator qualityEvaluator = EasyMock.createMock(QualityEvaluator.class);

        // do the actual work...
        final ExecutionResult result = engine.execute(extractor, corpus);
        final Map<Dimension, Value> map = performanceEvaluator.evaluate(result.getLogFile());
        final Map<Dimension, Value> m = qualityEvaluator.evaluate(corpus.getReference(), result.getOutput());

        // persist and visualize results...
    }

}
