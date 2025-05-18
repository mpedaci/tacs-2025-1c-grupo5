package utn.tacs.grupo5.repository.impl;

import org.springframework.data.mongodb.repository.MongoRepository;
import utn.tacs.grupo5.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface MongoUserRepository extends MongoRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
