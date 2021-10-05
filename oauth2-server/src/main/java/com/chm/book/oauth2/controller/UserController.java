package com.chm.book.oauth2.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private JwtTokenStore JwtTokenStore;

    @Qualifier("jwtTokenStore")
    @Autowired
    @Lazy
    private TokenStore tokenStore;

    @CrossOrigin
    @ResponseBody
    @RequestMapping({"/user"})
    public Map<String, Object> user(@RequestHeader String authorization) {


        Map<String, Object> map = new HashMap<>();
        OAuth2Authentication authen = null;
        try {
            authen = tokenStore.readAuthentication(authorization.split(" ")[1]);
            if(authen == null)
            {
                map.put("error", "invalid token!");
                return map;
            }
        } catch (Exception ex) {
            map.put("error", ex);
        }
        map.put("user", authen.getPrincipal());
        map.put("authorities", authen.getAuthorities());
        return map;
    }


    //登出操作
    @CrossOrigin
    @RequestMapping({"/oauth/exit"})
    public void logout(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        new SecurityContextLogoutHandler().logout(request, response, authentication);
        try {
            System.out.println("referer" + request.getHeader("referer"));
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {//清除认证
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
        //重定向到指定页面

    }


    /**
     * 退出登录,并清除token
     **/
    @GetMapping("/removeToken")
    public Boolean removeToken(String accessToken){

        return consumerTokenServices.revokeToken(accessToken);
    }

    /**
     * @Description 账号退出
     * @Date 2019/7/25 17:47
     * @Version  1.0
     */
    @PostMapping(value = "logout")
    public Boolean logOut(@RequestParam String access_token){
        try {
            if(StringUtils.isNotBlank(access_token)){
                OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
                if(oAuth2AccessToken != null){
                    System.out.println("----access_token是："+oAuth2AccessToken.getValue());
                    tokenStore.removeAccessToken(oAuth2AccessToken);
                    OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
                    if(oAuth2RefreshToken != null) {
                        tokenStore.removeRefreshToken(oAuth2RefreshToken);
                        tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
                    }
                }
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
