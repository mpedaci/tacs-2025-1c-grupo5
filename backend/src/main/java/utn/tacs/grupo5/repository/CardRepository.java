package utn.tacs.grupo5.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import utn.tacs.grupo5.entity.card.Card;

public interface CardRepository extends MongoRepository<Card, UUID> {

    List<Card> findByGameId(UUID gameId);

}
