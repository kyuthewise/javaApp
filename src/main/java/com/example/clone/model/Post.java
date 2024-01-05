package com.example.clone.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Post cannot be blank")
    private String post;

    private String date;

    @Column(name = "like_count", nullable = false, columnDefinition = "integer default 0")
    private int likeCount;
    public Post(){

    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserInfo user;

    public Post(int id, String post, String date) {
        this.id = id;
        this.post = post;
        this.date = date;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Comment> comments;

}
