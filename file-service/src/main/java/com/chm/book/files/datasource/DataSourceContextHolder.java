package com.chm.book.files.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataSourceContextHolder {

    public static List<String> dataSourceIds = new ArrayList<>();

    public static final String DEFAULT_DS = "master";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDatabaseType() {
        return Optional.ofNullable(contextHolder.get()).orElse(DEFAULT_DS);
    }

    public static void clearDatabaseType() {
        contextHolder.remove();
    }
}
