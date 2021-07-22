package com.chm.book.files;

import com.chm.book.files.config.RsaKeyProperties;
import com.chm.book.files.datasource.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableConfigurationProperties({RsaKeyProperties.class})
@SpringBootApplication
@Import({DynamicDataSourceRegister.class})
@EnableResourceServer
@EnableCaching
public class FileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
