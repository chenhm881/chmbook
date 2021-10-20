package com.chm.book.blog.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.chm.book.blog.datasource.DataSourceContextHolder;
import com.chm.book.blog.datasource.DynamicDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment env;

    private Binder binder;

    @SneakyThrows
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        Map defaultDataSourceProperties = binder.bind("spring.datasource.master", Map.class).get();
        String typeStr = env.getProperty("spring.datasource.master.type");
        Class<? extends DruidXADataSource> clazz = getDataSourceType(typeStr);
        DruidXADataSource defaultDruidDatasource = bind(clazz, defaultDataSourceProperties);
        final DataSource defaultDatasource = xaDataSource(defaultDruidDatasource);

        DataSourceContextHolder.dataSourceIds.add("master");

        List<Map> configs = binder.bind("spring.datasource.cluster", Bindable.listOf(Map.class)).get();
        Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();
        for (int i = 0; i < configs.size(); i++) {
            Map config = configs.get(i);
            clazz = getDataSourceType((String) config.get("type"));
            String key = config.get("key").toString();
            DruidXADataSource customDruidXADataSource = bind(clazz, config);
            DataSource xaDataSource = xaDataSource(customDruidXADataSource);
            customDataSources.put(key, xaDataSource);
            DataSourceContextHolder.dataSourceIds.add(key);
        }

        GenericBeanDefinition define = new GenericBeanDefinition();
        define.setBeanClass(DynamicDataSource.class);
        MutablePropertyValues mpv = define.getPropertyValues();
        mpv.add("defaultTargetDataSource", defaultDatasource);
        mpv.add("targetDataSources", customDataSources);
        beanDefinitionRegistry.registerBeanDefinition("datasource", define);

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        this.binder = Binder.get(this.env);
    }

    private Class<? extends DruidXADataSource> getDataSourceType(String typeStr) {
        Class<? extends DruidXADataSource> type;
        try {
            if (StringUtils.hasLength(typeStr)) {
                type = (Class<? extends DruidXADataSource>) Class.forName(typeStr);
            } else {
                type = DruidXADataSource.class;
            }
            return type;
        } catch (Exception e) {
            throw new IllegalArgumentException("can not resolve class with type: " + typeStr);
        }
    }

    private <T extends DruidXADataSource> T bind(Class<T> clazz, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source});
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get();
    }

    private DataSource xaDataSource(DruidXADataSource druidXADataSource) throws Exception {
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName(druidXADataSource.getName());
        xaDataSource.setMaintenanceInterval(120);
        xaDataSource.setLoginTimeout(60);
        xaDataSource.setBorrowConnectionTimeout(60);
        xaDataSource.setMaxIdleTime(120);
//        xaDataSource.setMinPoolSize(5);
//        xaDataSource.setMaxPoolSize(30);
        xaDataSource.setTestQuery("Select 1");
        return xaDataSource;
    }
}
