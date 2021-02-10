package com.chm.book.blog.datasource;

import java.util.ArrayList;
import java.util.List;

public class DataSourceContextHolder {

    public static List<String> dataSourceIds = new ArrayList<>();

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDatabaseType() {
        return contextHolder.get();
    }

    public static void clearDatabaseType() {
        contextHolder.remove();
    }
}
