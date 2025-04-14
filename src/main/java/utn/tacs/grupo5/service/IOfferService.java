package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.entity.offer.OfferStatus;

import java.util.List;

public interface IOfferService extends ICRUDService<Offer, Long, OfferInputDto> {
    List<Offer> getAll();
    List<Offer> getAllByPublicationId(Long publicationId);
    Offer getById(Long publicationId, Long offerId);
    void patch(Long offerId, Long postId, OfferStatus offerStatus);
    void delete(Long offerId, Long postId);
}
