package org.whiskeysierra.banshie.extractors.impl;

import org.whiskeysierra.banshie.extractors.Extractor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;
import java.util.UUID;

@Entity
public class ExtractorEntity implements Extractor {

    @Id
    private UUID uuid;

    private String name;
    private String version;
    private String path;

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public File getPath() {
        return new File(path);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static ExtractorEntity copyOf(Extractor extractor) {
        final ExtractorEntity entity = new ExtractorEntity();

        entity.uuid = extractor.getUuid();
        entity.name = extractor.getName();
        entity.version = extractor.getVersion();
        entity.path = extractor.getPath().getAbsolutePath();

        return entity;
    }

}
