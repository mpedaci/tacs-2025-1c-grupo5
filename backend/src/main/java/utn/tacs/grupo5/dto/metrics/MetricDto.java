package utn.tacs.grupo5.dto.metrics;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricDto {

    private String name;
    private String description;
    private Object value;

}
