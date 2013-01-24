package org.whiskeysierra.banshie.corpora.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.util.Modules;
import org.whiskeysierra.banshie.corpora.CorpusStorage;
import org.whiskeysierra.banshie.jpa.AriesJpaPersistModule;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class CorpusModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CorpusStorage.class).to(DefaultCorpusStorage.class).in(Singleton.class);
        bind(export(CorpusStorage.class)).toProvider(service(DefaultCorpusStorage.class).export());

        install(Modules.override(new JpaPersistModule("banshie")).with(new AriesJpaPersistModule("banshie")));
    }

}
