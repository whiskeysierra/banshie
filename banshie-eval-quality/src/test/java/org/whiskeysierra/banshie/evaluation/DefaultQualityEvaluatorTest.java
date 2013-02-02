package org.whiskeysierra.banshie.evaluation;

import org.junit.Test;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public final class DefaultQualityEvaluatorTest {

    @Test
    public void test() {
        final QualityEvaluator unit = new DefaultQualityEvaluator(DocumentBuilderFactory.newInstance());
        final File resources = new File("src/test/resources");
        unit.evaluate(new File(resources, "reference.xml"), new File(resources, "prediction.xml"));
    }

}
