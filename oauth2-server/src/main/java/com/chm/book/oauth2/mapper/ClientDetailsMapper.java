package com.chm.book.oauth2.mapper;

import com.chm.book.oauth2.domain.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClientDetailsMapper {

    OauthClientDetails selectClientDetails(String clientId);
}