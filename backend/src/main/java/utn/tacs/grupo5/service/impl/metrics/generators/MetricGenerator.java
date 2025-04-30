package utn.tacs.grupo5.service.impl.metrics.generators;

import utn.tacs.grupo5.dto.metrics.MetricDto;

public abstract class MetricGenerator {

    protected String name;
    protected String description;

    public MetricGenerator(String name, String description) {
        this.name = name;
        this.description = description;
    }

    abstract protected Object getValue();

    public MetricDto generate() {
        MetricDto metricDto = new MetricDto();
        metricDto.setName(name);
        metricDto.setDescription(description);
        metricDto.setValue(getValue());
        return metricDto;
    }
}
