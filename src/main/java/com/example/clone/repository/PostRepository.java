package com.example.clone.repository;

import com.example.clone.model.Post;
import com.example.clone.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findById(int id);
}
