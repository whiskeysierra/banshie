package org.whiskeysierra.banshie.evaluation;

import com.google.common.collect.Maps;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.whiskeysierra.banshie.evaluation.counter.Counter;
import org.whiskeysierra.banshie.evaluation.score.Score;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Map;

public final class DefaultQualityEvaluatorTest {

    @Test
    public void test() {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final Counter counter = EasyMock.createNiceMock(Counter.class);
        final Map<Dimension, Score> scores = Maps.newHashMap();

        final QualityEvaluator unit = new DefaultQualityEvaluator(factory, counter, scores);

        final File resources = new File("src/test/resources");
        final File reference = new File(resources, "reference.xml");
        final File prediction = new File(resources, "prediction.xml");

        final Map<Dimension, Value> values = unit.evaluate(reference, prediction);

        Assert.assertTrue(values.isEmpty());
    }

}
