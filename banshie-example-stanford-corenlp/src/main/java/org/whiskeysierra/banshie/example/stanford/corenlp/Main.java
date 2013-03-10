package org.whiskeysierra.banshie.example.stanford.corenlp;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public final class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        final InputStreamReader reader = new InputStreamReader(System.in, Charsets.UTF_8);
        final String document = CharStreams.toString(reader);

        final Annotation annotation = new Annotation(document);

        final Properties properties = new Properties();
        properties.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        final StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);

        pipeline.annotate(annotation);

        final List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);

        final XMLOutputFactory factory = XMLOutputFactory.newInstance();
        final XMLStreamWriter writer = factory.createXMLStreamWriter(new OutputStreamWriter(System.out, Charsets.UTF_8));
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("document");

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                final String word = token.get(TextAnnotation.class);
                final String type = token.get(NamedEntityTagAnnotation.class).toLowerCase(Locale.ENGLISH);
                final int start = token.get(CharacterOffsetBeginAnnotation.class);
                final int end = token.get(CharacterOffsetEndAnnotation.class);

                writer.writeCharacters(" ");

                if ("o".equals(type)) {
                    writer.writeCharacters(word);
                } else {
                    writer.writeStartElement("span");
                    writer.writeAttribute("type", type);
                    writer.writeAttribute("start", String.valueOf(start));
                    writer.writeAttribute("end", String.valueOf(end));
                    writer.writeCharacters(word);
                    writer.writeEndElement();
                }
            }
        }

        writer.writeEndElement();
        writer.writeEndDocument();
        writer.close();
    }

}
