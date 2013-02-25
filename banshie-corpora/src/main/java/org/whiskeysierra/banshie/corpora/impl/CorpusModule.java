package org.whiskeysierra.banshie.corpora.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.util.Modules;
import org.whiskeysierra.banshie.corpora.CorpusRepository;
import org.whiskeysierra.banshie.jpa.AriesJpaPersistModule;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class CorpusModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CorpusRepository.class).to(DefaultCorpusRepository.class).in(Singleton.class);
        bind(export(CorpusRepository.class)).toProvider(service(DefaultCorpusRepository.class).export());

        install(Modules.override(new JpaPersistModule("banshie")).with(new AriesJpaPersistModule("banshie")));
    }

}
