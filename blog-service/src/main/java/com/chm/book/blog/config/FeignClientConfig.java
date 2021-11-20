package com.chm.book.blog.config;

import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Autowired
    public ObjectFactory<HttpMessageConverters>  messageConverters;


    @Bean
    public Encoder feignFormEncoder() {
        return new FormEncoder(new SpringEncoder(messageConverters));
    }
}
