package org.whiskeysierra.banshie.example.opennlp;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
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
import java.util.Arrays;
import java.util.List;

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

        final SentenceDetector detector = getSentenceDetector();
        final Tokenizer tokenizer = getTokenizer();
        final TokenNameFinder finder = getNameFinder();

        final Joiner joiner = Joiner.on(' ');
        final XMLOutputFactory factory = XMLOutputFactory.newInstance();
        final XMLStreamWriter writer = factory.createXMLStreamWriter(System.out);
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("document");

        for (Span sentences : detector.sentPosDetect(document)) {
            final String sentence = sentences.getCoveredText(document).toString();
            final Span[] indices = tokenizer.tokenizePos(sentence);
            final String[] tokens = Span.spansToStrings(indices, sentence);
            final Span[] spans = finder.find(tokens);

            if (spans.length == 0) {
                writer.writeCharacters(sentence);
            } else {
                final List<String> words = Arrays.asList(tokens);

                int last = 0;

                for (Span span : spans) {
                    final String word = joiner.join(words.subList(span.getStart(), span.getEnd()));
                    final String type = span.getType();
                    final int start = sentences.getStart() + indices[span.getStart()].getStart();
                    final int end = sentences.getStart() + indices[span.getEnd()].getEnd() - 1;

                    // between the end of the last and the start of this span
                    writer.writeCharacters(joiner.join(words.subList(last, span.getStart())));

                    writer.writeStartElement("span");
                    writer.writeAttribute("type", type);
                    writer.writeAttribute("start", String.valueOf(start));
                    writer.writeAttribute("end", String.valueOf(end));
                    writer.writeCharacters(word);
                    writer.writeEndElement();

                    last = span.getEnd();
                }

                // rest of the sentence
                writer.writeCharacters(joiner.join(words.subList(last, words.size())));
            }
        }

        writer.writeEndElement();
        writer.writeEndDocument();
        writer.close();

        finder.clearAdaptiveData();
    }

}
