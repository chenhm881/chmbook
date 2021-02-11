package com.chm.book.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class Oauth2ClientConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(aaaClientRegistration());
    }

    @Value("${spring.security.oauth2.client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.client-secret}")
    private String clientSecret;

    private ClientRegistration aaaClientRegistration() {
        return ClientRegistration.withRegistrationId("aaa")  // (1)
                .clientId(clientId)  // (2)
                .clientSecret(clientSecret)  // (3)
                .clientAuthenticationMethod(ClientAuthenticationMethod.POST)  // (4)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")  // (5)
                .clientName("AAA")       // (6)
                .tokenUri("http://your.provider.com/oauth/token")  // (7)
                .authorizationUri("http://your.provider.com/oauth/authorize")  // (8)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)  // (9)
                .scope("api")  // (10)
                .userNameAttributeName("username")  // (11)
                .userInfoUri("http://your.provider.com/api/v3/user")  // (12)
                .jwkSetUri("")  // (13)
                .build();
    }

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