package com.chm.book.blog.controller;

import com.chm.book.blog.domain.*;
import com.chm.book.blog.entityconvert.ArticleConvert;
import com.chm.book.blog.service.ArticleService;
import com.chm.book.blog.service.BlogService;
import com.chm.book.blog.service.OauthService;
import com.chm.book.blog.service.TicketService;
import com.chm.book.blog.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class IndexController {

    @Autowired
    private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private ArticleConvert articleConvert;

    @Autowired
    private OauthService oauthService;

    @RequestMapping("/hello")
    public String helloWorld() {
        //ticketService.findTicket("hello");
        //OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        String getValue = articleService.find();
        return getValue;
    }

    @RequestMapping("/hi")
    public String hi(Authentication user) {
        //ticketService.findTicket("hello");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        String getValue = articleService.addFiles();
        return getValue;
    }
    @RequestMapping("/user")
    public Authentication getUser(Authentication user) {
        return user;
    }

    @CrossOrigin
    @RequestMapping("/articles")
    public ResponseEntity<Map<String,Object>> getArticles( @RequestBody Map<String, Object> params) {
        List<ArticleEntity> articles = blogService.getArticles();
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
        Article article = blogService.getArticle(id);
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
    @RequestMapping("/saveArticle")
    public ResponseEntity<Map<String,Object>> saveArticle(Authentication user, @RequestBody ArticleTags articleTags) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        System.out.println("saveArticle: token " + details.getTokenValue());
        System.out.println("saveArticle: user " + user.getPrincipal());
        ArticleEntity articleEntity = articleConvert.covertToArticle(articleTags);
        List<Integer> tags = articleTags.getTags();
        ResponseEntity<Map<String,Object>> responseEntity  = blogService.saveArticle(articleEntity, tags);
        return responseEntity;
    }


    @CrossOrigin
    @RequestMapping("dologout")
    public ResponseEntity<Map<String,Object>> logout(Authentication user) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        System.out.println(details.getTokenValue());
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication((Authentication)null);
//
//        SecurityContextHolder.clearContext();
        oauthService.exit(details.getTokenValue());
        System.out.println("logout: hello");
        ResponseEntity<Map<String,Object>> responseEntity  = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("tags")
    public ResponseEntity<Map<String,Object>> getTags() {
        List<TagEntity> tags = blogService.getTags();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("data", tags);
        responseMap.put("message", "message1");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("categories")
    public ResponseEntity<Map<String,Object>> getCategories() {
        List<CategoryEntity> categories = blogService.getCategories();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("data", categories);
        responseMap.put("message", "message1");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("/authorize/login")
    public void login(String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!StringUtils.isEmpty(code)) {
            LinkedMultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("client_id", authorizationCodeResourceDetails.getClientId());
            valueMap.add("client_secret", authorizationCodeResourceDetails.getClientSecret());
            valueMap.add("grant_type", authorizationCodeResourceDetails.getGrantType());
            valueMap.add("redirect_uri", authorizationCodeResourceDetails.getPreEstablishedRedirectUri());
            valueMap.add("code", code);
            Map<String, String> map = HttpUtils.doFrom(authorizationCodeResourceDetails.getAccessTokenUri(), valueMap, Map.class);
            System.out.println(map);

            //获取用户信息，说明这里主要目的就是通过资源服务器去获取用户信息
            Map principal = HttpUtils.doGet(resourceServerProperties.getUserInfoUri() + "?access_token=" + map.get("access_token"), Map.class);

            //这里通过本地登录单点登录
            String username = principal.get("name").toString();
            //如果用户存在则不添加，这里如果生产应用中，可以更具规则修改


            //这里通过本地登录的方式来获取会话
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("username", username);
            params.add("password", username);
            HttpEntity<LinkedMultiValueMap<String, ? extends Object>> httpEntity = new HttpEntity(params, httpHeaders);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/login";
            ResponseEntity<Object> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
            //将登录后的header原本的给浏览器，这就是当前浏览器的会话
            HttpHeaders headers = exchange.getHeaders();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                entry.getValue().stream().forEach(value -> response.addHeader(entry.getKey(), value));
            }
            //这个状态是根据security的返回数据设定的
            response.setStatus(exchange.getStatusCode().value());
        }
    }
}
