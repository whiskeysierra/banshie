package org.whiskeysierra.banshie.evaluation;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

final class DefaultQualityEvaluator implements QualityEvaluator {

    final DocumentBuilderFactory factory;

    @Inject
    DefaultQualityEvaluator(DocumentBuilderFactory factory) {
        this.factory = factory;
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
        // TODO count insertion, deletion and substitution errors
        // TODO exact, contains and overlap mode?
        // TODO run scoring

        final List<Span> references = readSpans(reference);
        final List<Span> predictions = readSpans(prediction);

        System.out.println(references);
        System.out.println(predictions);

        return null;
    }

}
