package utn.tacs.grupo5.service;

import java.util.List;

import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;

public interface IExternalCardService {

    List<Card> getCardsByName(Game game, String name);

}
