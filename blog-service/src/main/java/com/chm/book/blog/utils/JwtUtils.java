package com.chm.book.blog.utils;

import com.chm.book.blog.domain.Payload;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class JwtUtils {

    private final String publicKeyPath;

    private final String JWT_PAYLOAD_USER_KEY = "user";

    @Autowired
    public JwtUtils(@Value("${oauth2.key.publicKey}") String publicKey) {
        this.publicKeyPath = notNull(publicKey);
    }

    public String readResourceKey(String fileName) {
        String key = null;
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            assert inputStream != null;
            key = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }


    private Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
    }

    /**
     * generate the PublicKey object
     *
     * @param pubKeyString
     * @return
     */
    @SneakyThrows
    private PublicKey getPublicKey(String pubKeyString) {

        byte[] bytes = Base64.getDecoder().decode(pubKeyString.getBytes(StandardCharsets.UTF_8));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(spec);
        return publicKey;
    }

    public <T> Payload<T> getInfoFromToken(String token, Class<T> userType) {

        String pubKeyString = readResourceKey(publicKeyPath);

        PublicKey publicKey = getPublicKey(pubKeyString);

        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        claims.setSysUser(new Gson().fromJson(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        return claims;
    }
}
