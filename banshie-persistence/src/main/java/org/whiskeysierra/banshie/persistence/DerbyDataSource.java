package org.whiskeysierra.banshie.persistence;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.ops4j.peaberry.activation.Stop;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class DerbyDataSource extends ForwardingDataSource {

    private final BasicDataSource source = new BasicDataSource();

    public DerbyDataSource() {
        source.setDriverClassName(EmbeddedDriver.class.getName());
        source.setUrl("jdbc:derby:database");
        source.setUsername("");
        source.setPassword("");
    }

    @Override
    protected DataSource delegate() {
        return source;
    }

    @Stop
    public void onStop() throws SQLException {
        source.close();
    }

}
