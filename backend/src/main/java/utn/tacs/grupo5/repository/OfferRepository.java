package utn.tacs.grupo5.repository;

import java.util.List;
import java.util.UUID;

import utn.tacs.grupo5.entity.post.Offer;

public interface OfferRepository extends ICRUDRepository<Offer, UUID> {

    List<Offer> findAllByPostId(UUID postId);

}
