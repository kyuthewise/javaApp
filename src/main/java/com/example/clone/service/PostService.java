package com.example.clone.service;
import com.example.clone.common.GetAuth;
import com.example.clone.model.Post;
import com.example.clone.model.PostResponse;
import com.example.clone.model.UserInfo;
import org.springframework.stereotype.Service;
import java.util.*;

@Service

public class PostService {

    private final PostDataService postDataService;

    private final GetAuth getAuth;


    public PostService(PostDataService postDataService, GetAuth getAuth) {
        this.postDataService = postDataService;
        this.getAuth = getAuth;
    }

    public PostResponse createPost(Post post) {


        UserInfo currentUser = getAuth.getCurrentUser();

        post.setUser(currentUser);
        try {
            Post savedPost = postDataService.savePost(post);
            return new PostResponse(true, "Post created successfully", savedPost);
        } catch (Exception e) {
            // Handle exception and return an appropriate response
            return new PostResponse(false, "Failed to create post", (Post) null);
        }
    }

    public PostResponse handleDelete(int id){

       UserInfo currentUser = getAuth.getCurrentUser();

        Post post = postDataService.findPostById(id).orElse(null);
        if(post == null){
            return new PostResponse(false, "cannot find post", (Post) null);
        }


        if (post.getUser().getId() != currentUser.getId()) {
            return new PostResponse(false, "Unauthorized to delete this post", (Post) null);
        }
        postDataService.deletePost(id);

        return new PostResponse(true, "Post deleted successfully", (Post) null);

    }
    public PostResponse setLike(int id){
        Post post = postDataService.findPostById(id).orElse(null);
        if(post == null){
            return new PostResponse(false, "cannot find post", (Post) null);
        }


        post.setLikeCount(post.getLikeCount() + 1);
        Post savedPost = postDataService.savePost(post);
        return new PostResponse(true, "Post liked successfully", savedPost);
    }

    public PostResponse getPost(){
        List<Post> currentUserPosts = getAuth.getCurrentUser().getPosts();
        List<Map<String, Object>> postList = new ArrayList<>();

        for (Post post : currentUserPosts) {
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("id", post.getId());
            postMap.put("post", post.getPost());
            postMap.put("date", post.getDate());
            postMap.put("comments", post.getComments());
            postMap.put("name", post.getUser().getName());
            postList.add(postMap);
        }
        return new PostResponse(true, "Posts retrieved successfully", postList);
    }

}
