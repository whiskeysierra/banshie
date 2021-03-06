package org.whiskeysierra.banshie.extractors.impl;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;
import org.ops4j.peaberry.activation.Start;
import org.whiskeysierra.banshie.extractors.Extractor;
import org.whiskeysierra.banshie.extractors.ExtractorRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DefaultExtractorRepository implements ExtractorRepository {

    private final Provider<EntityManager> provider;

    private File basePath = new File("extractors");

    @Inject
    public DefaultExtractorRepository(Provider<EntityManager> provider) {
        this.provider = provider;
    }

    @Inject(optional = true)
    public void setBasePath(@Named("extractors.directory") File basePath) {
        this.basePath = basePath;
    }

    @Start
    public void onStart() {
        basePath.mkdirs();
    }

    private EntityManager manager() {
        return provider.get();
    }

    @Transactional
    @Override
    public void save(Extractor extractor) {
        final File directory = new File(basePath, extractor.getUuid().toString());
        directory.mkdirs();

        final File file = new File(directory, extractor.getPath().getName());

        try {
            Files.copy(extractor.getPath(), file);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        final ExtractorEntity entity = ExtractorEntity.copyOf(extractor);

        entity.setPath(file.getName());

        manager().persist(entity);
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
