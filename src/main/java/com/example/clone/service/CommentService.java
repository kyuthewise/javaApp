package com.example.clone.service;

import com.example.clone.common.GetAuth;
import com.example.clone.model.Comment;
import com.example.clone.model.CommentResponse;
import com.example.clone.model.Post;
import com.example.clone.model.UserInfo;
import com.example.clone.repository.CommentRepository;
import com.example.clone.repository.PostRepository;
import com.example.clone.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    final PostRepository postRepository;
    final CommentRepository commentRepository;


    final UserInfoRepository userInfoRepository;
    final GetAuth getAuth;


    public CommentService(PostRepository postRepository, UserInfoRepository userInfoRepository, GetAuth getAuth, CommentRepository commentRepository){
        this.postRepository = postRepository;
        this.userInfoRepository = userInfoRepository;
        this.getAuth = getAuth;
        this.commentRepository = commentRepository;
    }
    public CommentResponse createComment(Comment comment, int Id){

        int currentUser = getAuth.getCurrentUser().getId();

        Post post = postRepository.findById(Id).orElse(null);


        UserInfo user = userInfoRepository.findById(currentUser).orElse(null);
        if (post == null) {
            return new CommentResponse(false, "Post not found", null);
        }

        if (user == null) {
            return new CommentResponse(false, "User not found", null);
        }

        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(true, "Comment created successfully", savedComment);

    }

}
