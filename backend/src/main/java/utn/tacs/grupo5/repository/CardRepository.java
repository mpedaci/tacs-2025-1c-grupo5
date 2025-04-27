package utn.tacs.grupo5.repository;

import java.util.List;
import java.util.UUID;

import utn.tacs.grupo5.entity.card.Card;

public interface CardRepository extends ICRUDRepository<Card, UUID> {

    List<Card> findByGameId(UUID gameId);

}
