package utn.tacs.grupo5.repository;

import java.util.List;

import utn.tacs.grupo5.entity.card.Card;

public interface CardRepository extends ICRUDRepository<Card, Long> {

    List<Card> findByGameId(Long gameId);

}
