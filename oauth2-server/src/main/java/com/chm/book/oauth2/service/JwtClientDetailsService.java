package com.chm.book.oauth2.service;

import com.chm.book.oauth2.domain.OauthClientDetails;
import com.chm.book.oauth2.mapper.ClientDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class JwtClientDetailsService implements ClientDetailsService, ClientRegistrationService {

    @Autowired
    private ClientDetailsMapper clientDetailsMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetails oauthClientDetails = clientDetailsMapper.selectClientDetails(clientId);

        String resourceIds = oauthClientDetails.getResourceIds();
        String scopes = oauthClientDetails.getScope();
        String grantTypes = oauthClientDetails.getAuthorizedGrantTypes();
        String authorities = oauthClientDetails.getAuthorities();
        String redirectUris = oauthClientDetails.getRegisteredRedirectUris();
        List<String> autoApproveScopes = new ArrayList<>();
        autoApproveScopes.add(oauthClientDetails.getAutoApproveScopes());

        BaseClientDetails details = new BaseClientDetails(clientId, resourceIds, scopes, grantTypes, authorities, redirectUris);
        details.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValiditySeconds());
        details.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValiditySeconds());
        details.setClientId(oauthClientDetails.getClientId());
        details.setClientSecret(bCryptPasswordEncoder.encode(oauthClientDetails.getClientSecret()));
        details.setAutoApproveScopes(autoApproveScopes);
        return details;
    }

    public List<OauthClientDetails> selectClientDetailsByUsername(String username) throws ClientRegistrationException {
        List<OauthClientDetails> oauthClientDetailslist = clientDetailsMapper.selectClientDetailsByUsername(username);
        return oauthClientDetailslist;
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {


    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {

    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {

    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return null;
    }
}
