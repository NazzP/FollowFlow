package org.example.followflow.repository;

import jakarta.transaction.Transactional;
import org.example.followflow.config.TestAppConfig;
import org.example.followflow.entity.Like;
import org.example.followflow.entity.Post;
import org.example.followflow.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@DataJpaTest
public class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional
    @Rollback
    public void testSaveLike() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        userRepository.save(user);

        Post post = new Post();
        post.setTitle("Post Title");
        post.setBody("Post body content.");
        post.setAuthor(user);
        postRepository.save(post);

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);

        assertEquals(1, likeRepository.countByPostId(post.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void testCountLikesForPost() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        userRepository.save(user);

        Post post = new Post();
        post.setTitle("Post Title");
        post.setBody("Post body content.");
        post.setAuthor(user);
        postRepository.save(post);

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);

        Long count = likeRepository.countByPostId(post.getId());
        assertEquals(1L, count);
    }
}
