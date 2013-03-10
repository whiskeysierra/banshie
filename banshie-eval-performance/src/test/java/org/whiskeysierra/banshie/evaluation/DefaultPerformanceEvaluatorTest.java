package org.whiskeysierra.banshie.evaluation;

import com.google.common.collect.ImmutableMap;
import com.google.inject.util.Providers;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public final class DefaultPerformanceEvaluatorTest {

    private void run(String name) {
        final Map<Dimension, Calculator> calculators = ImmutableMap.of(
            Dimension.CPU_TIME, new CpuUsageCalculator(),
            Dimension.MEMORY_CONSUMPTION, new MemoryUsageCalculator(),
            Dimension.TIME, new TimeCalculator()
        );

        final PerformanceEvaluator unit = new DefaultPerformanceEvaluator(
            Providers.of(new LogFileProcessor(calculators, new ObjectMapper())));

        final Map<Dimension, Value> values = unit.evaluate(new File("src/test/resources/" + name));

        Assert.assertFalse(values.isEmpty());

        for (Dimension dimension : Dimension.values()) {
            final Value value = values.get(dimension);

            if (value == null) continue;

            System.out.println(dimension.name() + ": " + value);
        }

        System.out.println();
    }

    @Test
    public void opennlp() {
        System.out.println("Apache OpenNLP");
        run("opennlp.log");
    }

    @Test
    public void corenlp() {
        System.out.println("Stanford CoreNLP");
        run("corenlp.log");
    }

}
