package org.whiskeysierra.banshie.evaluation;

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.io.File;
import java.util.Map;

final class DefaultPerformanceEvaluator implements PerformanceEvaluator {

    private final Provider<StatefulPerformanceEvaluator> provider;

    @Inject
    DefaultPerformanceEvaluator(Provider<StatefulPerformanceEvaluator> provider) {
        this.provider = provider;
    }

    @Override
    public Map<Dimension, Value> evaluate(File logFile) {
        return provider.get().evaluate(logFile);
    }

}
