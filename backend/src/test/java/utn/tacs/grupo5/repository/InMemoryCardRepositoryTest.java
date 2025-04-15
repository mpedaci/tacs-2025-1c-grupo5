package utn.tacs.grupo5.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.repository.impl.InMemoryCardRepository;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryCardRepositoryTest {

    private InMemoryCardRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryCardRepository();
    }

    @Test
    void testSaveNewCard() {
        Card card = new Card();
        Card savedCard = repository.save(card);

        assertNotNull(savedCard.getId());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void save_shouldUpdateSavedCard_whenCardExists() {
        Card card = new Card();
        card.setId(1L);
        card.setName("card1");
        repository.save(card);

        Card updatedCard = new Card();
        updatedCard.setId(1L);
        updatedCard.setName("card2");
        ;

        repository.save(updatedCard);

        assertEquals("card2", repository.findById(1L).get().getName());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_shouldReturnCard_whenCardExists() {
        Card card = new Card();
        Card savedCard = repository.save(card);

        Optional<Card> foundCard = repository.findById(savedCard.getId());
        assertTrue(foundCard.isPresent());
        assertEquals(savedCard.getId(), foundCard.get().getId());
    }

    @Test
    void findAll_shouldReturnAllCards() {
        Card card1 = new Card();
        Card card2 = new Card();

        repository.save(card1);
        repository.save(card2);

        List<Card> allCards = repository.findAll();
        assertEquals(2, allCards.size());
    }

    @Test
    void deleteById_shouldRemoveCard_whenCardExists() {
        Card card = new Card();
        Card savedCard = repository.save(card);

        repository.deleteById(savedCard.getId());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void testFindByGameId() {
        Game game1 = new Game();
        game1.setId(1L);
        Card card1 = new Card();
        card1.setGame(game1);
        Card card2 = new Card();
        card2.setGame(game1);

        Game game2 = new Game();
        game2.setId(2L);
        Card card3 = new Card();
        card3.setGame(game2);

        repository.save(card1);
        repository.save(card2);
        repository.save(card3);

        List<Card> cardsForGame1 = repository.findByGameId(1L);
        assertEquals(2, cardsForGame1.size());
        assertTrue(cardsForGame1.stream().allMatch(card -> card.getGame().getId().equals(1L)));
    }
}
