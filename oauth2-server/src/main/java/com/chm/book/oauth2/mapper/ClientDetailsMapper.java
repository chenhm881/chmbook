package com.chm.book.oauth2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClientDetailsMapper {

    ClientDetails selectClientDetails(String clientId);
}