package com.chm.book.oauth2.config;

import com.chm.book.oauth2.utils.JwtUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "rsa.key")
@Data
public class RsaKeyProperties {

    private String publicKeyPath;
    private String privateKeyPath;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void createRsaKey() throws Exception {
        this.publicKey = JwtUtils.getPublicKey(publicKeyPath);
        this.privateKey = JwtUtils.getPrivateKey(privateKeyPath);
    }
}
