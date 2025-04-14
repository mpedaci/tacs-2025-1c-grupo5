package utn.tacs.grupo5.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.service.impl.OfferService;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    OfferRepository offerRepository;

    @InjectMocks
    OfferService offerService;

    @Test
    public void testSave() {
        // Implement the test for the save method
        // You can use Mockito to mock the behavior of the offerRepository
        // and verify that the save method is called with the correct parameters.
    }


}
