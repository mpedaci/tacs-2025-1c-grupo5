package utn.tacs.grupo5.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.entity.offer.OfferedCard;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.impl.InMemoryOfferRepository;
import utn.tacs.grupo5.repository.impl.InMemoryOfferedCardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryOfferRepositoryTest {

    private InMemoryOfferRepository offerRepository;
    private InMemoryOfferedCardRepository offeredCardRepository;

    private Offer createOffer(Long postId, Long userId) {
        Post post = new Post();
        post.setId(postId);

        User user = new User();
        user.setId(userId);

        Offer offer = new Offer();
        offer.setPost(post);
        offer.setUser(user);

        offer.setOfferedCards(new ArrayList<>());
        return offer;
    }

    @BeforeEach
    public void setUp() {
        offeredCardRepository = new InMemoryOfferedCardRepository();
        offerRepository = new InMemoryOfferRepository();
        offerRepository.inMemoryOfferedCardRepository = offeredCardRepository;

        // Agregamos 3 ofertas por defecto
        for (long i = 1; i <= 3; i++) {
            offerRepository.save(createOffer(i, i));
        }
    }

    @Test
    public void saveNewOffer_AssignsId() {
        Offer newOffer = createOffer(99L, 88L);
        Offer saved = offerRepository.save(newOffer);

        assertNotNull(saved.getId());
        assertEquals(4, offerRepository.findAll().size());
    }

    @Test
    public void saveExistingOffer_Replaces() {
        Offer offer = offerRepository.findById(1L).orElseThrow();
        offer.setUser(new User()); // alguna modificación
        offerRepository.save(offer);

        assertEquals(3, offerRepository.findAll().size());
        assertEquals(1L, offer.getId());
    }

    @Test
    public void findById_Exists() {
        Optional<Offer> result = offerRepository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void findById_NotExists() {
        assertTrue(offerRepository.findById(999L).isEmpty());
    }

    @Test
    public void findAll_ReturnsAll() {
        List<Offer> offers = offerRepository.findAll();
        assertEquals(3, offers.size());
    }

    @Test
    public void findByPublicationId_Exists() {
        Optional<Offer> result = offerRepository.findByPublicationId(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getPost().getId());
    }

    @Test
    public void findByPublicationId_NotExists() {
        assertTrue(offerRepository.findByPublicationId(999L).isEmpty());
    }

    @Test
    public void findAllByPublicationId() {
        List<Offer> offers = offerRepository.findAllByPublicationId(2L);
        assertEquals(1, offers.size());
        assertEquals(2L, offers.get(0).getPost().getId());
    }

    @Test
    public void deleteByPublicationId_RemovesCorrect() {
        offerRepository.deleteByPublicationId(2L);
        assertTrue(offerRepository.findByPublicationId(2L).isEmpty());
        assertEquals(2, offerRepository.findAll().size());
    }

    @Test
    public void deleteByUserId_RemovesCorrect() {
        offerRepository.deleteByUserId(3L);
        assertTrue(offerRepository.findAll().stream().noneMatch(o -> o.getUser().getId().equals(3L)));
        assertEquals(2, offerRepository.findAll().size());
    }

    @Test
    public void deleteById_RemovesCorrectAndDeletesCards() {
        Offer offer = offerRepository.findById(1L).orElseThrow();

        OfferedCard card = new OfferedCard();
        card.setId(10L);
        offer.setOfferedCards(List.of(card));
        offeredCardRepository.save(card);

        offerRepository.save(offer);
        offerRepository.deleteById(1L);

        assertEquals(2, offerRepository.findAll().size());
        assertTrue(offeredCardRepository.findAll().isEmpty());
    }

    @Test
    public void deleteById_ThrowsIfNotFound() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            offerRepository.deleteById(999L);
        });
        assertEquals("Offer not found", ex.getMessage());
    }
}
