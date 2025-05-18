package utn.tacs.grupo5.service.impl;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.mapper.CardMapper;
import utn.tacs.grupo5.repository.impl.MongoCardRepository;
import utn.tacs.grupo5.repository.impl.MongoGameRepository;
import utn.tacs.grupo5.service.ICardService;
import utn.tacs.grupo5.service.IExternalCardService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardService implements ICardService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CardService.class);
    private final MongoCardRepository cardRepository;
    private final MongoGameRepository gameRepository;
    private final CardMapper cardMapper;
    private final IExternalCardService externalCardClient;

    public CardService(
            MongoCardRepository cardRepository,
            CardMapper cardMapper,
            MongoGameRepository gameRepository,
            IExternalCardService externalCardClient) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.gameRepository = gameRepository;
        this.externalCardClient = externalCardClient;
    }

    @Override
    public Optional<Card> get(UUID id) {
        Optional<Card> card = cardRepository.findById(id);
        card.ifPresent(c -> c.setGame(gameRepository.findById(c.getGameId()).orElseThrow(() -> {
            delete(id);
            return new NotFoundException("Card game not found");
        })));
        return card;
    }

    @Override
    public Card save(CardInputDto dto) {
        Card card = cardMapper.toEntity(dto);
        return cardRepository.save(card);
    }

    @Override
    public Card update(UUID id, CardInputDto dto) {
        cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card not found"));
        Card card = cardMapper.toEntity(dto);
        card.setId(id);
        return cardRepository.save(card);
    }

    @Override
    public void delete(UUID id) {
        cardRepository.deleteById(id);
    }

    @Override
    public List<Card> getAllByGameId(UUID gameId, String name) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game not found"));

        if (name == null || name.isEmpty()) {
            return cardRepository.findByGameId(gameId);
        }

        List<Card> cardsInDb = cardRepository.findAll().stream()
                .filter(card -> card.getGame().getId().equals(gameId))
                .filter(card -> card.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        logger.info("Filtered cards in DB for game {}: {}", game.getName().name(), cardsInDb.size());

        if (cardsInDb.isEmpty()) {
            List<Card> cardsApi = externalCardClient.getCardsByName(game, name);
            if (!cardsApi.isEmpty()) {
                // Filter out cards that are already in the database
                cardsApi.stream()
                        .filter(card -> !cardsInDb.stream().map(Card::getExternalId).toList()
                                .contains(card.getExternalId()))
                        .forEach(card -> {
                            card.setGame(game);
                            cardRepository.save(card);
                        });

                logger.info("Saved {} new cards from external API for game {}", cardsApi.size(), game.getName().name());

                return cardRepository.findAll().stream()
                        .filter(card -> card.getGame().getId().equals(gameId))
                        .filter(card -> card.getName().toLowerCase().contains(name.toLowerCase()))
                        .toList();
            }
        }

        return cardsInDb;
    }
}
