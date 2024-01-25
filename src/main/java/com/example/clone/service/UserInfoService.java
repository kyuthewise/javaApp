package com.example.clone.service;

import com.example.clone.model.UserInfo;
import com.example.clone.model.UserInfoDetails;
import com.example.clone.repository.UserInfoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserDataService userDataService;
    private final PasswordEncoder encoder;

    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder, UserDataService userDataService) {
        this.userDataService = userDataService;
        this.encoder = encoder;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = userDataService.findByName(username);


        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserInfo userInfo) {

        Optional<UserInfo> existingUser = userDataService.findByName(userInfo.getName());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        try {
            userInfo.setPassword(encoder.encode(userInfo.getPassword()));
            userDataService.saveUser(userInfo);
            return "User Added Successfully";
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("User could not be added due to a data integrity violation");
        }
    }

}
