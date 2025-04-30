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
import utn.tacs.grupo5.service.impl.metrics.generators.FinishedPostsMetric;

@ExtendWith(MockitoExtension.class)
public class FinishedPostsMetricTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    FinishedPostsMetric metric;

    @Test
    void testGetValueReturnsCorrectCount() {
        long finishedPostsCount = 5L;
        when(postRepository.getCountByStatus(Post.Status.FINISHED)).thenReturn(finishedPostsCount);
        Long result = metric.getValue();
        assertEquals(finishedPostsCount, result);
    }

    @Test
    void testGetValueReturnsZeroWhenNoFinishedPosts() {
        when(postRepository.getCountByStatus(Post.Status.FINISHED)).thenReturn(0L);
        Long result = metric.getValue();
        assertEquals(0L, result);
    }
}
