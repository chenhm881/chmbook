package com.chm.book.oauth2.controller;

import com.chm.book.oauth2.config.RsaKeyProperties;
import com.chm.book.oauth2.domain.SysUser;
import com.chm.book.oauth2.service.JwtClientDetailsService;
import com.chm.book.oauth2.service.SysUserService;
import com.mysql.cj.conf.RuntimeProperty;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
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

    @Autowired
    private RsaKeyProperties prop;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    JwtClientDetailsService jwtClientDetailsService;


    @Qualifier("oauth2ClientContext")
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

//    @CrossOrigin
//    @ResponseBody
//    @RequestMapping({"/user"})
//    public Map<String, Object> user(@RequestHeader String authorization) {
//
//        Map<String, Object> map = new HashMap<>();
//        OAuth2Authentication authen = null;
//        try {
//            String[] arr = authorization.split(" ");
//            authen = tokenStore.readAuthentication(arr[arr.length - 1]);
//            if(authen == null)
//            {
//                map.put("error", "invalid token!");
//                return map;
//            }
//        } catch (Exception ex) {
//            map.put("error", ex);
//        }
//        map.put("user", authen.getPrincipal());
//        map.put("authorities", authen.getAuthorities());
//        return map;
//    }

    @CrossOrigin
    @RequestMapping("/user")
    public Object getUser(Authentication authentication, HttpServletRequest request)  {
        String header = request.getHeader("Authorization");
        String token = header.substring(header.lastIndexOf("bearer") + 8);

        RSAPrivateKey privateKey = (RSAPrivateKey) prop.getPrivateKey();
        RSAPublicKey publicKey = (RSAPublicKey) prop.getPublicKey();
        //Object obj = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
        Object obj = Jwts.parser().setSigningKey(publicKey).build().parseClaimsJws(token);
        return obj;
    }


    @RequestMapping({"/register"})
    public int register(@RequestBody SysUser user) {
        return sysUserService.insert(user);
    }

//    @RequestMapping({"/"})
//    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        OauthClientDetails oauthClientDetails = jwtClientDetailsService.selectClientDetailsByUsername(request.getUserPrincipal().getName());
//        String clientId = oauthClientDetails.getClientId();
//        String clientSecret = oauthClientDetails.getClientSecret();
//        String redirectUrl = oauthClientDetails.getRegisteredRedirectUris();
//        response.sendRedirect(String.format("oauth/authorize?response_type=code&client_id=%s&client_secret=%s&redirect_uri=%s&scope=all",
//        clientId, clientSecret, redirectUrl));
//    }

    @CrossOrigin
    @RequestMapping({"/getToken"})
    public Map<String, Object> checkUser(Authentication user, @RequestParam Map<String, Object> parameters) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Map<String, Object> map = new HashMap<>();
        try {
            Collection<OAuth2AccessToken> oAuth2AccessTokens = tokenStore.findTokensByClientId("blog");
            if(oAuth2AccessTokens.size() > 0)
            {
                map.put("access_token", oAuth2AccessTokens.stream().findFirst().get().getValue());
                return map;
            }
        } catch (Exception ex) {
            map.put("error", ex);
        }
        return map;
    }

    //登出操作
    @CrossOrigin
    @RequestMapping({"/oauth/exit"})
    public void logout(HttpServletRequest request, HttpServletResponse response, String url, String accessToken) throws IOException {

//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, null, null);
            try {
                System.out.println("referer" + request.getHeader("referer"));
                if (!StringUtils.isEmpty(url)) {
                    //accountService.logout(url, request.getHeader("Authorization"));
                    //String accessToken = request.getHeader("accessToken");
                    consumerTokenServices.revokeToken(accessToken);
                    response.sendRedirect(url);
                } else {
                    throw new BadCredentialsException("Bad credentials");
                }
                //response.sendRedirect(request.getHeader("referer"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 退出登录,并清除token
     **/
    @CrossOrigin
    @RequestMapping("/remove")
    public Object removeToken(Authentication authentication, HttpServletRequest request){
        String oauth2Header = request.getHeader("Authorization");
        if (oauth2Header != null && oauth2Header.startsWith("Bearer")) {
            String token = oauth2Header.substring("Bearer".length()).trim();
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
            if (oAuth2AccessToken != null) {
                tokenStore.removeAccessToken(oAuth2AccessToken);
                tokenStore.readAuthentication(oAuth2AccessToken);
                return "success";
            }

        }
       return "fail";
    }


    /**
     * @Description 账号退出
     * @Date 2019/7/25 17:47
     * @Version  1.0
     */
//    @PostMapping(value = "logout")
//    public Boolean logOut(@RequestParam String access_token){
//        try {
//            if(StringUtils.isNotBlank(access_token)){
//                OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
//                if(oAuth2AccessToken != null){
//                    System.out.println("----access_token是："+oAuth2AccessToken.getValue());
//                    tokenStore.removeAccessToken(oAuth2AccessToken);
//                    OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
//                    if(oAuth2RefreshToken != null) {
//                        tokenStore.removeRefreshToken(oAuth2RefreshToken);
//                        tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
//                    }
//                }
//            }
//            return true;
//        } catch (Exception e){
//            return false;
//        }
//    }
}
