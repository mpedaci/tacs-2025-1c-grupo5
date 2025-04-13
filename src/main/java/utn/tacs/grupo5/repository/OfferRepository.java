package utn.tacs.grupo5.repository;

import utn.tacs.grupo5.entity.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends ICRUDRepository<Offer, Long> {

    Optional<Offer> findByPublicationId(Long publicationId);

    Optional<Offer> findById(Long id);

    void deleteByPublicationId(Long publicationId);

    void deleteByUserId(Long userId);

    List<Offer> findAllByPublicationId(Long publicationId);


}
