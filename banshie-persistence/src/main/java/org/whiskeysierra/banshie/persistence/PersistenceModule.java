package org.whiskeysierra.banshie.persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.names;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.Filters.ldap;

public final class PersistenceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataSource.class).to(DerbyDataSource.class).in(Singleton.class);

        bind(export(DataSource.class)).toProvider(
            service(DataSource.class).attributes(names("osgi.jndi.service.name=jdbc/banshiedb")).export());

        /*
        bind(EntityManagerFactory.class).toProvider(
            service(EntityManagerFactory.class).filter(ldap("(osgi.unit.name=banshie)")).single()).in(Singleton.class);
            */
    }

}
