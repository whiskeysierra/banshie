package org.whiskeysierra.banshie.extractors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final class DefaultExtractorStorage implements ExtractorStorage {

    private EntityManager manager;

    DefaultExtractorStorage(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void save(Extractor extractor) {
        manager.persist(ExtractorEntity.copyOf(extractor));
    }

    @Override
    public Extractor get(UUID uuid) {
        return manager.find(ExtractorEntity.class, uuid);
    }

    @Override
    public List<Extractor> listAll() {
        final CriteriaBuilder builder = manager.getCriteriaBuilder();
        final CriteriaQuery<ExtractorEntity> query = builder.createQuery(ExtractorEntity.class);
        final TypedQuery<ExtractorEntity> typedQuery = manager.createQuery(query);

        return new ArrayList<Extractor>(typedQuery.getResultList());
    }

}
