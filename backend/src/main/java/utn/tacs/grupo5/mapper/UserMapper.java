package utn.tacs.grupo5.mapper;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.dto.user.UserOutputDto;
import utn.tacs.grupo5.entity.User;

@Component
public class UserMapper implements IMapper<User, UserInputDto, UserOutputDto> {

    @Override
    public UserOutputDto toDto(User user) {
        UserOutputDto dto = new UserOutputDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    @Override
    public User toEntity(UserInputDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

}
