package com.chm.book.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TicketService {

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private OAuth2RestTemplate oauth2RestTemplate;

    public String findTicket(String name) {
        //Object object = oauth2RestTemplate.getResource();

        String url="http://ZUUL-GATEWAY/article-service/api/hello";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXJ0aWNsZS1zZXJ2aWNlIl0sInVzZXJfbmFtZSI6ImphY2siLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNjEzNDAzMDMwLCJ1c2VyIjp7InBhc3N3b3JkIjoiJDJhJDEwJFF6RGdzY0c2ZGZyMFRQOTViekExeC5oUEdBOHZUa3FHbm9ucjdNZXZPZjBoRC5TS2dGQVRHIiwidXNlcm5hbWUiOiJqYWNrIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiYWNjb3VudE5vbkV4cGlyZWQiOnRydWUsImFjY291bnROb25Mb2NrZWQiOnRydWUsImNyZWRlbnRpYWxzTm9uRXhwaXJlZCI6dHJ1ZSwiZW5hYmxlZCI6dHJ1ZX0sImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI0MzFjYjRkOC1mOWNkLTRlYjktYjgxZS05YzRjNTE2MTNmMTIiLCJjbGllbnRfaWQiOiJibG9nIn0.uPn3u4N2sEaxE-oGRJfckIT4-SFpMhbnKKQlzEleldgYkdb3j8-J2L_Wsm14L4BZIpqzbnGfJKdNQGGdZDDs1x-8h0JKtx7mzZq-1hkihiYqxRcYk1tJtmB6EYIM2Qp0CNnJT1b4SZlwwpuoA6cKmVTusc3RTlKdTWecyQ6hUain00-eJcZwmZ4laumcR9kS4oaiUduiAsrGPx53bOic71KbQ8B_z83azd2tKAH9ltB_e3UTOVN9aXKEfvNJSXYjH82LqNdIIv5Jb_5qleWEaygoBDVLuaVPg5Odj7bNmlNCHiRrD6xzycGhNkPRu80S6eSJve-M_YuKr0_IjvkXuQ");
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, header);
        //String forObject = restTemplate.getForObject("http://ZUUL-GATEWAY/article-service/api/hello", String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXJ0aWNsZS1zZXJ2aWNlIl0sInVzZXJfbmFtZSI6ImphY2siLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNjEzNDAzMDMwLCJ1c2VyIjp7InBhc3N3b3JkIjoiJDJhJDEwJFF6RGdzY0c2ZGZyMFRQOTViekExeC5oUEdBOHZUa3FHbm9ucjdNZXZPZjBoRC5TS2dGQVRHIiwidXNlcm5hbWUiOiJqYWNrIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiYWNjb3VudE5vbkV4cGlyZWQiOnRydWUsImFjY291bnROb25Mb2NrZWQiOnRydWUsImNyZWRlbnRpYWxzTm9uRXhwaXJlZCI6dHJ1ZSwiZW5hYmxlZCI6dHJ1ZX0sImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI0MzFjYjRkOC1mOWNkLTRlYjktYjgxZS05YzRjNTE2MTNmMTIiLCJjbGllbnRfaWQiOiJibG9nIn0.uPn3u4N2sEaxE-oGRJfckIT4-SFpMhbnKKQlzEleldgYkdb3j8-J2L_Wsm14L4BZIpqzbnGfJKdNQGGdZDDs1x-8h0JKtx7mzZq-1hkihiYqxRcYk1tJtmB6EYIM2Qp0CNnJT1b4SZlwwpuoA6cKmVTusc3RTlKdTWecyQ6hUain00-eJcZwmZ4laumcR9kS4oaiUduiAsrGPx53bOic71KbQ8B_z83azd2tKAH9ltB_e3UTOVN9aXKEfvNJSXYjH82LqNdIIv5Jb_5qleWEaygoBDVLuaVPg5Odj7bNmlNCHiRrD6xzycGhNkPRu80S6eSJve-M_YuKr0_IjvkXuQ");

        //发送请求
        HttpEntity<String> ans = restTemplate
                .exchange(url,
                        HttpMethod.GET,   //GET请求
                        new HttpEntity<>(null, headers),   //加入headers
                        String.class);  //body响应数据接收类型

        return "ticket: " + ans.getBody();
    }
}
