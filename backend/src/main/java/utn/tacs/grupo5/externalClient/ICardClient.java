package utn.tacs.grupo5.externalClient;

import java.util.List;

import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;

public interface ICardClient {

    List<Card> getCardsByName(String name);

    Game.Name getGame();

}
