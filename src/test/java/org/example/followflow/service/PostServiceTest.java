package org.example.followflow.service;

import org.example.followflow.entity.Post;
import org.example.followflow.repository.PostRepository;
import org.example.followflow.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void testCreatePost() {
        Post post = new Post();
        post.setTitle("Title 1");
        post.setBody("This is post body.");

        postService.createPost(post);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postCaptor.capture());

        Post savedPost = postCaptor.getValue();
        assertEquals("Title 1", savedPost.getTitle());
        assertEquals("This is post body.", savedPost.getBody());
    }

    @Test
    public void testGetAllPosts() {
        postService.getAllPosts();
        verify(postRepository).findAll();
    }

    @Test
    public void testGetPostById_Success() {
        Post post = new Post();
        post.setId(1L);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post fetchedPost = postService.getPostById(1L);
        assertEquals(post.getId(), fetchedPost.getId());
    }

    @Test
    public void testGetPostById_PostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.getPostById(1L));
        assertEquals("Post not found", exception.getMessage());
    }
}
