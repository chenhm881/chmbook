package com.chm.book.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom.dispatch")
@Data
public class CustomDispatchProperties {

    private String redirectUri;
    private String auth2LogoutUrl;

}
