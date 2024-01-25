package com.example.clone.service;
import com.example.clone.model.Post;
import com.example.clone.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostDataService {
    private final PostRepository postRepository;
    public PostDataService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> findPostById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(int id) {
        postRepository.deleteById(id);
    }
}
