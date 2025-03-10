package org.example.followflow.service;

public interface LikeService {
    void likePost(String username, Long postId);
    Long countLikesForPost(Long postId);
}
