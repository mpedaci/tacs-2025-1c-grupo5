package utn.tacs.grupo5.telegrambot.service;

import utn.tacs.grupo5.telegrambot.dto.card.CardOutputDto;
import utn.tacs.grupo5.telegrambot.dto.game.GameOutputDto;

import java.util.List;

public interface IGameService {
    List<GameOutputDto> getGames(String token);
    List<CardOutputDto> findCards(String token, String gameId, String cardName);
}
