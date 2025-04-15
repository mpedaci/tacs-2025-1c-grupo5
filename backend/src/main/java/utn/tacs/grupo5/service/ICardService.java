package utn.tacs.grupo5.service;

import java.util.List;

import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.entity.card.Card;

public interface ICardService extends ICRUDService<Card, Long, CardInputDto> {

    List<Card> getAllByGameId(Long gameId, String name);
}
