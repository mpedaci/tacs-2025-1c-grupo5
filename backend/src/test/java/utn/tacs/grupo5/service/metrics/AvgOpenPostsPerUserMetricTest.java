package utn.tacs.grupo5.service.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.impl.metrics.generators.AvgOpenPostsPerUserMetric;

@ExtendWith(MockitoExtension.class)
public class AvgOpenPostsPerUserMetricTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AvgOpenPostsPerUserMetric metric;

    @Test
    void testGetValueWithNoUsers() {
        when(userRepository.getCount()).thenReturn(0L);
        when(postRepository.getCountByStatus(Post.Status.PUBLISHED)).thenReturn(10L);

        double result = metric.getValue();

        assertEquals(0.0, result);
    }

    @Test
    void testGetValueWithNoOpenPosts() {
        when(userRepository.getCount()).thenReturn(5L);
        when(postRepository.getCountByStatus(Post.Status.PUBLISHED)).thenReturn(0L);

        double result = metric.getValue();

        assertEquals(0.0, result);
    }

    @Test
    void testGetValueWithUsersAndOpenPosts() {
        when(userRepository.getCount()).thenReturn(4L);
        when(postRepository.getCountByStatus(Post.Status.PUBLISHED)).thenReturn(8L);

        double result = metric.getValue();

        assertEquals(2.0, result);
    }

    @Test
    void testGetValueWithLargeNumbers() {
        when(userRepository.getCount()).thenReturn(1000L);
        when(postRepository.getCountByStatus(Post.Status.PUBLISHED)).thenReturn(5000L);

        double result = metric.getValue();

        assertEquals(5.0, result);
    }

}
