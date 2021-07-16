package com.chm.book.article.domain;

public class PostsWithBLOBs extends Posts {
    private String postsContent;

    private String postsSummary;

    public String getPostsContent() {
        return postsContent;
    }

    public void setPostsContent(String postsContent) {
        this.postsContent = postsContent == null ? null : postsContent.trim();
    }

    public String getPostsSummary() {
        return postsSummary;
    }

    public void setPostsSummary(String postsSummary) {
        this.postsSummary = postsSummary == null ? null : postsSummary.trim();
    }
}