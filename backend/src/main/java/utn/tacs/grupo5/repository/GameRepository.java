package utn.tacs.grupo5.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import utn.tacs.grupo5.entity.card.Game;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends MongoRepository<Game, UUID> {
    Optional<Game> findByTitle(String name);
}
