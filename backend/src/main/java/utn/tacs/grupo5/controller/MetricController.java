package utn.tacs.grupo5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utn.tacs.grupo5.controller.response.ResponseGenerator;
import utn.tacs.grupo5.dto.metric.MetricOutputDto;
import utn.tacs.grupo5.service.IMetricService;

import java.util.List;

@RestController
@RequestMapping("/metrics")
@Tag(name = "Metrics", description = "Metrics operations")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class MetricController extends BaseController {

    private final IMetricService metricService;

    public MetricController(IMetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping("/posts")
    @Operation(summary = "Get metrics for posts")
    public ResponseEntity<List<MetricOutputDto>> getPostsMetrics() {
        List<MetricOutputDto> metrics = metricService.getPostsMetrics();
        return ResponseGenerator.generateResponseOK(metrics);
    }

    @GetMapping("/offers")
    @Operation(summary = "Get metrics for offers")
    public ResponseEntity<List<MetricOutputDto>> getOffersMetrics() {
        List<MetricOutputDto> metrics = metricService.getOffersMetrics();
        return ResponseGenerator.generateResponseOK(metrics);
    }
}
