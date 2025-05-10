package utn.tacs.grupo5.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.game.GameInputDto;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.mapper.GameMapper;
import utn.tacs.grupo5.repository.GameRepository;
import utn.tacs.grupo5.service.impl.GameService;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameService gameService;

    @Test
    void get_shouldReturnOptionalGame_whenGameExists() {
        UUID gameId = UUID.randomUUID();
        Game game = new Game();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.get(gameId);

        assertTrue(result.isPresent());
        assertEquals(game, result.get());
        verify(gameRepository, times(1)).findById(gameId);
    }

    @Test
    void get_shouldReturnEmptyOptional_whenGameDoesntExists() {
        UUID gameId = UUID.randomUUID();

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        Optional<Game> result = gameService.get(gameId);

        assertFalse(result.isPresent());
        verify(gameRepository, times(1)).findById(gameId);
    }

    @Test
    void save_shouldSaveGame_whenValidInput() {
        GameInputDto dto = new GameInputDto();
        Game game = new Game();
        when(gameMapper.toEntity(dto)).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);

        Game result = gameService.save(dto);

        assertNotNull(result);
        assertEquals(game, result);
        verify(gameMapper, times(1)).toEntity(dto);
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void update_shouldUpdateGame_whenGameExists() {
        UUID gameId = UUID.randomUUID();
        GameInputDto dto = new GameInputDto();
        Game game = new Game();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(new Game()));
        when(gameMapper.toEntity(dto)).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);

        Game result = gameService.update(gameId, dto);

        assertNotNull(result);
        assertEquals(game, result);
        assertEquals(gameId, game.getId());
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameMapper, times(1)).toEntity(dto);
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void update_shouldThrowNotFoundException_whenGameDoesNotExist() {
        UUID gameId = UUID.randomUUID();
        GameInputDto dto = new GameInputDto();
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> gameService.update(gameId, dto));
        verify(gameRepository, times(1)).findById(gameId);
    }

    @Test
    void delete_shouldDeleteGame_whenGameExists() {
        UUID gameId = UUID.randomUUID();

        gameService.delete(gameId);

        verify(gameRepository, times(1)).deleteById(gameId);
    }
}
