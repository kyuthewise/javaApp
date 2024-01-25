package com.example.clone.common;

import com.example.clone.model.UserInfo;
import com.example.clone.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class GetAuth {


    private final UserInfoRepository userInfoRepository;

    @Autowired
    public GetAuth(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userInfoRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
