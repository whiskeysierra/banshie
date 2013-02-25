package org.whiskeysierra.banshie.evaluation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.whiskeysierra.banshie.evaluation.counter.Counter;
import org.whiskeysierra.banshie.evaluation.counter.Counts;
import org.whiskeysierra.banshie.evaluation.score.Score;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class DefaultQualityEvaluator implements QualityEvaluator {

    private final DocumentBuilderFactory factory;
    private final Counter counter;
    private final Map<Dimension, Score> scores;

    @Inject
    DefaultQualityEvaluator(DocumentBuilderFactory factory, Counter counter,
        Map<Dimension, Score> scores) {
        this.factory = factory;
        this.counter = counter;
        this.scores = scores;
    }

    private List<Span> readSpans(File file) {
        final List<Span> spans = Lists.newArrayList();

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();

            final Document document = builder.parse(file);
            final Element root = document.getDocumentElement();
            final NodeList nodes = root.getElementsByTagName("span");

            for (int i = 0; i < nodes.getLength(); i++) {
                final Element element = Element.class.cast(nodes.item(i));

                final Span span = new Span();

                span.setType(element.getAttribute("type"));
                span.setStart(Integer.parseInt(element.getAttribute("start")));
                span.setEnd(Integer.parseInt(element.getAttribute("end")));
                span.setValue(element.getTextContent());

                spans.add(span);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        } catch (SAXException e) {
            throw new IllegalStateException(e);
        }

        return spans;
    }

    @Override
    public Map<Dimension, Value> evaluate(File reference, File prediction) {
        final List<Span> references = readSpans(reference);
        final List<Span> predictions = readSpans(prediction);

        final Counts counts = counter.count(references, predictions);
        final Map<Dimension, Value> values = Maps.newHashMap();

        for (Entry<Dimension, Score> entry : scores.entrySet()) {
            values.put(entry.getKey(), new SimpleValue(entry.getValue().calculate(counts)));
        }

        return values;
    }

}
