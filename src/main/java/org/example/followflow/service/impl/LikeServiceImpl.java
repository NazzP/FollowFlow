package org.example.followflow.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.followflow.entity.Like;
import org.example.followflow.entity.Post;
import org.example.followflow.entity.User;
import org.example.followflow.repository.LikeRepository;
import org.example.followflow.repository.PostRepository;
import org.example.followflow.repository.UserRepository;
import org.example.followflow.service.LikeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public void likePost(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        likeRepository.save(Like.builder()
                .user(user)
                .post(post)
                .build());
    }

    @Override
    public Long countLikesForPost(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}
