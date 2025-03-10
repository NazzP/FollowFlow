package org.example.followflow.service;

import org.example.followflow.dto.UserDTO;
import org.example.followflow.entity.User;
import org.example.followflow.repository.UserRepository;
import org.example.followflow.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testFollowUser_Success() {
        User follower = new User();
        follower.setUsername("followerUser");

        User following = new User();
        following.setUsername("followingUser");

        when(userRepository.findByUsername("followerUser")).thenReturn(Optional.of(follower));
        when(userRepository.findByUsername("followingUser")).thenReturn(Optional.of(following));

        userService.followUser("followerUser", "followingUser");

        assertTrue(follower.getFollowing().contains(following));
        verify(userRepository).save(follower);
    }

    @Test
    public void testFollowUser_FollowerNotFound() {
        when(userRepository.findByUsername("unknownFollower")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.followUser("unknownFollower", "followingUser"));

        assertEquals("Follower not found", exception.getMessage());
    }

    @Test
    public void testUnfollowUser_Success() {
        User follower = new User();
        follower.setUsername("followerUser");
        Set<User> followedUsers = new HashSet<>();
        follower.setFollowing(followedUsers);

        User following = new User();
        following.setUsername("followingUser");

        followedUsers.add(following);

        when(userRepository.findByUsername("followerUser")).thenReturn(Optional.of(follower));
        when(userRepository.findByUsername("followingUser")).thenReturn(Optional.of(following));

        userService.unfollowUser("followerUser", "followingUser");

        assertFalse(follower.getFollowing().contains(following));
        verify(userRepository).save(follower);
    }

    @Test
    public void testGetFollowing() {
        User user = new User();
        user.setUsername("testUser");

        User followedUser = new User();
        followedUser.setUsername("followedUser");

        Set<User> followedUsers = new HashSet<>();
        followedUsers.add(followedUser);

        user.setFollowing(followedUsers);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Set<UserDTO> followingDTOs = userService.getFollowing("testUser");

        assertEquals(1, followingDTOs.size());
        assertEquals("followedUser", followingDTOs.iterator().next().getUsername());
    }

    @Test
    public void testGetFollowing_UserNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getFollowing("unknownUser"));

        assertEquals("User not found", exception.getMessage());
    }
}
