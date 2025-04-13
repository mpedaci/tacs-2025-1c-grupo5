package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.offer.Offer;

import java.util.List;

public interface IOfferService extends ICRUDService<Offer, Long, OfferInputDto> {
    List<Offer> getAll();
    List<Offer> getAllByPublicationId(Long publicationId);
    Offer getById(Long publicationId, Long offerId);
}
