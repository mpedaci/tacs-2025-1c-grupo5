package utn.tacs.grupo5.repository;

import utn.tacs.grupo5.entity.card.Card;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends ICRUDRepository<Card, UUID> {
    List<Card> findByGameId(UUID gameId);
}
