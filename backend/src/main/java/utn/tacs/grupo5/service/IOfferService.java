package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.post.Offer;

import java.util.List;

public interface IOfferService extends ICRUDService<Offer, Long, OfferInputDto> {

    List<Offer> getAllByPostId(Long postId);

    void updateStatus(Long offerId, Offer.Status newState);

}
