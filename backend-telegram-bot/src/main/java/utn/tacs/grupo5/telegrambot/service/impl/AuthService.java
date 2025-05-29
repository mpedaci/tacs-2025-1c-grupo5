package utn.tacs.grupo5.telegrambot.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.telegrambot.dto.auth.AuthInputDto;
import utn.tacs.grupo5.telegrambot.dto.auth.AuthOutputDto;
import utn.tacs.grupo5.telegrambot.service.IAuthService;

@Service
public class AuthService extends BaseWebClient implements IAuthService {
    @Override
    public AuthOutputDto logInUser(String username, String password) {
        return webClient.post()
                .uri("/auth/login")
                .bodyValue(new AuthInputDto(username, password))
                .retrieve()
                .bodyToMono(AuthOutputDto.class)
                .block();
    }
}
