package utn.tacs.grupo5.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.repository.impl.InMemoryOfferRepository;

@ExtendWith(MockitoExtension.class)
public class InMemoryOfferRepositoryTest {
    InMemoryOfferRepository offerRepository;

    @BeforeEach
    public void setUp() {
        offerRepository = new InMemoryOfferRepository();
    }

    @Test
    public void testSave() {
        // Implement the test for the save method
    }

}
