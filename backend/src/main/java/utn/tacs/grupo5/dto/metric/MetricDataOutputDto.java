package utn.tacs.grupo5.dto.metric;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "Metric Data", description = "Metric Data schema for output")
public class MetricDataOutputDto {
    private String x;
    private Number y;
}
