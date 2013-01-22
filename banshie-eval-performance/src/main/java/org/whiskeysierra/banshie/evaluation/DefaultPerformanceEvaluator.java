package org.whiskeysierra.banshie.evaluation;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.io.File;
import java.io.IOException;
import java.util.Map;

final class DefaultPerformanceEvaluator implements PerformanceEvaluator {

    private final Provider<MapLineProcessor> provider;

    @Inject
    DefaultPerformanceEvaluator(Provider<MapLineProcessor> provider) {
        this.provider = provider;
    }

    @Override
    public Map<Dimension, Value> evaluate(File logFile) {
        try {
            return Files.readLines(logFile, Charsets.UTF_8, provider.get());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
