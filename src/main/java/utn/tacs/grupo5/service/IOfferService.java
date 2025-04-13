package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.entity.Offer;

import java.util.List;
import java.util.Optional;

public interface IOfferService extends ICRUDService<Offer, Long, OfferInputDto> {
    List<Offer> getAll();
    List<Offer> getAllByPublicationId(Long publicationId);
    Offer getById(Long publicationId, Long offerId);
}
