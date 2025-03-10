package org.example.followflow.repository;

import jakarta.transaction.Transactional;
import org.example.followflow.config.TestAppConfig;
import org.example.followflow.entity.Post;
import org.example.followflow.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSavePost() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        userRepository.save(user);

        Post post = new Post();
        post.setTitle("Post Title");
        post.setBody("Post body content.");
        post.setAuthor(user);

        postRepository.save(post);

        Post foundPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        assertEquals("Post Title", foundPost.getTitle());
        assertEquals("Post body content.", foundPost.getBody());
        assertEquals(user, foundPost.getAuthor());
    }

    @Test
    void testFindById_PostNotFound() {
        assertFalse(postRepository.findById(0L).isPresent());
    }
}
