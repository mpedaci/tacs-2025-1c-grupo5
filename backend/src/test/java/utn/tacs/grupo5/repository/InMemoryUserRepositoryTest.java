package utn.tacs.grupo5.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.repository.impl.InMemoryUserRepository;

@ExtendWith(MockitoExtension.class)
public class InMemoryUserRepositoryTest {

    InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    void save_shouldSaveUser_whenUserDoenstExist() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId(), "User ID should be generated");
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals(1, userRepository.findAll().size(), "Repository should contain one user");
    }

    @Test
    void save_shouldUpdateSavedUser_whenUserExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        User savedUser = userRepository.save(user);

        User updatedUser = new User();
        updatedUser.setId(savedUser.getId());
        updatedUser.setUsername("updatedUser");
        updatedUser.setEmail("updated@example.com");

        User savedUpdatedUser = userRepository.save(updatedUser);

        assertEquals(savedUser.getId(), savedUpdatedUser.getId(), "User ID should remain the same");
        assertEquals("updatedUser", savedUpdatedUser.getUsername());
        assertEquals("updated@example.com", savedUpdatedUser.getEmail());
        assertEquals(1, userRepository.findAll().size(), "Repository should still contain one user");
    }

    @Test
    void save_shouldSaveUsersWithUniqueIds() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        assertNotEquals(savedUser1.getId(), savedUser2.getId(), "Each user should have a unique ID");
        assertEquals(2, userRepository.findAll().size(), "Repository should contain two users");
    }

    @Test
    void findById_shouldReturnUser_whenUserExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals(savedUser.getId(), foundUser.get().getId());
    }

    @Test
    void findById_shouldReturnEmpty_whenUserDoesNotExist() {
        Optional<User> foundUser = userRepository.findById(999L);

        assertFalse(foundUser.isPresent(), "User should not be found");
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        userRepository.save(user1);
        userRepository.save(user2);

        assertEquals(2, userRepository.findAll().size(), "Repository should contain two users");
    }

    @Test
    void deleteById_shouldRemoveUser_whenUserExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        assertEquals(0, userRepository.findAll().size(), "Repository should be empty after deletion");
    }

    @Test
    void deleteById_shouldDoNothing_whenUserDoesNotExist() {
        Long nonExistingUserId = 999L;
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        User savedUser = userRepository.save(user);

        userRepository.deleteById(nonExistingUserId);

        assertNotEquals(savedUser.getId(), nonExistingUserId);
        assertEquals(1, userRepository.findAll().size(), "Repository should remain with one user");
    }

    @Test
    void findByEmail_shouldReturnUser_whenEmailExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void findByEmail_shouldReturnEmpty_whenEmailDoesNotExist() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent(), "User should not be found");
    }

    @Test
    void findByUsername_shouldReturnUser_whenUsernameExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("testUser");

        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void findByUsername_shouldReturnEmpty_whenUsernameDoesNotExist() {
        Optional<User> foundUser = userRepository.findByUsername("nonexistentUser");

        assertFalse(foundUser.isPresent(), "User should not be found");
    }

}
