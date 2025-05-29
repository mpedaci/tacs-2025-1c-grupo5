package utn.tacs.grupo5.telegrambot.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.telegrambot.dto.user.UserInputDto;
import utn.tacs.grupo5.telegrambot.dto.user.UserOutputDto;
import utn.tacs.grupo5.telegrambot.service.IUserService;

@Service
public class UserService extends BaseWebClient implements IUserService {
    @Override
    public UserOutputDto registerUser(String name, String username, String password) {
        return webClient.post()
                .uri("/users")
                .bodyValue(new UserInputDto(name, username, password))
                .retrieve()
                .bodyToMono(UserOutputDto.class)
                .block();
    }
}
