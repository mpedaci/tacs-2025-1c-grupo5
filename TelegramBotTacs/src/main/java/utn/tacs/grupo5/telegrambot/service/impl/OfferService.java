package utn.tacs.grupo5.telegrambot.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.tacs.grupo5.telegrambot.dto.OfferOutputDTO;
import utn.tacs.grupo5.telegrambot.dto.PostCreationDTO;
import utn.tacs.grupo5.telegrambot.dto.IdOutputDTO;
import utn.tacs.grupo5.telegrambot.service.IOfferService;

@Service
public class OfferService implements IOfferService {
    private WebClient webClient;

    public OfferService(WebClient.Builder webClientBuilder,
                       @Value("${api.baseUrl}") String apiURL,
                       @Value("${api.offerEndpoint}") String apiEndpoint
    ) {
        this.webClient = webClientBuilder.baseUrl(apiURL+apiEndpoint).build();
    }

    public String createOffer(OfferOutputDTO offerOutputDTO) {
        return webClient.post()
                .bodyValue(offerOutputDTO)
                .retrieve()
                .bodyToMono(IdOutputDTO.class)
                .map(IdOutputDTO::getId)
                .block();
    }

}
