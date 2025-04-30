package utn.tacs.grupo5.service.impl.metrics.generators;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.PostRepository;

@Component
public class OpenPostsMetric extends MetricGenerator {

    private static final String METRIC_NAME = "open-posts";
    private static final String METRIC_DESCRIPTION = "Number of open posts";
    private final PostRepository postRepository;

    public OpenPostsMetric(PostRepository postRepository) {
        super(METRIC_NAME, METRIC_DESCRIPTION);
        this.postRepository = postRepository;
    }

    @Override
    public Long getValue() {
        return postRepository.getCountByStatus(Post.Status.PUBLISHED);
    }

}
