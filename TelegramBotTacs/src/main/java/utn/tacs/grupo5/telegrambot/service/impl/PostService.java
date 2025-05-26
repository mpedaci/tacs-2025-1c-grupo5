package utn.tacs.grupo5.telegrambot.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.tacs.grupo5.telegrambot.dto.PostCreationDTO;
import utn.tacs.grupo5.telegrambot.dto.PostInputDTO;
import utn.tacs.grupo5.telegrambot.dto.IdOutputDTO;
import utn.tacs.grupo5.telegrambot.service.IPostService;

import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {
    private WebClient webClient;

    public PostService(WebClient.Builder webClientBuilder,
                       @Value("${api.baseUrl}") String apiURL,
                       @Value("${api.postEndpoint}") String apiEndpoint
    ) {
        this.webClient = webClientBuilder.baseUrl(apiURL+apiEndpoint).build();
    }

    public String createPost(PostCreationDTO postCreationDto) {
        return webClient.post()
                .bodyValue(postCreationDto)
                .retrieve()
                .bodyToMono(IdOutputDTO.class)
                .map(IdOutputDTO::getId)
                .block();
    }

    public List<PostInputDTO> getPosts(String cardName, String gameId, String state) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParamIfPresent("name", Optional.ofNullable(cardName))
                        .queryParamIfPresent("gameId", Optional.ofNullable(gameId))
                        .queryParamIfPresent("state", Optional.ofNullable(state))
                        .build())
                .retrieve()
                .bodyToFlux(PostInputDTO.class)
                .collectList()
                .block();
    }
}