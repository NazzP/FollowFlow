package org.example.followflow.service;

import jakarta.transaction.Transactional;
import org.example.followflow.entity.Like;
import org.example.followflow.entity.Post;
import org.example.followflow.entity.User;
import org.example.followflow.repository.LikeRepository;
import org.example.followflow.repository.PostRepository;
import org.example.followflow.repository.UserRepository;
import org.example.followflow.service.impl.LikeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    @Test
    public void testLikePost_Success() {
        User user = new User();
        user.setUsername("testUser");

        Post post = new Post();
        post.setId(1L);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        likeService.likePost("testUser", 1L);

        ArgumentCaptor<Like> likeCaptor = ArgumentCaptor.forClass(Like.class);
        verify(likeRepository).save(likeCaptor.capture());

        Like savedLike = likeCaptor.getValue();
        assertEquals(user, savedLike.getUser());
        assertEquals(post, savedLike.getPost());
    }

    @Test
    public void testLikePost_UserNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> likeService.likePost("unknownUser", 1L));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testLikePost_PostNotFound() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> likeService.likePost("testUser", 1L));

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    public void testCountLikesForPost() {
        when(likeRepository.countByPostId(1L)).thenReturn(5L);

        Long count = likeService.countLikesForPost(1L);
        assertEquals(5L, count);
    }
}
