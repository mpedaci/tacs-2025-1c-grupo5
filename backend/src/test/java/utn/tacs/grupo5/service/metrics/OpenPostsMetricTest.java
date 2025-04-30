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
import utn.tacs.grupo5.service.impl.metrics.generators.OpenPostsMetric;

@ExtendWith(MockitoExtension.class)
public class OpenPostsMetricTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    OpenPostsMetric metric;

    @Test
    void testGetValueReturnsCorrectCount() {
        long openPostsCount = 10L;
        when(postRepository.getCountByStatus(Post.Status.PUBLISHED)).thenReturn(openPostsCount);
        Long result = metric.getValue();
        assertEquals(openPostsCount, result);
    }

    @Test
    void testGetValueReturnsZeroWhenNoOpenPosts() {
        when(postRepository.getCountByStatus(Post.Status.PUBLISHED)).thenReturn(0L);
        Long result = metric.getValue();
        assertEquals(0L, result);
    }
}
