package org.example.followflow.service;

import org.example.followflow.dto.UserDTO;

import java.util.Set;

public interface UserService {
    void followUser(String followerUsername, String followingUsername);
    void unfollowUser(String followerUsername, String followingUsername);
    Set<UserDTO> getFollowing(String username);
}
