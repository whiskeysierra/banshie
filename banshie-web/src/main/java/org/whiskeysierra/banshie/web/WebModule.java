package org.whiskeysierra.banshie.web;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.osgi.service.http.HttpService;
import org.springframework.web.servlet.DispatcherServlet;

import static org.ops4j.peaberry.Peaberry.service;

public final class WebModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HttpService.class).toProvider(service(HttpService.class).
            out(Key.get(HttpServiceWatcher.class)).single()).asEagerSingleton();
    }

    @Provides
    @Singleton
    public DispatcherServlet provideDispatcherServlet() {
        final DispatcherServlet dispatcher = new DispatcherServlet();

        dispatcher.setContextConfigLocation("/WEB-INF/application-context.xml");
        //dispatcher.setContextClass(OsgiBundleXmlWebApplicationContext.class);

        return dispatcher;
    }

}
