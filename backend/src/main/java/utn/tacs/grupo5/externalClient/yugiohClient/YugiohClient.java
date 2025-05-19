package utn.tacs.grupo5.externalClient.yugiohClient;

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
public class YugiohClient implements ICardClient {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(YugiohClient.class);
    private WebClient yugiohClient;
    private final Game.Name gameName = Game.Name.YUGIOH;

    @Value("${YUGIOH_API_URL}")
    private String apiBaseUrl;

    @PostConstruct
    public void init() {
        this.yugiohClient = WebClient.builder()
                .baseUrl(apiBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .build();
    }

    @Override
    public List<Card> getCardsByName(String name) {
        YuGiOhResponse response;

        try {
            response = yugiohClient.get()
                    .uri(uriBuilder -> {
                        uriBuilder.path("cardinfo.php");
                        // .queryParam("name", name);
                        logger.debug(gameName.name() + " URI: {}", uriBuilder.toUriString());
                        return uriBuilder.build();
                    })
                    .retrieve()
                    .bodyToMono(YuGiOhResponse.class)
                    .block();
        } catch (Exception e) {
            logger.info("No cards Yu-Gi-Oh cards fetched: {}", e.getMessage());
            return Collections.emptyList();
        }

        logger.info("Fetched a total of {} cards", response != null ? response.data.size() : 0);

        if (response == null || response.data == null)
            return Collections.emptyList();

        return response.data.stream().map(dto -> {
            logger.info("Fetched yugioh card: {}", dto.name);
            Card card = new Card();
            card.setName(dto.name);
            card.setExternalId(String.valueOf(dto.id));
            card.setImageUrl(dto.card_images.getFirst().image_url);
            return card;
        }).collect(Collectors.toList());
    }

    @Override
    public Game.Name getGame() {
        return gameName;
    }

}
