package com.chm.book.files.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDatabaseType();
    }

    public DataSource getTargetDataSource() {
        return super.determineTargetDataSource();
    }
}
