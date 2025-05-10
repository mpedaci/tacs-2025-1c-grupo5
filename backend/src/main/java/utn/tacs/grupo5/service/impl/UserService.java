package utn.tacs.grupo5.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import utn.tacs.grupo5.controller.exceptions.ConflictException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.mapper.UserMapper;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> get(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(UserInputDto dto) {
        userRepository.findByUsername(dto.getUsername())
                .ifPresent(user -> {
                    throw new ConflictException("Username already exists");
                });

        String hashedPassword = hashPassword(dto.getPassword());

        User user = userMapper.toEntity(dto);
        user.setPassword(hashedPassword);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User update(UUID id, UserInputDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        User user = userMapper.toEntity(dto);
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        user.setId(id);
        user.setCreatedAt(existingUser.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

}
