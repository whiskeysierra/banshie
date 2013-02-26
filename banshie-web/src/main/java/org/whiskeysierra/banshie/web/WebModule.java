package org.whiskeysierra.banshie.web;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import org.osgi.service.http.HttpService;

import static org.ops4j.peaberry.Peaberry.service;

public final class WebModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HttpService.class).toProvider(service(HttpService.class).
            out(Key.get(HttpServiceWatcher.class)).single()).asEagerSingleton();
    }

}
