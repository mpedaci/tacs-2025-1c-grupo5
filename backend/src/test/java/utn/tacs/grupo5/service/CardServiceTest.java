package utn.tacs.grupo5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.mapper.CardMapper;
import utn.tacs.grupo5.repository.impl.MongoCardRepository;
import utn.tacs.grupo5.repository.impl.MongoGameRepository;
import utn.tacs.grupo5.service.impl.CardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    MongoCardRepository cardRepository;

    @Mock
    CardMapper cardMapper;

    @Mock
    MongoGameRepository gameRepository;

    @Mock
    IExternalCardService externalCardClient;

    @InjectMocks
    CardService cardService;

    @Test
    void get_shouldReturnOptionalCard_whenCardExists() {
        UUID cardId = UUID.randomUUID();
        Card card = new Card();
        card.setId(cardId);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game()));

        Optional<Card> result = cardService.get(cardId);

        assertTrue(result.isPresent());
        assertEquals(cardId, result.get().getId());
        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    void get_shouldReturnEmptyOptional_whenCardDoesNotExist() {
        UUID cardId = UUID.randomUUID();

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        Optional<Card> result = cardService.get(cardId);

        assertFalse(result.isPresent());
        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    void save_shouldSaveCard_whenValidInput() {
        CardInputDto dto = new CardInputDto();
        Card card = new Card();

        when(cardMapper.toEntity(dto)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);

        Card result = cardService.save(dto);

        assertNotNull(result);
        verify(cardMapper, times(1)).toEntity(dto);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void update_shouldUpdateCard_whenCardExists() {
        UUID cardId = UUID.randomUUID();
        CardInputDto dto = new CardInputDto();
        Card card = new Card();
        card.setId(cardId);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(new Card()));
        when(cardMapper.toEntity(dto)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);

        Card result = cardService.update(cardId, dto);

        assertNotNull(result);
        assertEquals(cardId, result.getId());
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardMapper, times(1)).toEntity(dto);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void update_shouldThrowNotFoundException_whenCardDoesNotExist() {
        UUID cardId = UUID.randomUUID();
        CardInputDto dto = new CardInputDto();

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cardService.update(cardId, dto));
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardMapper, never()).toEntity(any());
        verify(cardRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeleteCard_whenCardExists() {
        UUID cardId = UUID.randomUUID();

        doNothing().when(cardRepository).deleteById(cardId);

        cardService.delete(cardId);

        verify(cardRepository, times(1)).deleteById(cardId);
    }

    @Test
    void getAllByGameId_shouldThrowNotFoundException_whenGameDoesNotExist() {
        UUID gameId = UUID.randomUUID();

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cardService.getAllByGameId(gameId, ""));
        verify(gameRepository, times(1)).findById(gameId);
        verify(cardRepository, never()).findByGameId(any());
    }

    @Test
    void getAllByGameId_shouldReturnFilteredCards_whenCardsInDB() {
        UUID gameId = UUID.randomUUID();
        Game game = new Game();
        game.setId(gameId);
        game.setTitle("Test Game");
        game.setName(Game.Name.MAGIC);

        Card card1 = new Card();
        card1.setName("Card One");
        card1.setGame(game);

        Card card2 = new Card();
        card2.setName("Another Card");
        card2.setGame(game);

        List<Card> cards = List.of(card1, card2);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> result = cardService.getAllByGameId(gameId, "One");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Card One", result.get(0).getName());
        verify(gameRepository, times(1)).findById(gameId);
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    void getAllByGameId_shouldFetchAndSaveCardsFromExternalApi_whenNoMatchingCardsInDb() {
        UUID gameId = UUID.randomUUID();
        Game game = new Game();
        game.setId(gameId);
        game.setTitle("Test Game");
        game.setName(Game.Name.MAGIC);

        Card externalCard = new Card();
        externalCard.setName("External Card");
        externalCard.setExternalId("ext-123");
        externalCard.setGame(game);

        List<Card> cardsInDbSecondTime = new ArrayList<>();
        cardsInDbSecondTime.add(externalCard);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(cardRepository.findAll()).thenReturn(new ArrayList<Card>()).thenReturn(cardsInDbSecondTime);
        when(externalCardClient.getCardsByName(game, "External")).thenReturn(List.of(externalCard));
        when(cardRepository.save(any(Card.class))).thenReturn(externalCard);

        List<Card> result = cardService.getAllByGameId(gameId, "External");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("External Card", result.get(0).getName());
        verify(gameRepository, times(1)).findById(gameId);
        verify(cardRepository, times(2)).findAll();
        verify(externalCardClient, times(1)).getCardsByName(game, "External");
        verify(cardRepository, times(1)).save(externalCard);
    }

    @Test
    void getAllByGameId_shouldReturnEmptyList_whenNoCardsMatchFilterInDbOrApi() {
        UUID gameId = UUID.randomUUID();
        Game game = new Game();
        game.setId(gameId);
        game.setTitle("Test Game");
        game.setName(Game.Name.MAGIC);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(cardRepository.findAll()).thenReturn(new ArrayList<>());
        when(externalCardClient.getCardsByName(game, "Nonexistent")).thenReturn(new ArrayList<>());

        List<Card> result = cardService.getAllByGameId(gameId, "Nonexistent");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(gameRepository, times(1)).findById(gameId);
        verify(cardRepository, times(1)).findAll();
        verify(externalCardClient, times(1)).getCardsByName(game, "Nonexistent");
    }

}