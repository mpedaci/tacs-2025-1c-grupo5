package utn.tacs.grupo5.service;

import utn.tacs.grupo5.dto.metric.MetricOutputDto;

import java.util.List;

public interface IMetricService {
    List<MetricOutputDto> getPostsMetrics();
    List<MetricOutputDto> getOffersMetrics();
}
