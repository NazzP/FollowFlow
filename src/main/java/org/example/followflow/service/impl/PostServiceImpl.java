package org.example.followflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.followflow.entity.Post;
import org.example.followflow.repository.PostRepository;
import org.example.followflow.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
