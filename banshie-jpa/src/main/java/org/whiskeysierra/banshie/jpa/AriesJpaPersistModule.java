package org.whiskeysierra.banshie.jpa;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Filters.ldap;

public final class AriesJpaPersistModule extends AbstractModule {

    private final String unitName;

    public AriesJpaPersistModule(String unitName) {
        this.unitName = unitName;
    }

    @Override
    protected void configure() {
        final String filter = String.format("(osgi.unit.name=%s)", unitName);
        bind(EntityManagerFactory.class).toProvider(
            service(EntityManagerFactory.class).filter(ldap(filter)).single());

        bind(AriesJpaPersistService.class).in(Singleton.class);
        bind(PersistService.class).to(AriesJpaPersistService.class);
        bind(UnitOfWork.class).to(AriesJpaPersistService.class);
        bind(EntityManager.class).toProvider(AriesJpaPersistService.class);
    }

}
