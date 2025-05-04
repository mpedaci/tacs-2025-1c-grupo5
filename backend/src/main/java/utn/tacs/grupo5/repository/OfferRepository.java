package utn.tacs.grupo5.repository;

import java.util.List;

import utn.tacs.grupo5.entity.post.Offer;

public interface OfferRepository extends ICRUDRepository<Offer, Long> {

    List<Offer> findAllByPostId(Long postId);

}
