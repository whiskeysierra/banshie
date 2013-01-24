package org.whiskeysierra.banshie.persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

final class PersistenceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PersistenceService.class).in(Singleton.class);
    }

}
