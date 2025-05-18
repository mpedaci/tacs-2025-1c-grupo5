package utn.tacs.grupo5.repository;

import utn.tacs.grupo5.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ICRUDRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}
