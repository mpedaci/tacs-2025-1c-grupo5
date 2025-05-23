package utn.tacs.grupo5.telegrambot.service.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.tacs.grupo5.telegrambot.dto.RegisterInputDTO;
import utn.tacs.grupo5.telegrambot.dto.GameOutputDTO;
import utn.tacs.grupo5.telegrambot.dto.RegisterOutputDTO;
import utn.tacs.grupo5.telegrambot.service.IUserService;

import java.util.List;

@Service
public class UserService implements IUserService {
    private WebClient webClient;
    @Getter
    private List<GameOutputDTO> gamesOnMemory;

    public UserService(WebClient.Builder webClientBuilder,
                       @Value("${api.baseUrl}") String apiURL,
                       @Value("${api.userEndpoint}") String apiEndpoint
    ) {
        this.webClient = webClientBuilder.baseUrl(apiURL+apiEndpoint).build();
    }

    public RegisterOutputDTO registerUser(String name, String username, String password) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterInputDTO(name, username, password))
                .retrieve()
                .bodyToMono(RegisterOutputDTO.class)
                .block();
    }
}
