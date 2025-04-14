package utn.tacs.grupo5.repository;

import utn.tacs.grupo5.entity.offer.OfferedCard;

public interface OfferedCardRepository extends ICRUDRepository<OfferedCard, Long> {
    void deleteByCard(OfferedCard card);
}
