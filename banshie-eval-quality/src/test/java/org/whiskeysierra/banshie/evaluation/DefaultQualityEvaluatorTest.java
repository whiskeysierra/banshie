package org.whiskeysierra.banshie.evaluation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public final class DefaultQualityEvaluatorTest {

    private void run(String name) {
        final Injector injector = Guice.createInjector(new QualityEvaluationModule());
        final QualityEvaluator unit = injector.getInstance(QualityEvaluator.class);

        final File resources = new File("src/test/resources");
        final File reference = new File(resources, "reference.xml");
        final File prediction = new File(resources, name);

        final Map<Dimension, Value> values = unit.evaluate(reference, prediction);

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
        run("opennlp.xml");
    }

    @Test
    public void corenlp() {
        System.out.println("Stanford CoreNLP");
        run("corenlp.xml");
    }

}
