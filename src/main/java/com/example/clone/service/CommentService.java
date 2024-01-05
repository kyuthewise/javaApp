package com.example.clone.service;

import com.example.clone.model.Comment;
import com.example.clone.model.Post;
import com.example.clone.model.UserInfo;
import com.example.clone.repository.CommentRepository;
import com.example.clone.repository.PostRepository;
import com.example.clone.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    final PostRepository postRepository;
    final CommentRepository commentRepository;

    @Autowired
    final UserInfoRepository userInfoRepository;
    final GetAuth getAuth;

    @Autowired
    public CommentService(PostRepository postRepository, UserInfoRepository userInfoRepository, GetAuth getAuth, CommentRepository commentRepository){
        this.postRepository = postRepository;
        this.userInfoRepository = userInfoRepository;
        this.getAuth = getAuth;
        this.commentRepository = commentRepository;
    }
    public Comment createComment(Comment comment, int Id){

        int currentUser = getAuth.getCurrentUser().getId();

        Post post = postRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        UserInfo user = userInfoRepository.findById(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);

    }
}
