package utn.tacs.grupo5.dto.metric;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "Metric", description = "Metric schema for output")
public class MetricOutputDto {
    private String title;
    private List<MetricDataOutputDto> data;
    private List<MetricSerieOutputDto> serie;
}
