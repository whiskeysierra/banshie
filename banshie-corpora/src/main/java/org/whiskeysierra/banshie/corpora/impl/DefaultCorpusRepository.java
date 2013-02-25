package org.whiskeysierra.banshie.corpora.impl;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.corpora.CorpusRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DefaultCorpusRepository implements CorpusRepository {

    private Provider<EntityManager> provider;
    private final File base;

    @Inject
    public DefaultCorpusRepository(Provider<EntityManager> provider,
        @Named("corpus.directory") File base) {

        this.provider = provider;
        this.base = base;

        base.mkdirs();
    }

    private EntityManager manager() {
        return provider.get();
    }

    @Transactional
    @Override
    public void save(Corpus corpus) {
        final File directory = new File(base, corpus.getUuid().toString());
        directory.mkdirs();

        final File reference = new File(directory, corpus.getReference().getName());
        final File input = new File(directory, corpus.getInput().getName());

        try {
            Files.copy(corpus.getReference(), reference);
            Files.copy(corpus.getInput(), input);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        final CorpusEntity entity = CorpusEntity.copyOf(corpus);

        entity.setReference(reference.getName());
        entity.setInput(input.getName());

        manager().persist(entity);
    }

    @Transactional
    @Override
    public Corpus get(UUID uuid) {
        return manager().find(CorpusEntity.class, uuid);
    }

    @Transactional
    @Override
    public List<Corpus> listAll() {
        final CriteriaBuilder builder = manager().getCriteriaBuilder();
        final CriteriaQuery<CorpusEntity> query = builder.createQuery(CorpusEntity.class);
        final TypedQuery<CorpusEntity> typedQuery = manager().createQuery(query);

        return Lists.<Corpus>newArrayList(typedQuery.getResultList());
    }

}
