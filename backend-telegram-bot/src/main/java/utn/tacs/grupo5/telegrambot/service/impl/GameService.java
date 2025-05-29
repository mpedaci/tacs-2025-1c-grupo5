package utn.tacs.grupo5.telegrambot.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.telegrambot.dto.card.CardOutputDto;
import utn.tacs.grupo5.telegrambot.dto.game.GameOutputDto;
import utn.tacs.grupo5.telegrambot.service.IGameService;

import java.util.List;

@Service
public class GameService extends BaseWebClient implements IGameService {
    @Override
    public List<GameOutputDto> getGames(String token) {
        return webClient.get()
                .uri("/games")
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(GameOutputDto.class)
                .collectList()
                .block();
    }

    @Override
    public List<CardOutputDto> findCards(String token, String gameId, String cardName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/games/{gameId}/cards")
                        .queryParam("name", cardName)
                        .build(gameId))
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(CardOutputDto.class)
                .collectList()
                .block();
    }
}
