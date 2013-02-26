package org.whiskeysierra.banshie.web;

import com.google.inject.Inject;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.util.AbstractWatcher;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import javax.servlet.ServletException;

final class HttpServiceWatcher extends AbstractWatcher<HttpService> {

    private final SimpleServlet servlet;

    @Inject
    HttpServiceWatcher(SimpleServlet servlet) {
        this.servlet = servlet;
    }

    @Override
    protected HttpService adding(Import<HttpService> service) {
        try {
            final HttpService http = service.get();
            http.unregister("/banshie");
            http.registerServlet("/banshie", servlet, null, null);

            return http;
        } catch (ServletException e) {
            throw new IllegalStateException(e);
        } catch (NamespaceException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void removed(HttpService service) {
        service.unregister("/banshie");
    }

}
