package utn.tacs.grupo5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import utn.tacs.grupo5.TestSecurityConfig;
import utn.tacs.grupo5.dto.metric.MetricOutputDto;
import utn.tacs.grupo5.service.IMetricService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = MetricController.class)
@Import(TestSecurityConfig.class)
public class MetricsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    IMetricService metricService;

    @Test
    void shouldReturnOfferMetricsData() throws Exception {
        List<MetricOutputDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(new MetricOutputDto());
        when(metricService.getOffersMetrics()).thenReturn(expectedResponse);

        mockMvc.perform(get("/metrics/offers"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void shouldReturnPostMetricsData() throws Exception {

        List<MetricOutputDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(new MetricOutputDto());
        when(metricService.getPostsMetrics()).thenReturn(expectedResponse);

        mockMvc.perform(get("/metrics/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}
