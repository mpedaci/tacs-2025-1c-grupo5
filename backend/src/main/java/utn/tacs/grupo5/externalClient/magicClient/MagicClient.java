package utn.tacs.grupo5.externalClient.magicClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.externalClient.ICardClient;

@Service
public class MagicClient implements ICardClient {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MagicClient.class);
    private WebClient magicClient;
    private final Game.Name gameName = Game.Name.MAGIC;

    @Value("${external.api.magic.url}")
    private String apiBaseUrl;

    @PostConstruct
    public void init() {
        this.magicClient = WebClient.builder()
                .baseUrl(apiBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .build();
    }

    @Override
    public List<Card> getCardsByName(String name) {
        MagicResponse response = magicClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("cards")
                            .queryParam("name", name);
                    logger.debug(gameName.name() + " URI: {}", uriBuilder.toUriString());
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(MagicResponse.class)
                .block();

        logger.info("Fetched a total of {} cards", response != null ? response.cards.size() : 0);

        if (response == null || response.cards == null)
            return Collections.emptyList();

        return response.cards.stream().map(dto -> {
            Card card = new Card();
            card.setName(dto.name);
            card.setExternalId(String.valueOf(dto.id));
            card.setImageUrl(dto.imageUrl);
            return card;
        }).collect(Collectors.toList());
    }

    @Override
    public Game.Name getGame() {
        return gameName;
    }

}
