package org.example.followflow.it.controller;

import jakarta.transaction.Transactional;
import org.example.followflow.config.TestContainerConfig;
import org.example.followflow.entity.Post;
import org.example.followflow.entity.User;
import org.example.followflow.repository.LikeRepository;
import org.example.followflow.repository.PostRepository;
import org.example.followflow.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestContainerConfig.class})
@Transactional
@SpringBootTest
public class LikeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    private Post testPost;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        likeRepository.deleteAll();

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        userRepository.save(testUser);

        testPost = new Post();
        testPost.setTitle("Post Title");
        testPost.setBody("Post body content.");
        testPost.setAuthor(testUser);
        postRepository.save(testPost);
    }

    @Test
    @Transactional
    public void testLikePost() throws Exception {
        mockMvc.perform(post("/api/v1/likes")
                        .param("username", "testUser")
                        .param("postId", String.valueOf(testPost.getId())))
                .andExpect(status().isOk());

        assertEquals(1, likeRepository.countByPostId(testPost.getId()));
    }
}
