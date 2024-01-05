package com.example.clone.controller;
import com.example.clone.service.PostService;
import com.example.clone.model.Post;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/post")
public class PostController {


    private PostService postService;

    @Autowired

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PostMapping("/like")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Post> setLike(@RequestParam int id) {
        Post postLiked = postService.setLike(id);
        return new ResponseEntity<>(postLiked, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> handleDelete(@RequestParam int id) {
        Post postDeleted = postService.handleDelete(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("getPost")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getPost() {
        try{
        List<Map<String, Object>> postList  = postService.getPost();
        return ResponseEntity.ok().body(postList);
    }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }


}
