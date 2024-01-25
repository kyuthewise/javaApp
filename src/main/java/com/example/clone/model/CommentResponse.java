package com.example.clone.model;
import com.example.clone.model.Comment;

public class CommentResponse {
    private boolean success;
    private String message;
    private Comment comment;

    public CommentResponse(boolean success, String message, Comment comment) {
        this.success = success;
        this.message = message;
        this.comment = comment;
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


    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

}
