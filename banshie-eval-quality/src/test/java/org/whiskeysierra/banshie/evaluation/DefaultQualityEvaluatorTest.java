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

        System.out.println(values);
    }

    @Test
    public void opennlp() {
        run("opennlp.xml");
    }

    @Test
    public void corenlp() {
        run("corenlp.xml");
    }

}
