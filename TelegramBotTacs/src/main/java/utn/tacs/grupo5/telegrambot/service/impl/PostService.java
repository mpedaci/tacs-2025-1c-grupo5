package utn.tacs.grupo5.telegrambot.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.tacs.grupo5.telegrambot.dto.PostInputDTO;
import utn.tacs.grupo5.telegrambot.dto.PostOutputDTO;

@Service
public class PostService {
    private WebClient webClient;

    public PostService(WebClient.Builder webClientBuilder,
                       @Value("${api.baseUrl}") String apiURL,
                       @Value("${api.postEndpoint}") String apiEndpoint
    ) {
        this.webClient = webClientBuilder.baseUrl(apiURL+apiEndpoint).build();
    }

    public String createPost(PostInputDTO postInputDto) {
        return webClient.post()
                .bodyValue(postInputDto)
                .retrieve()
                .bodyToMono(PostOutputDTO.class)
                .map(PostOutputDTO::getId)
                .block();
    }
}