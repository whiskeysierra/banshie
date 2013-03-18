package org.whiskeysierra.banshie.evaluation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public final class DefaultQualityEvaluatorTest {

    private void run(String name, String referenceName) {
        final Injector injector = Guice.createInjector(new QualityEvaluationModule());
        final QualityEvaluator unit = injector.getInstance(QualityEvaluator.class);

        final File resources = new File("src/test/resources");
        final File reference = new File(resources, referenceName);
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

    private void run(String name) {
        run(name, "reference.xml");
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

    @Test
    public void versus() {
        System.out.println("Apache OpenNLP vs. Stanford CoreNLP");
        run("opennlp-email.xml", "corenlp-email.xml");
    }

    @Test
    public void versus2() {
        System.out.println("Stanford CoreNLP vs. Apache OpenNLP");
        run("corenlp-email.xml", "opennlp-email.xml");
    }

}
