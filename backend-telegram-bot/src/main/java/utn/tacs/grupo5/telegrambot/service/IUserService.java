package utn.tacs.grupo5.telegrambot.service;

import utn.tacs.grupo5.telegrambot.dto.user.UserOutputDto;

public interface IUserService {
    UserOutputDto registerUser(String name, String username, String password);
}
