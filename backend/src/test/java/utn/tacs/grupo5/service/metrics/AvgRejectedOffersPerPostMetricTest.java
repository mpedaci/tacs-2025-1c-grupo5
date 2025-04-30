package utn.tacs.grupo5.service.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.service.impl.metrics.generators.AvgRejectedOffersPerPostMetric;

@ExtendWith(MockitoExtension.class)
public class AvgRejectedOffersPerPostMetricTest {

    @Mock
    OfferRepository offerRepository;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    AvgRejectedOffersPerPostMetric metric;

    @Test
    void testGetValueWhenNoPosts() {
        when(postRepository.getCount()).thenReturn(0L);
        Double result = metric.getValue();
        assertEquals(0.0, result);
    }

    @Test
    void testGetValueWhenNoRejectedOffers() {
        when(postRepository.getCount()).thenReturn(10L);
        when(offerRepository.getCountByStatus(Offer.Status.REJECTED)).thenReturn(0L);
        Double result = metric.getValue();
        assertEquals(0.0, result);
    }

    @Test
    void testGetValueWithRejectedOffersAndPosts() {
        when(postRepository.getCount()).thenReturn(5L);
        when(offerRepository.getCountByStatus(Offer.Status.REJECTED)).thenReturn(10L);
        Double result = metric.getValue();
        assertEquals(2.0, result);
    }

    @Test
    void testGetValueWithFractionalResult() {
        when(postRepository.getCount()).thenReturn(3L);
        when(offerRepository.getCountByStatus(Offer.Status.REJECTED)).thenReturn(7L);
        Double result = metric.getValue();
        assertEquals(7.0 / 3.0, result);
    }
}
