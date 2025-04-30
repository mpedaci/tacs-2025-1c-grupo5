package utn.tacs.grupo5.service.impl.metrics.generators;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.PostRepository;

@Component
public class AvgOffersPerPostMetric extends MetricGenerator {

    private static final String METRIC_NAME = "offers-per-post";
    private static final String METRIC_DESCRIPTION = "Average number of offers per post";
    private final OfferRepository offerRepository;
    private final PostRepository postRepository;

    public AvgOffersPerPostMetric(OfferRepository offerRepository, PostRepository postRepository) {
        super(METRIC_NAME, METRIC_DESCRIPTION);
        this.offerRepository = offerRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Double getValue() {
        long offerCount = offerRepository.getCount();
        long postCount = postRepository.getCount();
        return postCount == 0 ? 0 : (double) offerCount / postCount;
    }

}
