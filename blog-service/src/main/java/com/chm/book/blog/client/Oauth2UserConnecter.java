package com.chm.book.blog.client;


import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface Oauth2UserConnecter {

    @RequestLine("POST /login")
    void Login(@QueryMap LinkedMultiValueMap<String, Object> map);

    @RequestLine("GET /oauth/authorize?client_id=blog&client_secret=123&redirect_uri=http://localhost:8181/login&response_type=code&scope=all")
    void Authorize();

}
