package org.example.followflow.repository;

import jakarta.transaction.Transactional;
import org.example.followflow.config.TestAppConfig;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        userRepository.save(user);

        User foundUser = userRepository.findByUsername("testUser")
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertEquals("testUser", foundUser.getUsername());
        assertEquals("testPassword", foundUser.getPassword());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByUsername_UserNotFound() {
        assertFalse(userRepository.findByUsername("unknownUser").isPresent());
    }
}
