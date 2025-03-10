package org.example.followflow.it.controller;

import org.example.followflow.config.TestAppConfig;
import org.example.followflow.entity.Post;
import org.example.followflow.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ContextConfiguration;

import jakarta.transaction.Transactional;
import org.example.followflow.config.TestContainerConfig;
import org.example.followflow.entity.User;
import org.example.followflow.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestContainerConfig.class})
public class PostControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User testUser;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        userRepository.save(testUser);
    }

    @Test
    @Transactional
    public void testCreatePost() throws Exception {
        Post post = new Post();
        post.setTitle("Post Title");
        post.setBody("Post body content.");
        post.setAuthor(testUser);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isOk());

        assertEquals(1, postRepository.count());
    }

    @Test
    @Transactional
    public void testGetPostById() throws Exception {
        Post post = new Post();
        post.setTitle("Post Title");
        post.setBody("Post body content.");
        post.setAuthor(testUser);
        postRepository.save(post);

        mockMvc.perform(get("/api/v1/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Post Title"));
    }

    @Test
    @Transactional
    public void testGetAllPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk());
    }
}
