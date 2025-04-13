package utn.tacs.grupo5.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.entity.Offer;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.service.impl.OfferService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    OfferRepository offerRepository;

    @InjectMocks
    OfferService offerService;

    @Test
    public void get_shouldReturnOffer_whenOfferExists() {
//        Long offerId = 1L;
//        Offer offer = new Offer();
//        offer.setId(offerId);
//
//        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));
//
//        Optional<Offer> result = offerService.get(offerId);
//
//        assertNotNull(result);
//        assertEquals(offerId, result.get().getId());


         }


}
