package utn.tacs.grupo5.repository;

import java.util.List;
import java.util.Optional;

public interface ICRUDRepository<Entity, ID> {
    Entity save(Entity entity);

    Optional<Entity> findById(ID id);

    List<Entity> findAll();

    void deleteById(ID id);

}
