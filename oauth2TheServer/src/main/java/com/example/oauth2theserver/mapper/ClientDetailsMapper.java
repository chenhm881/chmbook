package com.example.oauth2theserver.mapper;

import com.example.oauth2theserver.domain.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClientDetailsMapper {

    OauthClientDetails selectClientDetails(String clientId);
    List<OauthClientDetails> selectClientDetailsByUsername(String username);
}
