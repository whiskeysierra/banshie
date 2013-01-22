package org.whiskeysierra.banshie.corpora;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class CorpusModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CorpusStorage.class).to(DefaultCorpusStorage.class).in(Singleton.class);
        bind(export(CorpusStorage.class)).toProvider(service(DefaultCorpusStorage.class).export());
    }

}
