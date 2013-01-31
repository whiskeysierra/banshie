package org.whiskeysierra.banshie.example.lingpipe;

import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public final class Main {

    public static void main(String[] args) throws IOException {
        final Reader reader = new InputStreamReader(System.in, Charsets.UTF_8);
        final String document = CharStreams.toString(reader);

        final List<String> tokenList = Lists.newArrayList();
        final List<String> whiteList = Lists.newArrayList();

        final IndoEuropeanTokenizerFactory factory = IndoEuropeanTokenizerFactory.INSTANCE;
        final Tokenizer tokenizer = factory.tokenizer(document.toCharArray(), 0, document.length());
        tokenizer.tokenize(tokenList, whiteList);

        final String[] tokens = Iterables.toArray(tokenList, String.class);
        final String[] whites = Iterables.toArray(whiteList, String.class);
        final MedlineSentenceModel model = new MedlineSentenceModel();
        final int[] sentenceBoundaries = model.boundaryIndices(tokens, whites);

        int sentStartTok = 0;
        int sentEndTok;

        for (int i = 0; i < sentenceBoundaries.length; ++i) {
            sentEndTok = sentenceBoundaries[i];
            System.out.println("SENTENCE " + (i + 1) + ": ");
            for (int j = sentStartTok; j <= sentEndTok; j++) {
                System.out.print(tokens[j] + whites[j + 1]);
            }
            System.out.println();
            sentStartTok = sentEndTok + 1;
        }
    }

}
