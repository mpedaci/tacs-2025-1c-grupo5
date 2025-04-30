package utn.tacs.grupo5.service.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.impl.metrics.generators.UserCountMetric;

@ExtendWith(MockitoExtension.class)
public class UserCountMetricTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserCountMetric metric;

    @Test
    void testGetValueReturnsCorrectCount() {
        long expectedCount = 10L;
        when(userRepository.getCount()).thenReturn(expectedCount);
        Long actualCount = metric.getValue();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testGetValueWhenRepositoryReturnsZero() {
        long expectedCount = 0L;
        when(userRepository.getCount()).thenReturn(expectedCount);
        Long actualCount = metric.getValue();
        assertEquals(expectedCount, actualCount);
    }

}
