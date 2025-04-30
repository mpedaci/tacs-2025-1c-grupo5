package utn.tacs.grupo5.service.impl.metrics.generators;

import org.springframework.stereotype.Component;

import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.repository.UserRepository;

@Component
public class AvgOpenPostsPerUserMetric extends MetricGenerator {

    private static final String METRIC_NAME = "avg-open-posts-per-user";
    private static final String METRIC_DESCRIPTION = "Average number of open posts per user";
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public AvgOpenPostsPerUserMetric(PostRepository postRepository, UserRepository userRepository) {
        super(METRIC_NAME, METRIC_DESCRIPTION);
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Double getValue() {
        long totalOpenPosts = postRepository.getCountByStatus(Post.Status.PUBLISHED);
        long totalUsers = userRepository.getCount();
        return totalUsers == 0 ? 0 : (double) totalOpenPosts / totalUsers;
    }

}
