package utn.tacs.grupo5.repository;

import utn.tacs.grupo5.entity.offer.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends ICRUDRepository<Offer, Long> {

    Optional<Offer> findByPostId(Long postId);

    Optional<Offer> findById(Long id);

    void deleteByPostId(Long postId);

    void deleteByUserId(Long userId);

    List<Offer> findAllByPostId(Long postId);

}
