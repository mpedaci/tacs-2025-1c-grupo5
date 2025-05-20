package utn.tacs.grupo5.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import utn.tacs.grupo5.dto.metric.MetricOutputDto;
import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.repository.impl.MongoOfferRepository;
import utn.tacs.grupo5.repository.impl.MongoPostRepository;
import utn.tacs.grupo5.service.impl.MetricService;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

    @Mock
    private MongoOfferRepository offerRepository;

    @Mock
    private MongoPostRepository postRepository;

    @InjectMocks
    private MetricService metricService;

    @Test
    void testGetPostsMetrics() {
        Post post1 = new Post();
        post1.setPublishedAt(LocalDateTime.of(2023, 10, 1, 10, 0));
        post1.setStatus(Post.Status.PUBLISHED);

        Post post2 = new Post();
        post2.setPublishedAt(LocalDateTime.of(2023, 10, 2, 10, 0));
        post2.setStatus(Post.Status.CANCELLED);

        Post post3 = new Post();
        post3.setPublishedAt(LocalDateTime.of(2023, 10, 1, 10, 0));
        post3.setFinishedAt(LocalDateTime.of(2023, 10, 3, 10, 0));
        post3.setStatus(Post.Status.PUBLISHED);

        when(postRepository.findAll()).thenReturn(List.of(post1, post2, post3));

        List<MetricOutputDto> metrics = metricService.getPostsMetrics();

        assertEquals(4, metrics.size());
        assertEquals("Publicaciones creadas", metrics.get(0).getTitle());
        assertEquals(2, metrics.get(0).getData().size());
        assertEquals("Publicaciones finalizadas", metrics.get(1).getTitle());
        assertEquals(1, metrics.get(1).getData().size());
    }

    @Test
    void testGetOffersMetrics() {
        Offer offer1 = new Offer();
        offer1.setPublishedAt(LocalDateTime.of(2023, 10, 1, 10, 0));
        offer1.setStatus(Offer.Status.ACCEPTED);

        Offer offer2 = new Offer();
        offer2.setPublishedAt(LocalDateTime.of(2023, 10, 2, 10, 0));
        offer2.setStatus(Offer.Status.REJECTED);

        Offer offer3 = new Offer();
        offer3.setPublishedAt(LocalDateTime.of(2023, 10, 1, 10, 0));
        offer3.setStatus(Offer.Status.PENDING);

        when(offerRepository.findAll()).thenReturn(List.of(offer1, offer2, offer3));

        List<MetricOutputDto> metrics = metricService.getOffersMetrics();

        assertEquals(4, metrics.size());
        assertEquals("Ofertas creadas", metrics.get(0).getTitle());
        assertEquals(2, metrics.get(0).getData().size());
        assertEquals("Ofertas aceptadas", metrics.get(1).getTitle());
        assertEquals(1, metrics.get(1).getData().size());
    }
}