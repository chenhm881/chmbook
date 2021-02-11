package com.chm.book.article.config;

import com.chm.book.article.utils.JwtUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "rsa.key")
@Data
public class RsaKeyProperties {

    private String publicKeyPath;

    private PublicKey publicKey;

    @PostConstruct
    public void createRsaKey() throws Exception {
        this.publicKey = JwtUtils.getPublicKey(publicKeyPath);
    }
}
