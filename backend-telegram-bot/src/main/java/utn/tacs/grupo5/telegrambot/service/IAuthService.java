package utn.tacs.grupo5.telegrambot.service;

import utn.tacs.grupo5.telegrambot.dto.auth.AuthOutputDto;

public interface IAuthService {
    AuthOutputDto logInUser(String username, String password);
}
