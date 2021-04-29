package com.chm.book.blog.service;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name="ZUUL-GATEWAY")
public interface ArticleService {
//    @PostMapping(value = "/article-service/api/hello", headers = {
//            "Authorization=Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXJ0aWNsZS1zZXJ2aWNlIl0sInVzZXJfbmFtZSI6ImphY2siLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNjEzNDAzMDMwLCJ1c2VyIjp7InBhc3N3b3JkIjoiJDJhJDEwJFF6RGdzY0c2ZGZyMFRQOTViekExeC5oUEdBOHZUa3FHbm9ucjdNZXZPZjBoRC5TS2dGQVRHIiwidXNlcm5hbWUiOiJqYWNrIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiYWNjb3VudE5vbkV4cGlyZWQiOnRydWUsImFjY291bnROb25Mb2NrZWQiOnRydWUsImNyZWRlbnRpYWxzTm9uRXhwaXJlZCI6dHJ1ZSwiZW5hYmxlZCI6dHJ1ZX0sImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI0MzFjYjRkOC1mOWNkLTRlYjktYjgxZS05YzRjNTE2MTNmMTIiLCJjbGllbnRfaWQiOiJibG9nIn0.uPn3u4N2sEaxE-oGRJfckIT4-SFpMhbnKKQlzEleldgYkdb3j8-J2L_Wsm14L4BZIpqzbnGfJKdNQGGdZDDs1x-8h0JKtx7mzZq-1hkihiYqxRcYk1tJtmB6EYIM2Qp0CNnJT1b4SZlwwpuoA6cKmVTusc3RTlKdTWecyQ6hUain00-eJcZwmZ4laumcR9kS4oaiUduiAsrGPx53bOic71KbQ8B_z83azd2tKAH9ltB_e3UTOVN9aXKEfvNJSXYjH82LqNdIIv5Jb_5qleWEaygoBDVLuaVPg5Odj7bNmlNCHiRrD6xzycGhNkPRu80S6eSJve-M_YuKr0_IjvkXuQ"
//    })
    @PostMapping(value = "/article-service/api/hello")
//    public String find(@RequestHeader("Authorization") String signature);
    public String find();

}