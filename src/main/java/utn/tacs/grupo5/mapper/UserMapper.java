package utn.tacs.grupo5.mapper;

import utn.tacs.grupo5.dto.UserDto;
import utn.tacs.grupo5.entity.User;

public class UserMapper implements IMapper<User, UserDto> {

    @Override
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        return dto;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        System.out.println(user.toString());
        return user;
    }

}
