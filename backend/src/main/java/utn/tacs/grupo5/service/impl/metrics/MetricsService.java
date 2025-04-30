package utn.tacs.grupo5.service.impl.metrics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import utn.tacs.grupo5.dto.metrics.MetricDto;
import utn.tacs.grupo5.dto.metrics.MetricsDto;
import utn.tacs.grupo5.service.impl.metrics.generators.MetricGenerator;
import utn.tacs.grupo5.service.metrics.IMetricsService;

@Service
public class MetricsService implements IMetricsService {

    private final List<MetricGenerator> metricGenerators;

    public MetricsService(List<MetricGenerator> metricGenerators) {
        this.metricGenerators = metricGenerators;
    }

    @Override
    public MetricsDto getMetrics() {
        MetricsDto metricsDto = new MetricsDto();
        List<MetricDto> metrics = new ArrayList<>();
        for (MetricGenerator metricGenerator : metricGenerators) {
            metrics.add(metricGenerator.generate());
        }

        metricsDto.setMetrics(metrics);
        return metricsDto;
    }

}
