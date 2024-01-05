package com.example.clone.service;


import com.example.clone.model.Post;
import com.example.clone.model.UserInfo;
import org.springframework.stereotype.Service;
import com.example.clone.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.clone.repository.UserInfoRepository;

import java.util.*;

@Service

public class PostService {

    private final PostRepository postRepository;
    private final GetAuth getAuth;
    private final UserInfoRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserInfoRepository userRepository, GetAuth getAuth) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.getAuth = getAuth;
    }

    @Autowired
    UserInfoRepository userInfoRepository;
    public Post createPost(Post post) {


        UserInfo currentUser = getAuth.getCurrentUser();

        post.setUser(currentUser);
        return postRepository.save(post);
    }
    public Post handleDelete(int id){

       UserInfo currentUser = getAuth.getCurrentUser();

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (post.getUser().getId() != currentUser.getId()) {
            throw new RuntimeException("Unauthorized to delete this post");
        }
        postRepository.deleteById(id);
        return postRepository.save(post);

    }
    public Post setLike(int id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikeCount(post.getLikeCount() + 1);
        return postRepository.save(post);
    }

    public List<Map<String, Object>> getPost() throws Exception {
        List<Post> currentUserPosts = getAuth.getCurrentUser().getPosts();
        List<Map<String, Object>> PostList = new ArrayList<>();

        for (Post post : currentUserPosts) {
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("id", post.getId());
            postMap.put("post", post.getPost());
            postMap.put("date", post.getDate());
            postMap.put("comments", post.getComments());
            postMap.put("name", post.getUser().getName());
            PostList.add(postMap);
        }
        return PostList;    }

}
