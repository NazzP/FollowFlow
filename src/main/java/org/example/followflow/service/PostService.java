package org.example.followflow.service;


import org.example.followflow.entity.Post;

import java.util.List;

public interface PostService {
    void createPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long id);
}
