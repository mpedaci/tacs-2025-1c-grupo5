package utn.tacs.grupo5.repository.impl;

import org.springframework.data.mongodb.repository.MongoRepository;
import utn.tacs.grupo5.entity.post.Offer;

import java.util.List;
import java.util.UUID;

public interface MongoOfferRepository extends MongoRepository<Offer, UUID> {
    List<Offer> findAllByPostId(UUID postId);
}
