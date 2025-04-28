package utn.tacs.grupo5.service;

import java.util.List;
import java.util.UUID;

import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.entity.card.Card;

public interface ICardService extends ICRUDService<Card, UUID, CardInputDto> {

    List<Card> getAllByGameId(UUID gameId, String name);
}
