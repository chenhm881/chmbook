package com.chm.book.blog.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class Oauth2ClientConfig {

//    @Bean
//    @ConfigurationProperties("oauth2")
//    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    public OAuth2RestTemplate oAuth2RestTemplate(
//            @Qualifier("oAuth2ProtectedResourceDetails") OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails) {
//        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails);
//    }
//
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository();
//    }
//
//    @Value("${spring.security.oauth2.client.client-id}")
//    private String clientId;
//
//    @Value("${spring.security.oauth2.client.client-secret}")
//    private String clientSecret;
//
//    private ClientRegistration aaaClientRegistration() {
//
//        return ClientRegistration.withRegistrationId("aaa")  // (1)
//                .clientId("blog")  // (2)
//                .clientSecret("123456")  // (3)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.POST)  // (4)
//                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")  // (5)
//                .clientName("AAA")       // (6)
//                .tokenUri("http://localhost:8771/oauth/token")  // (7)
//                .authorizationUri("http://localhost:8771/oauth/authorize")  // (8)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)  // (9)
//                .scope("all")  // (10)
//                .userNameAttributeName("username")  // (11)
//                .userInfoUri("http://your.provider.com/api/v3/user")  // (12)
//                .jwkSetUri("")  // (13)
//                .build();
//    }
//
//    @Bean
//    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails details, OAuth2ClientContext context) {
//        OAuth2RestTemplate template = new OAuth2RestTemplate(details, context);
//
//        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
//        authCodeProvider.setStateMandatory(false);
//        AccessTokenProviderChain provider = new AccessTokenProviderChain(
//                Arrays.asList(authCodeProvider));
//        template.setAccessTokenProvider(provider);
//        return template;
//    }

    /**
     * 注册处理redirect uri的filter
     * @param oauth2RestTemplate
     * @param tokenService
     * @return
     */
//    @Bean
//    public OAuth2ClientAuthenticationProcessingFilter oauth2ClientAuthenticationProcessingFilter(
//            OAuth2RestTemplate oauth2RestTemplate,
//            RemoteTokenServices tokenService) {
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(redirectUri);
//        filter.setRestTemplate(oauth2RestTemplate);
//        filter.setTokenServices(tokenService);
//
//
//        //设置回调成功的页面
//        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler() {
//            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                this.setDefaultTargetUrl("/home");
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
//        });
//        return filter;
//    }
//
//    /**
//     * 注册check token服务
//     * @param details
//     * @return
//     */
//    @Bean
//    public RemoteTokenServices tokenService(OAuth2ProtectedResourceDetails details) {
//        RemoteTokenServices tokenService = new RemoteTokenServices();
//        tokenService.setCheckTokenEndpointUrl(checkTokenUrl);
//        tokenService.setClientId(details.getClientId());
//        tokenService.setClientSecret(details.getClientSecret());
//        return tokenService;
//    }
}
