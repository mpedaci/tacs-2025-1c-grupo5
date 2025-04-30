package utn.tacs.grupo5.dto.metrics;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricsDto {

    private List<MetricDto> metrics;

}
