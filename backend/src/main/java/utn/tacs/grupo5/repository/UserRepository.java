package utn.tacs.grupo5.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import utn.tacs.grupo5.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
