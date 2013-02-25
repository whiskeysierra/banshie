package org.whiskeysierra.banshie.corpora;

import java.util.List;
import java.util.UUID;

public interface CorpusRepository {

    void save(Corpus corpus);

    Corpus get(UUID uuid);

    List<Corpus> listAll();

}
