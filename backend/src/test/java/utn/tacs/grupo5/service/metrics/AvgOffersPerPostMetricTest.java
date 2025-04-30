package utn.tacs.grupo5.service.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.service.impl.metrics.generators.AvgOffersPerPostMetric;

@ExtendWith(MockitoExtension.class)
public class AvgOffersPerPostMetricTest {

    @Mock
    OfferRepository offerRepository;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    AvgOffersPerPostMetric metric;

    @Test
    public void testGetValueWithNoPosts() {
        when(postRepository.getCount()).thenReturn(0L);
        when(offerRepository.getCount()).thenReturn(10L);

        double result = metric.getValue();

        assertEquals(0.0, result);
    }

    @Test
    public void testGetValueWithNoOffers() {
        when(postRepository.getCount()).thenReturn(5L);
        when(offerRepository.getCount()).thenReturn(0L);

        double result = metric.getValue();

        assertEquals(0.0, result);
    }

    @Test
    public void testGetValueWithValidData() {
        when(postRepository.getCount()).thenReturn(4L);
        when(offerRepository.getCount()).thenReturn(8L);

        double result = metric.getValue();

        assertEquals(2.0, result);
    }

    @Test
    public void testGetValueWithLargeNumbers() {
        when(postRepository.getCount()).thenReturn(1_000_000L);
        when(offerRepository.getCount()).thenReturn(2_000_000L);

        double result = metric.getValue();

        assertEquals(2.0, result);
    }

}
