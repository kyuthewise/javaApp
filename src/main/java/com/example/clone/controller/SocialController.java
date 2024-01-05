package com.example.clone.controller;


import com.example.clone.service.FriendDetails;
import com.example.clone.service.JwtService;
import com.example.clone.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SocialController {
    @Autowired
    private UserInfoService service;


    @Autowired
    private FriendDetails friends;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/addFriend")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> addFriend(@RequestParam int friendId) {
        try {
            friends.addFriend(friendId);
            return ResponseEntity.ok().body("Friend added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/removeFriend")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> removeFriend(@RequestParam int friendId) {
        try {
            friends.removeFriend(friendId);
            return ResponseEntity.ok().body("Friend removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getFriendlist")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getFriendlist() {
        try {
            List<Map<String, Object>> friendDetailsList = friends.getFriendlist();
            return ResponseEntity.ok().body(friendDetailsList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}


