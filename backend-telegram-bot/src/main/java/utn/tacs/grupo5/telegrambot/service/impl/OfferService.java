package utn.tacs.grupo5.telegrambot.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.telegrambot.dto.offer.OfferInputDto;
import utn.tacs.grupo5.telegrambot.dto.offer.OfferOutputDto;
import utn.tacs.grupo5.telegrambot.service.IOfferService;

@Service
public class OfferService extends BaseWebClient implements IOfferService {
    @Override
    public OfferOutputDto createOffer(String token, OfferInputDto offerInputDto) {
        return webClient.post()
                .uri("/offers")
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(offerInputDto)
                .retrieve()
                .bodyToMono(OfferOutputDto.class)
                .block();
    }
}
