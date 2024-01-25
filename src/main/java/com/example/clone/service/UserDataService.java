package com.example.clone.service;

import com.example.clone.model.UserInfo;
import com.example.clone.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDataService {

    private final UserInfoRepository userInfoRepository;


    public UserDataService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo saveUser(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }
    public Optional<UserInfo> findByName(String name) {
        return userInfoRepository.findByName(name);
    }

    public Optional<UserInfo> findUserById(int id) {
        return userInfoRepository.findById(id);
    }

    public List<UserInfo> findAllUsers() {
        return userInfoRepository.findAll();
    }

    public void deleteUser(int id) {
        userInfoRepository.deleteById(id);
    }
}
