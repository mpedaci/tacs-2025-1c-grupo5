package utn.tacs.grupo5.service;

import java.util.Optional;

public interface ICRUDService<Entity, ID, Dto> {
    Optional<Entity> get(ID id);

    Entity save(Dto dto);

    Entity update(ID id, Dto dto);

    void delete(ID id);
}
