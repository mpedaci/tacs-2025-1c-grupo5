package utn.tacs.grupo5.telegrambot.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.tacs.grupo5.telegrambot.dto.PostCreationDTO;
import utn.tacs.grupo5.telegrambot.dto.PostInputDTO;
import utn.tacs.grupo5.telegrambot.dto.PostOutputDTO;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
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
                .bodyToMono(PostOutputDTO.class)
                .map(PostOutputDTO::getId)
                .block();
    }

    public List<PostInputDTO> getPosts(String cardName, String gameId, String state) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts")
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