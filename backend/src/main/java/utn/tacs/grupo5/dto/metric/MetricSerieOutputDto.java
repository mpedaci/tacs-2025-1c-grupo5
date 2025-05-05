package utn.tacs.grupo5.dto.metric;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "Metric Serie", description = "Metric Serie schema for output")
public class MetricSerieOutputDto {
    private String dataKey;
    private String label;
}
