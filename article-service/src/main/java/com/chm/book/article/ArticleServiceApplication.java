package com.chm.book.article;

import com.chm.book.article.config.RsaKeyProperties;
import com.chm.book.article.datasource.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableConfigurationProperties({RsaKeyProperties.class})
@SpringBootApplication
@Import({DynamicDataSourceRegister.class})
@EnableResourceServer
public class ArticleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleServiceApplication.class, args);
	}

}
