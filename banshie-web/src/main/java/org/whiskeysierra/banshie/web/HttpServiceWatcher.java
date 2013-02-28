package org.whiskeysierra.banshie.web;

import com.google.inject.Inject;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.util.AbstractWatcher;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;

final class HttpServiceWatcher extends AbstractWatcher<HttpService> {

    private final DispatcherServlet dispatcher;
    private final TestServlet test;

    @Inject
    HttpServiceWatcher(DispatcherServlet dispatcher, TestServlet test) {
        this.dispatcher = dispatcher;
        this.test = test;
    }

    @Override
    protected HttpService adding(Import<HttpService> service) {
        try {
            final HttpService http = service.get();

            http.unregister("/");
            http.registerServlet("/", dispatcher, null, null);

            http.unregister("/test");
            http.registerServlet("/test", test, null, null);

            return http;
        } catch (ServletException e) {
            throw new IllegalStateException(e);
        } catch (NamespaceException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void removed(HttpService service) {
        service.unregister("/");
        service.unregister("/test");
    }

}
