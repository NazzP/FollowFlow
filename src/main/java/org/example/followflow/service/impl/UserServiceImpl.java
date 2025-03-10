package org.example.followflow.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.followflow.dto.UserDTO;
import org.example.followflow.entity.User;
import org.example.followflow.repository.UserRepository;
import org.example.followflow.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void followUser(String followerUsername, String followingUsername) {
        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new RuntimeException("Following user not found"));

        follower.getFollowing().add(following);
        userRepository.save(follower);
    }

    @Transactional
    @Override
    public void unfollowUser(String followerUsername, String followingUsername) {
        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new RuntimeException("Following user not found"));

        follower.getFollowing().remove(following);
        userRepository.save(follower);
    }

    @Override
    public Set<UserDTO> getFollowing(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<UserDTO> followingDTOs = new HashSet<>();
        for (User followedUser : user.getFollowing()) {
            followingDTOs.add(UserDTO.builder()
                    .username(followedUser.getUsername())
                    .build());
        }
        return followingDTOs;
    }
}
