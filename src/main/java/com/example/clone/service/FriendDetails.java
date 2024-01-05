package com.example.clone.service;

import com.example.clone.model.UserInfo;
import com.example.clone.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendDetails {

    @Autowired
    private UserInfoRepository userRepository;

    private final GetAuth getAuth;

    @Autowired
    public FriendDetails(UserInfoRepository userRepository, GetAuth getAuth){
        this.userRepository = userRepository;
        this.getAuth = getAuth;
    }
    public void addFriend(int friendId) throws Exception {


        int currentUser = getAuth.getCurrentUser().getId();

        UserInfo user = userRepository.findById(currentUser)
                .orElseThrow(() -> new Exception("User not found"));
        UserInfo friend = userRepository.findById(friendId)
                .orElseThrow(() -> new Exception("Friend not found"));


        if (user.getFriends().contains(friend)) {
            throw new Exception("They are already friends");
        }
        if (friendId == currentUser){
            throw new Exception("cannot add yourself");
        }



        user.getFriends().add(friend);
        friend.getFriends().add(user);


        userRepository.save(user);
        userRepository.save(friend);
    }

    public void removeFriend(int friendId) throws Exception {

        int currentUserId = getAuth.getCurrentUser().getId();
        UserInfo currentUser = getAuth.getCurrentUser();

        userRepository.findById(currentUserId)
                .orElseThrow(() -> new Exception("Current user not found"));
        UserInfo friend = userRepository.findById(friendId)
                .orElseThrow(() -> new Exception("Friend not found"));

        if (!currentUser.getFriends().remove(friend)) {
            throw new Exception("Friend not found in user's friend list");
        }

        if (!friend.getFriends().remove(currentUser)) {
            throw new Exception("User not found in friend's friend list");
        }

        userRepository.save(currentUser);
        userRepository.save(friend);
    }

    public List<Map<String, Object>> getFriendlist() throws Exception {
        List<UserInfo> currentUserFriends = getAuth.getCurrentUser().getFriends();
        List<Map<String, Object>> friendDetailsList = new ArrayList<>();

        for (UserInfo friend : currentUserFriends) {
            Map<String, Object> friendMap = new HashMap<>();
            friendMap.put("id", friend.getId());
            friendMap.put("name", friend.getName());
            friendDetailsList.add(friendMap);
        }

        return friendDetailsList;
    }
}
