package utn.tacs.grupo5.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.card.CardInputDto;
import utn.tacs.grupo5.dto.card.CardOutputDto;
import utn.tacs.grupo5.dto.game.GameOutputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.service.IGameService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardMapperTest {

    private CardMapper cardMapper;
    private IGameService gameService;
    private GameMapper gameMapper;

    @BeforeEach
    void setUp() {
        gameService = mock(IGameService.class);
        gameMapper = mock(GameMapper.class);
        cardMapper = new CardMapper(gameService, gameMapper);
    }

    @Test
    void toDto_ShouldMapCardToDto() {
        UUID cardId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        Game game = new Game();
        game.setId(gameId);

        Card card = new Card();
        card.setId(cardId);
        card.setName("Blue-Eyes White Dragon");
        card.setImageUrl("img.jpg");
        card.setExternalId("123ABC");
        card.setGame(game);

        GameOutputDto gameDto = new GameOutputDto();
        gameDto.setId(gameId);

        when(gameMapper.toDto(game)).thenReturn(gameDto);

        CardOutputDto dto = cardMapper.toDto(card);

        assertEquals(cardId, dto.getId());
        assertEquals("Blue-Eyes White Dragon", dto.getName());
        assertEquals("img.jpg", dto.getImageUrl());
        assertEquals("123ABC", dto.getExternalId());
        assertEquals(gameId, dto.getGame().getId());
    }

    @Test
    void toEntity_ShouldMapDtoToCard() {
        UUID gameId = UUID.randomUUID();

        CardInputDto dto = new CardInputDto();
        dto.setName("Dark Magician");
        dto.setImageUrl("dark.jpg");
        dto.setExternalId("MAG123");
        dto.setGameId(gameId);

        Game game = new Game();
        game.setId(gameId);

        when(gameService.get(gameId)).thenReturn(Optional.of(game));

        Card card = cardMapper.toEntity(dto);

        assertEquals("Dark Magician", card.getName());
        assertEquals("dark.jpg", card.getImageUrl());
        assertEquals("MAG123", card.getExternalId());
        assertEquals(gameId, card.getGameId());
        assertEquals(game, card.getGame());
    }

    @Test
    void toEntity_ShouldThrowIfGameNotFound() {
        UUID gameId = UUID.randomUUID();

        CardInputDto dto = new CardInputDto();
        dto.setGameId(gameId);

        when(gameService.get(gameId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            cardMapper.toEntity(dto);
        });

        assertEquals("Game not found", exception.getMessage());
    }
}
