package utn.tacs.grupo5.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.externalClient.ICardClient;
import utn.tacs.grupo5.service.IExternalCardService;

@Service
public class ExternalCardService implements IExternalCardService {

    private final List<ICardClient> cardClients;

    public ExternalCardService(List<ICardClient> cardClients) {
        this.cardClients = cardClients;
    }

    public List<Card> getCardsByName(Game game, String cardName) {
        ICardClient client = cardClients.stream()
                .filter(c -> c.getGame().equals(game.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No client found for game: " + game.getName()));
        return client.getCardsByName(cardName);
    }

}
