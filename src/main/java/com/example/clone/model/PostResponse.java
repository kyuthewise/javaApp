package com.example.clone.model;
import java.util.List;
import java.util.Map;

public class PostResponse {
    private boolean success;
    private String message;
    private Post post;
    private List<Map<String, Object>> postList;

    public PostResponse(boolean success, String message, Post post) {
        this.success = success;
        this.message = message;
        this.post = post;
        this.postList = null;


    }
    public PostResponse(boolean success, String message, List<Map<String, Object>> postList) {
        this.success = success;
        this.message = message;
        this.postList = postList;
    }


    public List<Map<String, Object>> getPostList() {
        return postList;
    }

    public void setPostList(List<Map<String, Object>> postList) {
        this.postList = postList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
