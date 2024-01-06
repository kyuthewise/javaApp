package com.example.clone.controller;
import com.example.clone.model.PostResponse;
import com.example.clone.service.PostService;
import com.example.clone.model.Post;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post) {
        PostResponse response = postService.createPost(post);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response.getMessage());
        }
    }
    @PostMapping("/{id}/like")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> setLike(@PathVariable int id) {
        PostResponse response = postService.setLike(id);
        if(response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> handleDelete(@PathVariable int id) {
        PostResponse response = postService.handleDelete(id);
        if(response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(response.getMessage());
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response.getMessage());
        }
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getPost() {
        try{
        PostResponse postList  = postService.getPost();
        return ResponseEntity.ok().body(postList);
    }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
