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
import utn.tacs.grupo5.service.impl.metrics.generators.CancelledPostsMetric;

@ExtendWith(MockitoExtension.class)
public class CancelledPostsMetricTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    CancelledPostsMetric metric;

    @Test
    void testGetValueReturnsCorrectCount() {
        long cancelledPostsCount = 5L;
        when(postRepository.getCountByStatus(Post.Status.CANCELLED)).thenReturn(cancelledPostsCount);
        Long result = metric.getValue();
        assertEquals(cancelledPostsCount, result);
    }

    @Test
    void testGetValueReturnsZeroWhenNoCancelledPosts() {
        when(postRepository.getCountByStatus(Post.Status.CANCELLED)).thenReturn(0L);
        Long result = metric.getValue();
        assertEquals(0L, result);
    }

}
