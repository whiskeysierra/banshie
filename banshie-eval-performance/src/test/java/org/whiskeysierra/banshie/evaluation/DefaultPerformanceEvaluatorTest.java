package org.whiskeysierra.banshie.evaluation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.inject.util.Providers;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public final class DefaultPerformanceEvaluatorTest {

    @Test
    public void test() {
        final Map<Dimension, Calculator> calculators = ImmutableMap.of(
            Dimension.CPU_USAGE, new CpuUsageCalculator(),
            Dimension.MEMORY_USAGE, new MemoryUsageCalculator(),
            Dimension.TIME, new TimeCalculator()
        );

        final PerformanceEvaluator unit = new DefaultPerformanceEvaluator(
            Providers.of(new LogFileProcessor(calculators, new ObjectMapper())));

        final Map<Dimension, Value> results = unit.evaluate(new File("src/test/resources/events.log"));

        // TODO do real testing!
        System.out.println(results);

    }

}
