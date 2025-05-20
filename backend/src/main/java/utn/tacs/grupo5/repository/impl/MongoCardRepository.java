package utn.tacs.grupo5.repository.impl;

import org.springframework.data.mongodb.repository.MongoRepository;
import utn.tacs.grupo5.entity.card.Card;

import java.util.List;
import java.util.UUID;

public interface MongoCardRepository extends MongoRepository<Card, UUID> {
    List<Card> findByGameId(UUID gameId);
}
