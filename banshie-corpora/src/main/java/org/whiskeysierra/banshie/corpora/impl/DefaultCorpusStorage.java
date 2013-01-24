package org.whiskeysierra.banshie.corpora.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.corpora.CorpusStorage;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.UUID;

public class DefaultCorpusStorage implements CorpusStorage {

    private Provider<EntityManager> provider;

    @Inject
    public DefaultCorpusStorage(Provider<EntityManager> provider) {
        this.provider = provider;
    }

    private EntityManager manager() {
        return provider.get();
    }

    @Transactional
    @Override
    public void save(Corpus corpus) {
        manager().persist(CorpusEntity.copyOf(corpus));
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
