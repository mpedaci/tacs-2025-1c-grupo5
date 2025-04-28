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
import java.util.UUID;

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
import utn.tacs.grupo5.mapper.UserMapper;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.impl.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    void get_shouldReturnOptionalUser_whenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.get(userId);

        assertNotNull(result);
        assertEquals(userId, result.get().getId());
    }

    @Test
    void get_shouldReturnEmptyOptional_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserInputDto dto = new UserInputDto();
        dto.setEmail("test@example.com");
        dto.setUsername("testuser");
        dto.setPassword("password");
        dto.setFirstName("Test");
        dto.setLastName("User");
        dto.setPhone("1234567890");

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());

        when(userMapper.toEntity(dto)).thenReturn(user);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(userMapper.toEntity(dto)).thenReturn(user);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user2 = invocation.getArgument(0);
            user2.setId(UUID.randomUUID());
            return user2;
        });

        userService.save(dto);
        verify(userRepository).save(userCaptor.capture());
        User result = userCaptor.getValue();

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(dto.getFirstName(), result.getFirstName());
        assertEquals(dto.getLastName(), result.getLastName());
        assertEquals(dto.getPhone(), result.getPhone());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getUsername(), result.getUsername());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());

        assertEquals(true, passwordEncoder.matches(dto.getPassword(), result.getPassword()));
    }

    @Test
    void update_shouldThrowNotFoundException_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UserInputDto dto = new UserInputDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(userId, dto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void update_shouldUpdateUser_whenUserExists() {
        UUID userId = UUID.randomUUID();
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

        User user = new User();
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userMapper.toEntity(dto)).thenReturn(user);
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
        UUID userId = UUID.randomUUID();

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }

}
