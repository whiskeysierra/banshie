package org.whiskeysierra.banshie;

import java.io.File;
import java.util.UUID;

public interface Extractor {

    UUID getUuid();

    String getName();

    String getVersion();

    File getPath();

}
