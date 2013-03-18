package org.whiskeysierra.banshie.example.stanford.corenlp;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
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
import java.io.Writer;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public final class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        final InputStreamReader reader = new InputStreamReader(System.in, Charsets.UTF_8);
        final String document = CharStreams.toString(reader);

        final Set<String> types = Sets.newHashSet("person", "organization", "date", "location");

        final Annotation annotation = new Annotation(document);

        final Properties properties = new Properties();
        properties.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        final StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);

        pipeline.annotate(annotation);

        final List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);

        final XMLOutputFactory factory = XMLOutputFactory.newInstance();
        final Writer writer = new OutputStreamWriter(System.out, Charsets.UTF_8);
        final XMLStreamWriter xml = factory.createXMLStreamWriter(writer);
        xml.writeStartDocument("UTF-8", "1.0");
        xml.writeStartElement("document");

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                final String word = token.get(TextAnnotation.class);
                final String type = token.get(NamedEntityTagAnnotation.class).toLowerCase(Locale.ENGLISH);
                final int start = token.get(CharacterOffsetBeginAnnotation.class);
                final int end = token.get(CharacterOffsetEndAnnotation.class);

                xml.writeCharacters(" ");

                if (types.contains(type)) {
                    xml.writeStartElement("span");
                    xml.writeAttribute("type", type);
                    xml.writeAttribute("start", String.valueOf(start));
                    xml.writeAttribute("end", String.valueOf(end));
                    xml.writeCharacters(word);
                    xml.writeEndElement();
                } else {
                    xml.writeCharacters(word);
                }
            }
        }

        xml.writeEndElement();
        xml.writeEndDocument();
        xml.close();
    }

}
