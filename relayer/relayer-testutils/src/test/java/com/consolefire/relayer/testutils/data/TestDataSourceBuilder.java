package com.consolefire.relayer.testutils.data;

import javax.sql.DataSource;

public abstract class TestDataSourceBuilder {



    public abstract DataSource buildDataSource();

    public abstract String getUrl();
    public abstract String getUsername();
    public abstract String getPassword();
}
