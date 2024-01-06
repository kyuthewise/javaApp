package com.example.clone.controller;

import com.example.clone.model.Comment;
import com.example.clone.model.CommentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.clone.service.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService){
    this.commentService = commentService;
}
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> addComment(@RequestBody Comment comment, @RequestParam int Id) {

        CommentResponse response = commentService.createComment(comment, Id);

        if (response.isSuccess()) {
            return new ResponseEntity<>(response.getComment(), HttpStatus.CREATED);
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response.getMessage());
        }

    }
}
