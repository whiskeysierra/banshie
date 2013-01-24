package org.whiskeysierra.banshie.extractors.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.whiskeysierra.banshie.extractors.ExtractorStorage;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class ExtractorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExtractorStorage.class).to(DefaultExtractorStorage.class).in(Singleton.class);
        bind(export(ExtractorStorage.class)).toProvider(service(ExtractorStorage.class).export());
    }

}
