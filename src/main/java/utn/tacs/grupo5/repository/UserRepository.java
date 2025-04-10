package utn.tacs.grupo5.repository;

import java.util.Optional;

import utn.tacs.grupo5.entity.User;

public interface UserRepository extends ICRUDRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
