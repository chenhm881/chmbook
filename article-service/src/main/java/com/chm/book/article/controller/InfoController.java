package com.chm.book.article.controller;

import com.chm.book.article.command.BlogHystrixObservableCommand;
import com.chm.book.article.domain.*;
import com.chm.book.article.service.*;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import rx.Observable;
import rx.Observer;

import java.util.*;

@RestController
@RequestMapping("/api")
public class InfoController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeStateService likeStateService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @CrossOrigin
    @RequestMapping("hello")
    public String helloWorld() {
        //int a = 5/0;
        return "Hello World";
    }

    @RequestMapping("find")
    public String getBlog()
    {

//        BlogCommandSemaphore command = new BlogCommandSemaphore(blogService, 62);
//        List<TBlog> tBlogs = command.execute();
//        //List<TBlog> tBlogs = blogService.getTBlog(62);
//        if(tBlogs.size() > 0) {
//        return "successfully";}
//        else {
//            return "failure";
//        }
        return "hello article";
    }

    @RequestMapping("tags")
    public List<TagEntity> getTags() {
        List<TagEntity> tags = tagService.getTags();
        return tags;
    }

    @RequestMapping("categories")
    public List<CategoryEntity> getCategories() {
        List<CategoryEntity> categories = categoryService.getCategories();
        return categories;
    }

    @RequestMapping("comments/article")
    public List<Comment> getCommentByArticleId(Integer articleId) {
        List<Comment> comments = commentService.getByArticleId(articleId);
        return comments;
    }

    @RequestMapping("comments/author")
    public List<Comment> getCommentByAuthorId(Integer authorId) {
        List<Comment> comments = commentService.getByAuthorId(authorId);
        return comments;
    }

    @RequestMapping("comment/save")
    public int insertOrUpdateComment(@RequestBody Comment comment) {
        int retValue = commentService.save(comment);
        return retValue;
    }

    @RequestMapping("comment/delete")
    public int deleteComment(Integer id) {
        int retValue = commentService.delete(id);
        return retValue;
    }

    @RequestMapping("like/save")
    public int insertOrUpdateLikeState(@RequestBody LikeState likeState) {
        int retValue = likeStateService.save(likeState);
        return retValue;
    }

    @RequestMapping("like/delete")
    public int deleteLikeState(Integer articleId, Integer authorId) {
        int retValue = likeStateService.delete(articleId, authorId);
        return retValue;
    }

    @RequestMapping("like/query")
    public LikeState queryLikeState(Integer articleId, Integer authorId) {
        LikeStateKey likeStateKey = new LikeStateKey(articleId, authorId.longValue());
        LikeState retValue = likeStateService.find(likeStateKey);
        return retValue;
    }

}
