package utn.tacs.grupo5.service.impl.metrics.generators;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.repository.UserRepository;

@Component
public class UserCountMetric extends MetricGenerator {

    private static final String METRIC_NAME = "users-count";
    private static final String METRIC_DESCRIPTION = "Number of users";
    private final UserRepository userRepository;

    public UserCountMetric(UserRepository userRepository) {
        super(METRIC_NAME, METRIC_DESCRIPTION);
        this.userRepository = userRepository;
    }

    @Override
    public Long getValue() {
        return userRepository.getCount();
    }

}
