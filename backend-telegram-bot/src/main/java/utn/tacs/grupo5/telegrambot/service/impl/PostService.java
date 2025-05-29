package utn.tacs.grupo5.telegrambot.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;
import utn.tacs.grupo5.telegrambot.dto.ConservationStatus;
import utn.tacs.grupo5.telegrambot.dto.post.PostInputDto;
import utn.tacs.grupo5.telegrambot.dto.post.PostOutputDto;
import utn.tacs.grupo5.telegrambot.service.IPostService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PostService extends BaseWebClient implements IPostService {
    @Override
    public PostOutputDto createPost(String token, PostInputDto postInputDto) {

        return webClient.post()
                .uri("/posts")
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(postInputDto)
                .retrieve()
                .bodyToMono(PostOutputDto.class)
                .block();
    }

    @Override
    public List<PostOutputDto> getPosts(String token, String gameId, String cardName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/posts")
                        .queryParam("gameId", gameId)
                        .queryParam("name", cardName)
                        .build())
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(PostOutputDto.class)
                .collectList()
                .block();
    }
}
