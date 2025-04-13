package utn.tacs.grupo5.mapper;

import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.dto.offer.OfferOutputDto;
import utn.tacs.grupo5.entity.Offer;

public class OfferMapper implements IMapper<Offer, OfferInputDto, OfferOutputDto> {
    @Override
    public OfferOutputDto toDto(Offer offer) {
        OfferOutputDto dto = new OfferOutputDto();
        dto.setId(offer.getId());
        dto.setOfferUser(offer.getOfferUser());
        dto.setPublication(offer.getPublication());
        dto.setOfferedCards(offer.getOfferedCards());
        dto.setMoney(offer.getMoney());
        dto.setState(offer.getState());
        dto.setPublicationDate(offer.getPublicationDate());
        dto.setFinished(offer.getOfferEndDate());
        return dto;
    }

    @Override
    public Offer toEntity(OfferInputDto offerInputDto) {
        Offer offer = new Offer();
        offer.setOfferUser(offerInputDto.getOfferUser());
        offer.setPublication(offerInputDto.getPublication());
        offer.setOfferedCards(offerInputDto.getOfferedCards());
        offer.setMoney(offerInputDto.getMoney());
        offer.setState(offerInputDto.getState());
                return offer;
    }
}
