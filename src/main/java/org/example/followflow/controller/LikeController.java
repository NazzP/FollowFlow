package org.example.followflow.controller;

import lombok.RequiredArgsConstructor;
import org.example.followflow.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<?> likePost(@RequestParam String username, @RequestParam Long postId) {
        likeService.likePost(username, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/count")
    public ResponseEntity<Long> countLikesForPost(@PathVariable Long postId) {
        Long count = likeService.countLikesForPost(postId);
        return ResponseEntity.ok(count);
    }
}
