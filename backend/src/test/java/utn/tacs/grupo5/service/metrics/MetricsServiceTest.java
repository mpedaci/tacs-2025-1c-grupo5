package utn.tacs.grupo5.service.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.dto.metrics.MetricDto;
import utn.tacs.grupo5.dto.metrics.MetricsDto;
import utn.tacs.grupo5.service.impl.metrics.MetricsService;
import utn.tacs.grupo5.service.impl.metrics.generators.MetricGenerator;

@ExtendWith(MockitoExtension.class)
public class MetricsServiceTest {

    @Mock
    MetricGenerator metricGenerator1;

    @Mock
    MetricGenerator metricGenerator2;

    MetricsService metricsService;

    private MetricDto metric1 = new MetricDto();
    private MetricDto metric2 = new MetricDto();

    @BeforeEach
    void setUp() {
        metricsService = new MetricsService(List.of(metricGenerator1, metricGenerator2));
        when(metricGenerator1.generate()).thenReturn(metric1);
        when(metricGenerator2.generate()).thenReturn(metric2);
    }

    @Test
    void testGetMetrics() {
        MetricsDto metricsDto = metricsService.getMetrics();

        assertEquals(2, metricsDto.getMetrics().size());
        assertEquals(metric1, metricsDto.getMetrics().get(0));
        assertEquals(metric2, metricsDto.getMetrics().get(1));
    }

}
