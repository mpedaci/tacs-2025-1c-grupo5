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
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        System.out.println("User is admin: " + user.getAdmin());
        return dto;
    }

    @Override
    public User toEntity(UserInputDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAdmin(dto.getAdmin() != null ? dto.getAdmin() : false);
        return user;
    }

}
