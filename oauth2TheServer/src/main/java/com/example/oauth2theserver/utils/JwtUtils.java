package com.example.oauth2theserver.utils;


import com.example.oauth2theserver.domain.Payload;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class JwtUtils {

    private final String JWT_PAYLOAD_USER_KEY = "user";

    private static String readResourceKey(String fileName) {
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

    /**
     * generate the PublicKey object
     *
     * @param pubKeyString
     * @return
     */
    @SneakyThrows
    public static PublicKey getPublicKey(String publicKeyPath) {

        String publicKeyString = readResourceKey(publicKeyPath);
        byte[] bytes = Base64.getDecoder().decode(publicKeyString.getBytes(StandardCharsets.UTF_8));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(spec);
        return publicKey;
    }

    @SneakyThrows
    public static PrivateKey getPrivateKey(String privateKeyPath) {
        String privateKeyString = readResourceKey(privateKeyPath);
        byte[] bytes = Base64.getDecoder().decode(privateKeyString.getBytes(StandardCharsets.UTF_8));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    public <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey, Class<T> userType) {

        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        claims.setSysUser(new Gson().fromJson(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        return claims;
    }

    private Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).build().parseClaimsJws(token);
    }

}
