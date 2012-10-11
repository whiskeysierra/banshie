package org.whiskeysierra.banshie.execution;

import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.Extractor;

public interface Engine {

    ExecutionResult execute(Extractor extractor, Corpus corpus);

}
