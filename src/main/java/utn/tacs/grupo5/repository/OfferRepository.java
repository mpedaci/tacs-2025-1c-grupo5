package utn.tacs.grupo5.repository;

import utn.tacs.grupo5.entity.Offer;

public interface OfferRepository extends ICRUDRepository<Offer, Long> {
    Offer findByPublicationId(Long publicationId);

    void deleteByPublicationId(Long publicationId);

    void deleteByUserId(Long userId);
}
