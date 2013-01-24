package org.whiskeysierra.banshie.jpa;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

final class AriesJpaPersistService implements Provider<EntityManager>, UnitOfWork, PersistService {

    private final ThreadLocal<EntityManager> entityManager = new ThreadLocal<EntityManager>();
    private final EntityManagerFactory factory;

    @Inject
    public AriesJpaPersistService(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void start() {
        // nothing to do
    }

    @Override
    public EntityManager get() {
        if (!isWorking()) {
            begin();
        }

        EntityManager em = entityManager.get();
        Preconditions.checkState(null != em, "Requested EntityManager outside work unit. "
            + "Try calling UnitOfWork.begin() first, or use a PersistFilter if you "
            + "are inside a servlet environment.");

        return em;
    }

    public boolean isWorking() {
        return entityManager.get() != null;
    }

    @Override
    public void begin() {
        Preconditions.checkState(null == entityManager.get(),
            "Work already begun on this thread. Looks like you have called UnitOfWork.begin() twice"
                + " without a balancing call to end() in between.");

        entityManager.set(factory.createEntityManager());
    }

    @Override
    public void end() {
        final EntityManager em = entityManager.get();

        // Let's not penalize users for calling end() multiple times.
        if (em == null) return;

        em.close();
        entityManager.remove();
    }

    @Override
    public void stop() {
        // nothing to do
    }

}
