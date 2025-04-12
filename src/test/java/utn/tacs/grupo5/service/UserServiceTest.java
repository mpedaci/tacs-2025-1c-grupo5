package utn.tacs.grupo5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import utn.tacs.grupo5.controller.exceptions.ConflictException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.impl.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void get_shouldReturnUser_whenUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.get(userId);

        assertNotNull(result);
        assertEquals(userId, result.get().getId());
    }

    @Test
    void get_shouldReturnEmptyOptional_whenUserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.get(userId);

        assertEquals(Optional.empty(), result);
    }

    @Test
    void save_shouldThrowConflictException_whenEmailAlreadyExists() {
        UserInputDto dto = new UserInputDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setPassword("password");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(ConflictException.class, () -> userService.save(dto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void save_shouldThrowConflictException_whenUsernameAlreadyExists() {
        UserInputDto dto = new UserInputDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setPassword("password");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(new User()));

        assertThrows(ConflictException.class, () -> userService.save(dto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void save_shouldSaveUser_whenValidInput() {
        UserInputDto dto = new UserInputDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setPassword("password");
        dto.setFirstName("Test");
        dto.setLastName("User");
        dto.setPhone("1234567890");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        userService.save(dto);
        verify(userRepository).save(userCaptor.capture());
        User user = userCaptor.getValue();

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getPhone(), user.getPhone());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getUsername(), user.getUsername());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertEquals(true, passwordEncoder.matches(dto.getPassword(), user.getPassword()));
    }

    @Test
    void update_shouldThrowNotFoundException_whenUserDoesNotExist() {
        Long userId = 1L;
        UserInputDto dto = new UserInputDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(userId, dto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void update_shouldUpdateUser_whenUserExists() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setCreatedAt(LocalDateTime.now());

        UserInputDto dto = new UserInputDto();
        dto.setEmail("updated@example.com");
        dto.setUsername("updateduser");
        dto.setFirstName("Updated");
        dto.setLastName("User");
        dto.setPhone("0987654321");
        dto.setPassword("newpassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.update(userId, dto);

        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
        assertEquals(dto.getEmail(), updatedUser.getEmail());
        assertEquals(dto.getUsername(), updatedUser.getUsername());
        assertEquals(existingUser.getCreatedAt(), updatedUser.getCreatedAt());
        assertNotEquals(updatedUser.getCreatedAt(), updatedUser.getUpdatedAt());
        assertNotNull(updatedUser.getUpdatedAt());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertEquals(true, passwordEncoder.matches(dto.getPassword(), updatedUser.getPassword()));
    }

    @Test
    void delete_shouldDeleteUser_whenUserExists() {
        Long userId = 1L;

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }

}
