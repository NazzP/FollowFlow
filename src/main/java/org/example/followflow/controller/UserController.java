package org.example.followflow.controller;

import lombok.RequiredArgsConstructor;
import org.example.followflow.dto.UserDTO;
import org.example.followflow.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/{followerUsername}/follow/{followingUsername}")
    public ResponseEntity<?> followUser(@PathVariable String followerUsername, @PathVariable String followingUsername) {
        userService.followUser(followerUsername, followingUsername);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followerUsername}/unfollow/{followingUsername}")
    public ResponseEntity<?> unfollowUser(@PathVariable String followerUsername, @PathVariable String followingUsername) {
        userService.unfollowUser(followerUsername, followingUsername);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<Set<UserDTO>> getFollowing(@PathVariable String username) {
        Set<UserDTO> following = userService.getFollowing(username);
        return ResponseEntity.ok(following);
    }
}
