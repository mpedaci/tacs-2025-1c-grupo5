package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.entity.User;

import java.util.UUID;

public interface IUserService extends ICRUDService<User, UUID, UserInputDto> {

}
