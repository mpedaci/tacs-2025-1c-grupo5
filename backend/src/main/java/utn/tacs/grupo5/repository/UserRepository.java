package utn.tacs.grupo5.repository;

import java.util.Optional;
import java.util.UUID;

import utn.tacs.grupo5.entity.User;

public interface UserRepository extends ICRUDRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

}
