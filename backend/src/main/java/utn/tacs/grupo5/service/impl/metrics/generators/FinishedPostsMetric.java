package utn.tacs.grupo5.service.impl.metrics.generators;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.PostRepository;

@Component
public class FinishedPostsMetric extends MetricGenerator {

    private static final String METRIC_NAME = "finished-posts";
    private static final String METRIC_DESCRIPTION = "Number of finished posts";
    private final PostRepository postRepository;

    public FinishedPostsMetric(PostRepository postRepository) {
        super(METRIC_NAME, METRIC_DESCRIPTION);
        this.postRepository = postRepository;
    }

    @Override
    public Long getValue() {
        return postRepository.getCountByStatus(Post.Status.FINISHED);
    }

}
