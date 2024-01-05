package com.example.clone.controller;

import com.example.clone.model.Comment;
import com.example.clone.repository.CommentRepository;
import com.example.clone.repository.PostRepository;
import com.example.clone.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.clone.service.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

@Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, @RequestParam int Id) {
        Comment postComment = commentService.createComment(comment, Id);
        return new ResponseEntity<>(postComment, HttpStatus.CREATED);
    }
}
