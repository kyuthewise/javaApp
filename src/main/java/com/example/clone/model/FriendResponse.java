package com.example.clone.model;


import java.util.List;
import java.util.Map;

public class FriendResponse {
    private boolean success;
    private String message;
    private List<Map<String, Object>> friendList;
    public FriendResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public List<Map<String, Object>> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Map<String, Object>> friendList) {
        this.friendList = friendList;
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
}