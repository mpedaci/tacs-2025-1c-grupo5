package utn.tacs.grupo5.telegrambot.service;

import utn.tacs.grupo5.telegrambot.dto.offer.OfferInputDto;
import utn.tacs.grupo5.telegrambot.dto.offer.OfferOutputDto;

public interface IOfferService {
    OfferOutputDto createOffer(String token, OfferInputDto offerInputDto);
}
