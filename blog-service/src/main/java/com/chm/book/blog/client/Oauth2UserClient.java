package com.chm.book.blog.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.codec.Encoder;
import feign.codec.StringDecoder;
import feign.form.FormEncoder;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static org.apache.commons.lang3.Validate.notNull;

@Component
@Configuration
public class Oauth2UserClient {
    private final String endpoint;
    private final Logger logger = Logger.getLogger(Oauth2UserClient.class);

    @Autowired
    private Encoder feignFormEncoder;


    public Oauth2UserClient(@Value("${oauth2.endpoint: http://localhost:8771}") String endpoint) {
        this.endpoint = notNull("http://localhost:8771");
    }

    public void Login(LinkedMultiValueMap<String, Object> map) {
        HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
        ObjectFactory converter = ()-> new HttpMessageConverters(jsonConverter);
        Feign.builder()
                .encoder(feignFormEncoder)
                .decoder(new SpringDecoder(converter))
                .target(Oauth2UserConnecter.class, this.endpoint).Login(map);
    }

    public void Authorize() {
        HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
        ObjectFactory converter = ()-> new HttpMessageConverters(jsonConverter);
        Feign.builder()
                .decoder(new SpringDecoder(converter))
                .target(Oauth2UserConnecter.class, this.endpoint).Authorize();
    }
}
