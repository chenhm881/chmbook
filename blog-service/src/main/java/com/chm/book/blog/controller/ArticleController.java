package com.chm.book.blog.controller;

import com.chm.book.blog.client.Oauth2UserClient;
import com.chm.book.blog.domain.*;
import com.chm.book.blog.entityconvert.ArticleConvert;
import com.chm.book.blog.service.FileService;
import com.chm.book.blog.service.ArticleService;
import com.chm.book.blog.service.OauthService;
import com.chm.book.blog.service.TicketService;
import com.chm.book.blog.utils.HttpUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ArticleController {

    @Autowired
    private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleConvert articleConvert;

    @Autowired
    private OauthService oauthService;

    @Autowired
    private Oauth2UserClient oauth2UserClient;

    @Autowired
    @Lazy
    private TokenStore tokenStore;


    @RequestMapping("/hi")
    public String hi(Authentication user) {
        //ticketService.findTicket("hello");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        String getValue = fileService.addFiles();
        return getValue;
    }
    @RequestMapping("/user")
    public Authentication getUser(Authentication user) {
        return user;
    }


    @CrossOrigin
    @RequestMapping("/articles")
    public ResponseEntity<Map<String,Object>> getArticles( @RequestBody Map<String, Object> params) {
        List<ArticleEntity> articles = articleService.getArticles();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("data", articles);
        responseMap.put("message", "message1");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

    @CrossOrigin
    @RequestMapping("/article/{id}")
    public ResponseEntity<Map<String,Object>> getArticle( @PathVariable Integer id) {
        Article article = articleService.getArticle(id);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("data", article);
        responseMap.put("message", "message1");
        responseMap.put("category", 1);
        responseMap.put("tags", new Integer[] {1, 2});
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

    @CrossOrigin
    @RequestMapping("/save")
    public ResponseEntity<Map<String,Object>> save(Authentication user, HttpServletRequest request, @RequestBody ArticleTags articleTags) {
        //OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        //System.out.println("saveArticle: token " + details.getTokenValue());
        //System.out.println("saveArticle: user " + user.getPrincipal());
        System.out.println("user: " + request.getHeader("authorization"));
        ArticleEntity articleEntity = articleConvert.covertToArticle(articleTags);
        List<Integer> tags = articleTags.getTags();
        System.out.println("articleEntity: " + articleEntity.getContent());
        System.out.println("tags: " + articleTags.getTags());
        String authorization =  request.getHeader("authorization");
        ResponseEntity<Map<String,Object>> responseEntity  = articleService.save(authorization, articleEntity, tags);
        return responseEntity;
    }


    @RequestMapping("tags")
    public ResponseEntity<Map<String,Object>> getTags() {
        List<TagEntity> tags = articleService.getTags();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("data", tags);
        responseMap.put("message", "message1");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("categories")
    public ResponseEntity<Map<String,Object>> getCategories() {
        List<CategoryEntity> categories = articleService.getCategories();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("data", categories);
        responseMap.put("message", "message1");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

    @CrossOrigin
    @RequestMapping("/authorize/login2")
    public void login2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String plainCreds = "admin:123";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        httpHeaders.add("Authorization", "Basic " + base64Creds);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //tokenStore.getAccessToken((OAuth2Authentication) request.getUserPrincipal()).getValue();

        LinkedMultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("username", "blog");
        valueMap.add("password", "123");
        HttpEntity<LinkedMultiValueMap<String, ? extends Object>> loginEntity = new HttpEntity(valueMap, httpHeaders);
        String url = "http://localhost:8771/login";


        LinkedMultiValueMap<String, Object> valueMap1 = new LinkedMultiValueMap<>();
        valueMap1.add("clientId", "blog");
        HttpEntity<LinkedMultiValueMap<String, ? extends Object>> loginEntity1 = new HttpEntity(valueMap1, httpHeaders);

        //ResponseEntity<Object> userResponse = restTemplate.exchange("http://localhost:8771/getToken?clientId=blog", HttpMethod.GET, null, Object.class);

        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("clientId", authorizationCodeResourceDetails.getClientId());
        params.add("authentication", request.getUserPrincipal());
        //params.add("client_secret", authorizationCodeResourceDetails.getClientSecret());
        //params.add("grant_type", "client_credentials");
        //params.add("redirect_uri", authorizationCodeResourceDetails.getPreEstablishedRedirectUri());
        //params.add("response_type", "code");
        HttpEntity<LinkedMultiValueMap<String, ? extends Object>> httpEntity = new HttpEntity(params, httpHeaders);
        System.out.println(authorizationCodeResourceDetails.getUserAuthorizationUri());
        //Map<String, Object> map = HttpUtils.doFrom(authorizationCodeResourceDetails.getAccessTokenUri(), valueMap, Map.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("authorization", "");

        HttpEntity<LinkedMultiValueMap<String, ? extends Object>> requestEntity = new HttpEntity(params, requestHeaders);
        //Map principal = restTemplate.postForObject("http://localhost:8771/getToken", requestEntity, Map.class);

        http://localhost:8771/oauth/token?grant_type=client_credentials&scope=all&client_id=client_2&client_secret=123

        //Map map = HttpUtils.doGet("http://localhost:8771/getToken?clientId=blog", Map.class);
        //System.out.println(map);
        //restTemplate.getForObject("http://localhost:8771/getToken?clientId=blog", Map.class);

        //Map map = restTemplate.getForObject("http://localhost:8771/getToken?clientId={1}", Map.class, "blog");
        //oauth2UserClient.Authorize();

        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            entry.getValue().stream().forEach(value -> response.addHeader(entry.getKey(), value));
        }
        System.out.println(authorizationCodeResourceDetails.getPreEstablishedRedirectUri());

        String token = ((OAuth2AuthenticationDetails)((OAuth2Authentication) request.getUserPrincipal()).getDetails()).getTokenValue();
        String userName = request.getUserPrincipal().getName();
        response.sendRedirect("http://localhost:3000/about"
                + "?access_token=" +  token + "&username=" + userName);

    }


    @CrossOrigin
    @RequestMapping("/authorize/login1")
    public void authorize(String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!StringUtils.isEmpty(code)) {
            LinkedMultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("client_id", authorizationCodeResourceDetails.getClientId());
            valueMap.add("client_secret", authorizationCodeResourceDetails.getClientSecret());
            valueMap.add("grant_type", authorizationCodeResourceDetails.getGrantType());
            valueMap.add("redirect_uri", request.getRequestURL());
            valueMap.add("code", code);
            Map<String, Object> map = HttpUtils.doFrom(authorizationCodeResourceDetails.getAccessTokenUri(), valueMap, Map.class);
            System.out.println(map);
            System.out.println(request.getHeader("referer"));

            response.sendRedirect("http://localhost:3000/about"
                    + "?access_token=" + map.get("access_token"));
        }
    }
        @CrossOrigin
        @RequestMapping("/authorize/login")
        public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String token = ((OAuth2AuthenticationDetails)((OAuth2Authentication) request.getUserPrincipal()).getDetails()).getTokenValue();
            String userName = request.getUserPrincipal().getName();
            response.sendRedirect("http://localhost:3000/about"
                    + "?access_token=" +  token + "&username=" + userName);

    }

}