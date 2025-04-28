package utn.tacs.grupo5.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.repository.impl.InMemoryGameRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryGameRepositoryTest {

    private InMemoryGameRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryGameRepository();
    }

    @Test
    void save_shouldSaveGame_whenGameDoesNotExist() {
        Game game = new Game();
        Game savedGame = repository.save(game);

        assertNotNull(savedGame.getId());
        assertEquals(4, repository.findAll().size());
    }

    @Test
    void save_shouldUpdateGame_whenGameExists() {
        Game game = new Game();
        Game savedGame = repository.save(game);

        savedGame.setTitle("Updated Name");
        repository.save(savedGame);

        Optional<Game> updatedGame = repository.findById(savedGame.getId());
        assertTrue(updatedGame.isPresent());
        assertEquals("Updated Name", updatedGame.get().getTitle());
    }

    @Test
    void findById_shouldReturnGame_whenGameExists() {
        Game game = new Game();
        Game savedGame = repository.save(game);

        Optional<Game> foundGame = repository.findById(savedGame.getId());
        assertTrue(foundGame.isPresent());
        assertEquals(savedGame.getId(), foundGame.get().getId());
    }

    @Test
    void findById_shouldReturnEmpty_whenGameDoesNotExist() {
        Optional<Game> foundGame = repository.findById(UUID.randomUUID());
        assertFalse(foundGame.isPresent());
    }

    @Test
    void findAll_shouldReturnAllGames() {
        Game game1 = new Game();
        Game game2 = new Game();
        repository.save(game1);
        repository.save(game2);

        List<Game> allGames = repository.findAll();
        assertEquals(5, allGames.size());
    }

    @Test
    void deleteById_shouldRemoveGame_whenGameExists() {
        Game game = new Game();
        Game savedGame = repository.save(game);

        repository.deleteById(savedGame.getId());
        Optional<Game> foundGame = repository.findById(savedGame.getId());
        assertFalse(foundGame.isPresent());
        assertEquals(3, repository.findAll().size());
    }
}
