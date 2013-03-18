package org.whiskeysierra.banshie.example.opennlp;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class Main {

    private static InputStream readResource(String name) throws IOException {
        return Resources.getResource(name).openStream();
    }

    private static SentenceDetector getSentenceDetector() throws IOException {
        final InputStream stream = readResource("en-sent.bin");

        try {
            return new SentenceDetectorME(new SentenceModel(stream));
        } finally {
            stream.close();
        }
    }

    private static Tokenizer getTokenizer() throws IOException {
        final InputStream stream = readResource("en-token.bin");

        try {
            return new TokenizerME(new TokenizerModel(stream));
        } finally {
            stream.close();
        }

    }

    private static TokenNameFinder getNameFinder() throws IOException {
        final InputStream stream = readResource("en-ner-person.bin");

        try {
            return new NameFinderME(new TokenNameFinderModel(stream));
        } finally {
            stream.close();
        }
    }

    public static void main(String[] args) throws IOException, XMLStreamException {
        final InputStreamReader reader = new InputStreamReader(System.in, Charsets.UTF_8);
        final String document = CharStreams.toString(reader);

        final Set<String> types = Sets.newHashSet("person", "organization", "date", "location");

        final SentenceDetector detector = getSentenceDetector();
        final Tokenizer tokenizer = getTokenizer();
        final TokenNameFinder finder = getNameFinder();

        final Joiner joiner = Joiner.on(' ');
        final XMLOutputFactory factory = XMLOutputFactory.newInstance();
        final Writer writer = new OutputStreamWriter(System.out, Charsets.UTF_8);
        final XMLStreamWriter xml = factory.createXMLStreamWriter(writer);
        xml.writeStartDocument("UTF-8", "1.0");
        xml.writeStartElement("document");

        for (Span sentences : detector.sentPosDetect(document)) {
            final String sentence = sentences.getCoveredText(document).toString();
            final Span[] indices = tokenizer.tokenizePos(sentence);
            final String[] tokens = Span.spansToStrings(indices, sentence);
            final Span[] spans = finder.find(tokens);

            if (spans.length == 0) {
                xml.writeCharacters(sentence);
            } else {
                final List<String> words = Arrays.asList(tokens);

                int last = 0;

                for (Span span : spans) {
                    final String word = joiner.join(words.subList(span.getStart(), span.getEnd()));
                    final String type = span.getType();
                    final int start = sentences.getStart() + indices[span.getStart()].getStart();
                    final int end = sentences.getStart() + indices[span.getEnd() - 1].getEnd();

                    // between the end of the last and the start of this span
                    xml.writeCharacters(joiner.join(words.subList(last, span.getStart())));

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

                    last = span.getEnd();
                }

                // rest of the sentence
                xml.writeCharacters(joiner.join(words.subList(last, words.size())));
            }
        }

        xml.writeEndElement();
        xml.writeEndDocument();
        xml.close();

        finder.clearAdaptiveData();
    }

}
