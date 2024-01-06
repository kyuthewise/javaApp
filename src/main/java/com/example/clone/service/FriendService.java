package com.example.clone.service;

import com.example.clone.common.GetAuth;
import com.example.clone.model.UserInfo;
import com.example.clone.model.FriendResponse;
import com.example.clone.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FriendService {



    private final GetAuth getAuth;
    private final UserDataService userDataService;


    public FriendService(UserInfoRepository userRepository, GetAuth getAuth, UserDataService userDataService){
        this.userDataService = userDataService;
        this.getAuth = getAuth;
    }

    public FriendResponse addFriend(int friendId){


        int currentUser = getAuth.getCurrentUser().getId();

        Optional<UserInfo> userOpt = userDataService.findUserById(currentUser);
        if (!userOpt.isPresent()) {
            return new FriendResponse(false, "User not found");
        }

        Optional<UserInfo> friendOpt = userDataService.findUserById(friendId);
        if (!friendOpt.isPresent()) {
            return new FriendResponse(false, "Friend not found");
        }

        UserInfo user = userOpt.get();
        UserInfo friend = friendOpt.get();


        if (user.getFriends().contains(friend)) {
            return new FriendResponse(false, "They are already friends");
        }

        if (friendId == currentUser) {
            return new FriendResponse(false, "Cannot add yourself");
        }

        user.getFriends().add(friend);
        friend.getFriends().add(user);


        userDataService.saveUser(user);
        userDataService.saveUser(friend);
        return new FriendResponse(true, "Friend added successfully");

    }

    public FriendResponse removeFriend(int friendId){

        int currentUserId = getAuth.getCurrentUser().getId();
        Optional<UserInfo> userOpt = userDataService.findUserById(currentUserId);
        if (!userOpt.isPresent()) {
            return new FriendResponse(false, "User not found");
        }

        Optional<UserInfo> friendOpt = userDataService.findUserById(friendId);
        if (!friendOpt.isPresent()) {
            return new FriendResponse(false, "Friend not found");
        }

        UserInfo user = userOpt.get();
        UserInfo friend = friendOpt.get();


        if (!user.getFriends().remove(friend)) {
            return new FriendResponse(false, "Friend not in users friendlist");
        }

        if (!friend.getFriends().remove(user)) {
            return new FriendResponse(false, "User not found in users friendlist");
        }

        userDataService.saveUser(user);
        userDataService.saveUser(friend);
        return new FriendResponse(true, "Friend removed");
    }

    public FriendResponse getFriendlist(){
        List<UserInfo> currentUserFriends = getAuth.getCurrentUser().getFriends();
        List<Map<String, Object>> friendDetailsList = new ArrayList<>();

        for (UserInfo friend : currentUserFriends) {
            Map<String, Object> friendMap = new HashMap<>();
            friendMap.put("id", friend.getId());
            friendMap.put("name", friend.getName());
            friendDetailsList.add(friendMap);
        }
        FriendResponse response = new FriendResponse(true, "Friend list retrieved successfully");
        response.setFriendList(friendDetailsList);
        return response;
    }


}
