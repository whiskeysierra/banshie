package org.whiskeysierra.banshie.extractors;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class ExtractorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExtractorStorage.class).to(DefaultExtractorStorage.class).in(Singleton.class);
        bind(export(ExtractorStorage.class)).toProvider(service(DefaultExtractorStorage.class).export());
    }

}
