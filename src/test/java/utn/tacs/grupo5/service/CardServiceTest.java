package utn.tacs.grupo5.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.mapper.CardMapper;
import utn.tacs.grupo5.repository.CardRepository;
import utn.tacs.grupo5.service.impl.CardService;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardService cardService;

    @Test
    void get_shouldReturnOptionalCard_whenCardExists() {
        Long cardId = 1L;
        Card card = new Card();
        card.setId(cardId);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        Optional<Card> result = cardService.get(cardId);

        assertTrue(result.isPresent());
        assertEquals(cardId, result.get().getId());
        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    void get_shouldReturnEmptyOptional_whenCardDoesNotExist() {
        Long cardId = 1L;

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
        Long cardId = 1L;
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
        Long cardId = 1L;
        CardInputDto dto = new CardInputDto();

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cardService.update(cardId, dto));
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardMapper, never()).toEntity(any());
        verify(cardRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeleteCard_whenCardExists() {
        Long cardId = 1L;

        doNothing().when(cardRepository).deleteById(cardId);

        cardService.delete(cardId);

        verify(cardRepository, times(1)).deleteById(cardId);
    }

    @Test
    void getAllByGameId_shouldReturnListOfCards_whenGameHasCards() {
        Long gameId = 1L;
        List<Card> cards = List.of(new Card(), new Card());

        when(cardRepository.findByGameId(gameId)).thenReturn(cards);

        List<Card> result = cardService.getAllByGameId(gameId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cardRepository, times(1)).findByGameId(gameId);
    }

    @Test
    void getAllByGameId_shouldReturnEmptyList_whenGameHasNoCardsOrDoesntExist() {
        Long gameId = 1L;

        when(cardRepository.findByGameId(gameId)).thenReturn(new ArrayList<Card>());

        List<Card> result = cardService.getAllByGameId(gameId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cardRepository, times(1)).findByGameId(gameId);
    }

}