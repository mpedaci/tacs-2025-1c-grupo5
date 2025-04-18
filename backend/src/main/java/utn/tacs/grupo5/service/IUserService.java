package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.entity.User;

public interface IUserService extends ICRUDService<User, Long, UserInputDto> {

}
