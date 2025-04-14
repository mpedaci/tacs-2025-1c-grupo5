package utn.tacs.grupo5.repository;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.repository.impl.InMemoryOfferRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class InMemoryOfferRepositoryTest {
    InMemoryOfferRepository offerRepository;

    @BeforeEach
    public void setUp() {
        offerRepository = new InMemoryOfferRepository();
    }

    @Test
    public void save_shouldSaveOffer_whenOfferDoenstExist() {
        Offer offer = new Offer();

        Offer savedOffer = offerRepository.save(offer);

        assertNotNull(savedOffer.getId(), "Offer ID should be generated");
        assertEquals(1, savedOffer.getId());
        assertEquals(1, offerRepository.findAll().size(), "Repository should contain one offer");
    }
}
