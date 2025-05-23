package utn.tacs.grupo5.telegrambot.service.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.tacs.grupo5.telegrambot.dto.LogInRequestDTO;
import utn.tacs.grupo5.telegrambot.dto.GameOutputDTO;
import utn.tacs.grupo5.telegrambot.service.IAutService;

import java.util.List;

@Service
public class AutService implements IAutService {
    private WebClient webClient;
    @Getter
    private List<GameOutputDTO> gamesOnMemory;

    public AutService(WebClient.Builder webClientBuilder,
                       @Value("${api.baseUrl}") String apiURL,
                       @Value("${api.autEndpoint}") String apiEndpoint
    ) {
        this.webClient = webClientBuilder.baseUrl(apiURL+apiEndpoint).build();
    }

    public String logInUser(String username, String password) {
        return webClient.post()
                .uri("/login")
                .bodyValue(new LogInRequestDTO(username, password))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
