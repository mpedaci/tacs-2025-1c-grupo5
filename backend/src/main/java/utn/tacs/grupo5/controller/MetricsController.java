package utn.tacs.grupo5.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import utn.tacs.grupo5.controller.response.ResponseGenerator;
import utn.tacs.grupo5.dto.metrics.MetricsDto;
import utn.tacs.grupo5.service.metrics.IMetricsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@Hidden
@RequestMapping("/admin")
public class MetricsController extends BaseController {

    private final IMetricsService metricsService;

    public MetricsController(IMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/metrics")
    public ResponseEntity<MetricsDto> getMetrics() {
        return ResponseGenerator.generateResponseOK(metricsService.getMetrics());
    }

}