package utn.tacs.grupo5.externalClient.pokemonClient;

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
public class PokemonClient implements ICardClient {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PokemonClient.class);
    private WebClient pokemonClient;
    private final Game.Name gameName = Game.Name.POKEMON;

    @Value("${POKEMON_API_URL}")
    private String apiBaseUrl;

    @PostConstruct
    public void init() {
        this.pokemonClient = WebClient.builder()
                .baseUrl(apiBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                .build();
    }

    @Override
    public List<Card> getCardsByName(String name) {
        logger.info("Fetching " + gameName.name() + " cards for card name: {}", name);
        String param = "name:" + name;
        PokemonResponse response = pokemonClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("cards")
                            .queryParam("q", param);
                    logger.debug(gameName.name() + " URI: {}", uriBuilder.toUriString());
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(PokemonResponse.class)
                .block();

        logger.info("Fetched a total of {} cards", response != null ? response.data.size() : 0);

        if (response == null || response.data == null)
            return Collections.emptyList();

        return response.data.stream().map(dto -> {
            logger.info("Fetched pokemon card: {}", dto.name);
            Card card = new Card();
            card.setName(dto.name);
            card.setExternalId(dto.id);
            card.setImageUrl(dto.images.large);
            return card;
        }).collect(Collectors.toList());
    }

    @Override
    public Game.Name getGame() {
        return gameName;
    }

}
