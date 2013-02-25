package org.whiskeysierra.banshie.extractors;

import java.util.List;
import java.util.UUID;

public interface ExtractorRepository {

    void save(Extractor extractor);

    Extractor get(UUID uuid);

    List<Extractor> listAll();

}
