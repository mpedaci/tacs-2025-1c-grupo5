package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.post.Offer;

import java.util.List;
import java.util.UUID;

public interface IOfferService extends ICRUDService<Offer, UUID, OfferInputDto> {

    List<Offer> getAllByPostId(UUID postId);

    void updateStatus(UUID offerId, Offer.Status newState);

}
