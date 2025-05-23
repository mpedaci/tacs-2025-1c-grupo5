package utn.tacs.grupo5.telegrambot.service.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.tacs.grupo5.telegrambot.dto.CardOutputDTO;
import utn.tacs.grupo5.telegrambot.dto.GameOutputDTO;
import utn.tacs.grupo5.telegrambot.service.ICardService;

import java.util.List;

@Service
public class CardService implements ICardService {

    private WebClient webClient;
    @Getter
    private List<GameOutputDTO> gamesOnMemory;

    public CardService(WebClient.Builder webClientBuilder,
                       @Value("${api.baseUrl}") String apiURL,
                       @Value("${api.cardEndpoint}") String apiEndpoint
    ) {
        this.webClient = webClientBuilder.baseUrl(apiURL+apiEndpoint).build();
    }

    public List<CardOutputDTO> findCard(String gameId, String cardName) {
        return webClient.get()
                .uri("/" + gameId + "/" + cardName)
                .retrieve()
                .bodyToFlux(CardOutputDTO.class)
                .collectList()
                .block();
    }

}
