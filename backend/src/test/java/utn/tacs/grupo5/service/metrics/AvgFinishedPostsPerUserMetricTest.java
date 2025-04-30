package utn.tacs.grupo5.service.metrics;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.impl.metrics.generators.AvgFinishedPostsPerUserMetric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AvgFinishedPostsPerUserMetricTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AvgFinishedPostsPerUserMetric metric;

    @Test
    void testGetValueWithNoUsers() {
        when(userRepository.getCount()).thenReturn(0L);

        double result = metric.getValue();

        assertEquals(0.0, result);
    }

    @Test
    void testGetValueWithNoFinishedPosts() {
        when(userRepository.getCount()).thenReturn(5L);
        when(postRepository.getCountByStatus(Post.Status.FINISHED)).thenReturn(0L);

        double result = metric.getValue();

        assertEquals(0.0, result);
    }

    @Test
    void testGetValueWithFinishedPostsAndUsers() {
        when(userRepository.getCount()).thenReturn(4L);
        when(postRepository.getCountByStatus(Post.Status.FINISHED)).thenReturn(8L);

        double result = metric.getValue();

        assertEquals(2.0, result);
    }

}
