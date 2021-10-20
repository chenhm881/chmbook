package com.chm.book.blog.client;


import feign.Param;
import feign.RequestLine;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface Oauth2UserConnecter {

    @RequestLine("GET /login?username=blog&password=123")
    void Login(Map<String, String> map);

    @RequestLine("GET /oauth/authorize?response_type=code&client_id=blog&client_secret=123&redirect_uri=http://localhost:8181/authorize/login&scope=all")
    void Authorize();

}
