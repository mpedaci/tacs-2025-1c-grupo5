package utn.tacs.grupo5.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.externalClient.ExternalCardClient;
import utn.tacs.grupo5.mapper.CardMapper;
import utn.tacs.grupo5.repository.CardRepository;
import utn.tacs.grupo5.repository.GameRepository;
import utn.tacs.grupo5.service.ICardService;

@Service
public class CardService implements ICardService {

    private final CardRepository cardRepository;
    private final GameRepository gameRepository;
    private final CardMapper cardMapper;

    @Autowired
    private ExternalCardClient externalCardClient;

    public CardService(CardRepository cardRepository, CardMapper cardMapper, GameRepository gameRepository) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.gameRepository = gameRepository;
    }

    @Override
    public Optional<Card> get(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public Card save(CardInputDto dto) {
        Card card = cardMapper.toEntity(dto);
        return cardRepository.save(card);
    }

    @Override
    public Card update(Long id, CardInputDto dto) {
        cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card not found"));
        Card card = cardMapper.toEntity(dto);
        card.setId(id);
        return cardRepository.save(card);
    }

    @Override
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    public List<Card> getAllByGameId(Long gameId, String name) {
        if (name == null || name.isEmpty()) {
            return cardRepository.findByGameId(gameId);
        }
        List<Card> cardsInDb = cardRepository.findAll().stream()
                .filter(card -> card.getGame().getId().equals(gameId))
                .filter(card -> card.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        if (cardsInDb.isEmpty()) {
            Game game = gameRepository.findById(gameId).get();
            if (game == null) {
                throw new NotFoundException("Game not found");
            }
            List<Card> cardsApi = externalCardClient.fetchCardsFromApi(game, name);
            if (cardsApi.isEmpty()) {
                throw new NotFoundException("No cards found");
            }
            for (Card card : cardsApi) {
                cardRepository.save(card);
            }
            cardsInDb = cardRepository.findAll().stream()
                    .filter(card -> card.getGame().getId().equals(gameId))
                    .filter(card -> card.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
            return cardsInDb;
        }
        return cardsInDb;
    }
}
