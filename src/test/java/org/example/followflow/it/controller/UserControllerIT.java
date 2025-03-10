package org.example.followflow.it.controller;

import jakarta.transaction.Transactional;
import org.example.followflow.config.TestAppConfig;
import org.example.followflow.config.TestContainerConfig;
import org.example.followflow.entity.User;
import org.example.followflow.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestContainerConfig.class})
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");

        userRepository.save(testUser);
    }

    @Test
    @Transactional
    public void testFollowUser() throws Exception {
        User followUser = new User();
        followUser.setUsername("followUser");
        followUser.setPassword("followUserPassword");
        userRepository.save(followUser);

        mockMvc.perform(post("/api/v1/users/{followerUsername}/follow/{followingUsername}", "testUser", "followUser"))
                .andExpect(status().isOk());

        // Verify that the user is following the other user
        User foundFollower = userRepository.findByUsername("testUser").orElseThrow();
        assertTrue(foundFollower.getFollowing().contains(followUser));
    }

    @Test
    @Transactional
    public void testUnfollowUser() throws Exception {
        User followUser = new User();
        followUser.setUsername("followUser");
        followUser.setPassword("followUserPassword");
        userRepository.save(followUser);

        mockMvc.perform(post("/api/v1/users/{followerUsername}/follow/{followingUsername}", "testUser", "followUser"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/v1/users/{followerUsername}/unfollow/{followingUsername}", "testUser", "followUser"))
                .andExpect(status().isOk());

        User foundFollower = userRepository.findByUsername("testUser").orElseThrow();
        assertFalse(foundFollower.getFollowing().contains(followUser));
    }

    @Test
    @Transactional
    public void testGetFollowing() throws Exception {
        User followUser = new User();
        followUser.setUsername("followUser");
        followUser.setPassword("followUserPassword");
        userRepository.save(followUser);

        mockMvc.perform(post("/api/v1/users/{followerUsername}/follow/{followingUsername}", "testUser", "followUser"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/users/{username}/following", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("followUser"));
    }
}
