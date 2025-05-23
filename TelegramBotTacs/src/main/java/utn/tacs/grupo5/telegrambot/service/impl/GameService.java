package utn.tacs.grupo5.telegrambot.service.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.tacs.grupo5.telegrambot.dto.CardOutputDTO;
import utn.tacs.grupo5.telegrambot.dto.GameOutputDTO;
import utn.tacs.grupo5.telegrambot.service.IGameService;

import java.util.List;

@Service
public class GameService implements IGameService {
    private WebClient webClient;
    @Getter
    private List<GameOutputDTO> gamesOnMemory;

    public GameService(WebClient.Builder webClientBuilder,
                       @Value("${api.baseUrl}") String apiURL,
                       @Value("${api.gameEndpoint}") String apiEndpoint
    ) {
        this.webClient = webClientBuilder.baseUrl(apiURL+apiEndpoint).build();
        loadGames();
    }

    public void loadGames() {
        gamesOnMemory = webClient.get()
                .retrieve()
                .bodyToFlux(GameOutputDTO.class)
                .collectList()
                .block();
    }

    public String findGame(String gameName) {
        return gamesOnMemory.stream()
                .filter(game -> game.getTitle().equalsIgnoreCase(gameName))
                .findFirst()
                .map(GameOutputDTO::getId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

}
