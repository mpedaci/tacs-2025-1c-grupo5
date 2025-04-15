package utn.tacs.grupo5.externalClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.externalClient.magicClient.MagicResponse;
import utn.tacs.grupo5.externalClient.pokemonClient.PokemonResponse;
import utn.tacs.grupo5.externalClient.yugiohClient.YuGiOhResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExternalCardClient {
    @Autowired
    private RestTemplate restTemplate;

    public List<Card> fetchCardsFromApi(Game game, String name) {
        if (game.getId() == 0L) {
            return fetchFromMagicApi(game, name);
        } else if (game.getId() == 1L) {
            return fetchFromPokemonApi(game, name);
        } else if (game.getId() == 2L) {
            return fetchFromYuGiOhApi(game, name);
        } else {
            throw new IllegalArgumentException("Game not supported: " + game.getName());
        }
    }

    private List<Card> fetchFromMagicApi(Game game, String name) {
        String url = "https://api.magicthegathering.io/v1/cards?name=" + name;
        MagicResponse response = restTemplate.getForObject(url, MagicResponse.class);

        if (response == null || response.cards == null) return Collections.emptyList();

        return response.cards.stream().map(dto -> {
            Card card = new Card();
            card.setGame(game);
            card.setName(dto.name);
            card.setExternalId(dto.id);
            card.setImageUrl(dto.imageUrl);
            return card;
        }).collect(Collectors.toList());
    }

    private List<Card> fetchFromPokemonApi(Game game, String name) {
        String url = "https://api.pokemontcg.io/v2/cards?q=name:" + name;
        PokemonResponse response = restTemplate.getForObject(url, PokemonResponse.class);

        if (response == null || response.data == null) return Collections.emptyList();

        return response.data.stream().map(dto -> {
            Card card = new Card();
            card.setGame(game);
            card.setName(dto.name);
            card.setExternalId(dto.id);
            card.setImageUrl(dto.images.large);
            return card;
        }).collect(Collectors.toList());
    }

    private List<Card> fetchFromYuGiOhApi(Game game, String name) {
        String url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?name=" + name;
        YuGiOhResponse response = restTemplate.getForObject(url, YuGiOhResponse.class);

        if (response == null || response.data == null) return Collections.emptyList();

        return response.data.stream().map(dto -> {
            Card card = new Card();
            card.setGame(game);
            card.setName(dto.name);
            card.setExternalId(String.valueOf(dto.id));
            card.setImageUrl(dto.card_images.getFirst().image_url);
            return card;
        }).collect(Collectors.toList());
    }
}
