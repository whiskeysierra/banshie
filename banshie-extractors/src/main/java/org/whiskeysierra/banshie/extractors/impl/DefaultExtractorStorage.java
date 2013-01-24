package org.whiskeysierra.banshie.extractors.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.whiskeysierra.banshie.extractors.Extractor;
import org.whiskeysierra.banshie.extractors.ExtractorStorage;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.UUID;

public class DefaultExtractorStorage implements ExtractorStorage {

    private Provider<EntityManager> provider;

    @Inject
    public DefaultExtractorStorage(Provider<EntityManager> provider) {
        this.provider = provider;
    }

    private EntityManager manager() {
        return provider.get();
    }

    @Transactional
    @Override
    public void save(Extractor extractor) {
        manager().persist(ExtractorEntity.copyOf(extractor));
    }

    @Transactional
    @Override
    public Extractor get(UUID uuid) {
        return manager().find(ExtractorEntity.class, uuid);
    }

    @Transactional
    @Override
    public List<Extractor> listAll() {
        final CriteriaBuilder builder = manager().getCriteriaBuilder();
        final CriteriaQuery<ExtractorEntity> query = builder.createQuery(ExtractorEntity.class);
        final TypedQuery<ExtractorEntity> typedQuery = manager().createQuery(query);

        return Lists.<Extractor>newArrayList(typedQuery.getResultList());
    }

}
