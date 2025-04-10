package utn.tacs.grupo5.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import utn.tacs.grupo5.controller.exceptions.ConflictException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.UserDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.mapper.UserMapper;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.IUserService;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(UserDto dto) {
        userRepository.findByEmail(dto.getEmail()).ifPresent(user -> {
            throw new ConflictException("User alredy exists");
        });
        UserMapper userMapper = new UserMapper();

        // TODO: Hash password
        // String hashedPassword = implement
        // user.setPassword(hashedPassword);

        User user = userMapper.toEntity(dto);
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, UserDto dto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        UserMapper userMapper = new UserMapper();
        User user = userMapper.toEntity(dto);
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
