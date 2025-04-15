package utn.tacs.grupo5.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.impl.InMemoryOfferRepository;

@ExtendWith(MockitoExtension.class)
public class InMemoryOfferRepositoryTest {

    InMemoryOfferRepository offerRepository;

    @BeforeEach
    public void setUp() {
        offerRepository = new InMemoryOfferRepository();
    }

    @Test
    void save_shouldSaveOffer_whenOfferDoesNotExist() {
        Offer offer = new Offer();
        offer.setMoney(BigDecimal.valueOf(100.0));

        Offer savedOffer = offerRepository.save(offer);

        assertNotNull(savedOffer.getId(), "Offer ID should be generated");
        assertEquals(BigDecimal.valueOf(100.0), savedOffer.getMoney());
        assertEquals(1, offerRepository.findAll().size(), "Repository should contain one offer");
    }

    @Test
    void save_shouldUpdateSavedOffer_whenOfferExists() {
        Offer offer = new Offer();
        offer.setMoney(BigDecimal.valueOf(100.0));

        Offer savedOffer = offerRepository.save(offer);

        Offer updatedOffer = new Offer();
        updatedOffer.setId(savedOffer.getId());
        updatedOffer.setMoney(BigDecimal.valueOf(200.0));

        Offer savedUpdatedOffer = offerRepository.save(updatedOffer);

        assertEquals(savedOffer.getId(), savedUpdatedOffer.getId(), "Offer ID should remain the same");
        assertEquals(BigDecimal.valueOf(200.0), savedUpdatedOffer.getMoney());
        assertEquals(1, offerRepository.findAll().size(), "Repository should still contain one offer");
    }

    @Test
    void save_shouldSaveOffersWithUniqueIds() {
        Offer offer1 = new Offer();
        offer1.setMoney(BigDecimal.valueOf(100.0));

        Offer offer2 = new Offer();
        offer2.setMoney(BigDecimal.valueOf(200.0));

        Offer savedOffer1 = offerRepository.save(offer1);
        Offer savedOffer2 = offerRepository.save(offer2);

        assertNotEquals(savedOffer1.getId(), savedOffer2.getId(), "Each offer should have a unique ID");
        assertEquals(2, offerRepository.findAll().size(), "Repository should contain two offers");
    }

    @Test
    void findById_shouldReturnOffer_whenOfferExists() {
        Offer offer = new Offer();
        offer.setMoney(BigDecimal.valueOf(100.0));

        Offer savedOffer = offerRepository.save(offer);

        Optional<Offer> foundOffer = offerRepository.findById(savedOffer.getId());

        assertTrue(foundOffer.isPresent(), "Offer should be found");
        assertEquals(savedOffer.getId(), foundOffer.get().getId());
    }

    @Test
    void findById_shouldReturnEmpty_whenOfferDoesNotExist() {
        Optional<Offer> foundOffer = offerRepository.findById(999L);

        assertFalse(foundOffer.isPresent(), "Offer should not be found");
    }

    @Test
    void findAll_shouldReturnAllOffers() {
        Offer offer1 = new Offer();
        offer1.setMoney(BigDecimal.valueOf(100.0));

        Offer offer2 = new Offer();
        offer2.setMoney(BigDecimal.valueOf(200.0));

        offerRepository.save(offer1);
        offerRepository.save(offer2);

        assertEquals(2, offerRepository.findAll().size(), "Repository should contain two offers");
    }

    @Test
    void deleteById_shouldRemoveOffer_whenOfferExists() {
        Offer offer = new Offer();
        offer.setMoney(BigDecimal.valueOf(100.0));

        Offer savedOffer = offerRepository.save(offer);

        offerRepository.deleteById(savedOffer.getId());

        assertEquals(0, offerRepository.findAll().size(), "Repository should be empty after deletion");
    }

    @Test
    void deleteById_shouldDoNothing_whenOfferDoesNotExist() {
        Long nonExistingOfferId = 999L;
        Offer offer = new Offer();
        offer.setMoney(BigDecimal.valueOf(100.0));

        Offer savedOffer = offerRepository.save(offer);

        offerRepository.deleteById(nonExistingOfferId);

        assertNotEquals(savedOffer.getId(), nonExistingOfferId);
        assertEquals(1, offerRepository.findAll().size(), "Repository should remain with one offer");
    }

    @Test
    void findAllByPostId_shouldReturnOffers_whenPostIdExists() {
        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(2L);

        Offer offer1 = new Offer();
        offer1.setMoney(BigDecimal.valueOf(100.0));
        offer1.setPost(post1);

        Offer offer2 = new Offer();
        offer2.setMoney(BigDecimal.valueOf(200.0));
        offer2.setPost(post1);

        Offer offer3 = new Offer();
        offer3.setMoney(BigDecimal.valueOf(300.0));
        offer3.setPost(post2);

        offerRepository.save(offer1);
        offerRepository.save(offer2);
        offerRepository.save(offer3);

        List<Offer> offersByPostId = offerRepository.findAllByPostId(1L);

        assertEquals(2, offersByPostId.size(), "Should return two offers for post ID 1");
        assertTrue(offersByPostId.stream().allMatch(offer -> offer.getPost().getId().equals(1L)));
    }

    @Test
    void findAllByPostId_shouldReturnEmpty_whenPostIdDoesNotExist() {
        Post post = new Post();
        post.setId(1L);
        Offer offer = new Offer();
        offer.setPost(post);
        offer.setId(1L);
        offer.setMoney(BigDecimal.valueOf(100.0));

        offerRepository.save(offer);

        List<Offer> offersByPostId = offerRepository.findAllByPostId(999L);

        assertTrue(offersByPostId.isEmpty(), "Should return an empty list for non-existing post ID");
    }

}
