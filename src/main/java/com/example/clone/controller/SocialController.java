package com.example.clone.controller;


import com.example.clone.model.CommentResponse;
import com.example.clone.model.FriendResponse;
import com.example.clone.service.FriendService;
import com.example.clone.service.JwtService;
import com.example.clone.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class SocialController {



    private final FriendService friends;

    public SocialController(FriendService friends){
        this.friends = friends;
    }


    @PostMapping("/friends")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> addFriend(@RequestParam int Id) {
        try {
            friends.addFriend(Id);
            return ResponseEntity.ok().body("Friend added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/friends/{Id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> removeFriend(@PathVariable int Id) {
        FriendResponse response = friends.removeFriend(Id);

                if(response.isSuccess()){
                    return ResponseEntity.ok().body("Friend removed successfully");
                }
                else{
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(response.getMessage());
                }
    }


    @GetMapping("/friends")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getFriendlist() {
        FriendResponse response = friends.getFriendlist();

        if (response.isSuccess()) {

            return ResponseEntity.ok().body(response);
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response.getMessage());
        }


    }


}
