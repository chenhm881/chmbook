package com.chm.book.oauth2.domain;

import lombok.Data;

@Data
public class OauthClientDetails {

    private String clientId;
    private String clientSecret;
    private String resourceIds;
    private String scope;
    private String authorizedGrantTypes;
    private String registeredRedirectUris;
    private String autoApproveScopes;
    private String authorities;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private String additionalInformation;
}
