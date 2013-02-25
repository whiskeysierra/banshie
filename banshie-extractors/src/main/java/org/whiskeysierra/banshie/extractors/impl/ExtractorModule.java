package org.whiskeysierra.banshie.extractors.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.util.Modules;
import org.whiskeysierra.banshie.extractors.ExtractorRepository;
import org.whiskeysierra.banshie.jpa.AriesJpaPersistModule;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class ExtractorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExtractorRepository.class).to(DefaultExtractorRepository.class).in(Singleton.class);
        bind(export(ExtractorRepository.class)).toProvider(service(ExtractorRepository.class).export());

        install(Modules.override(new JpaPersistModule("banshie")).with(new AriesJpaPersistModule("banshie")));
    }

}
