package org.whiskeysierra.banshie.corpora.impl;

import org.whiskeysierra.banshie.corpora.Corpus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;
import java.util.UUID;

@Entity
class CorpusEntity implements Corpus {

    @Id
    private UUID uuid;

    private String reference;
    private String input;

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public File getReference() {
        return new File(reference);
    }

    @Override
    public File getInput() {
        return new File(input);
    }

    public void setInput(String input) {
        this.input = input;
    }

    public static CorpusEntity copyOf(Corpus corpus) {
        final CorpusEntity entity = new CorpusEntity();

        entity.uuid = corpus.getUuid();
        entity.reference = corpus.getReference().getAbsolutePath();
        entity.input = corpus.getInput().getAbsolutePath();

        return entity;
    }

}
