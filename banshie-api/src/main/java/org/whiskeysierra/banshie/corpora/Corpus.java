package org.whiskeysierra.banshie.corpora;

import java.io.File;
import java.util.UUID;

public interface Corpus {

    UUID getUuid();

    File getReference();

    File getInput();

}
