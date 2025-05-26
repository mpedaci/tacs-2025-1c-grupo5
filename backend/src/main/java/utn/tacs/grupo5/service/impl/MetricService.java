package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.dto.metric.MetricDataOutputDto;
import utn.tacs.grupo5.dto.metric.MetricOutputDto;
import utn.tacs.grupo5.dto.metric.MetricSerieOutputDto;
import utn.tacs.grupo5.entity.post.Offer;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.service.IMetricService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class MetricService implements IMetricService {
    private final OfferRepository offerRepository;
    private final PostRepository postRepository;

    public MetricService(OfferRepository offerRepository, PostRepository postRepository) {
        this.offerRepository = offerRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<MetricOutputDto> getPostsMetrics() {
        List<Post> posts = postRepository.findAll();

        Map<LocalDate, Long> createdByDate = new TreeMap<>();
        Map<LocalDate, Long> finishedByDate = new TreeMap<>();
        Map<LocalDate, Long> publishedByDate = new TreeMap<>();
        Map<LocalDate, Long> cancelledByDate = new TreeMap<>();

        for (Post post : posts) {
            if (post.getPublishedAt() != null) {
                LocalDate date = post.getPublishedAt().toLocalDate();
                createdByDate.put(date, createdByDate.getOrDefault(date, 0L) + 1);

                switch (post.getStatus()) {
                    case PUBLISHED:
                        publishedByDate.put(date, publishedByDate.getOrDefault(date, 0L) + 1);
                        break;
                    case CANCELLED:
                        cancelledByDate.put(date, cancelledByDate.getOrDefault(date, 0L) + 1);
                        break;
                    default:
                        break;
                }
            }

            if (post.getFinishedAt() != null) {
                LocalDate date = post.getFinishedAt().toLocalDate();
                finishedByDate.put(date, finishedByDate.getOrDefault(date, 0L) + 1);
            }
        }

        return List.of(
                buildMetric("Publicaciones creadas", createdByDate),
                buildMetric("Publicaciones finalizadas", finishedByDate),
                buildMetric("Publicaciones en progreso", publishedByDate),
                buildMetric("Publicaciones rechazadas", cancelledByDate));
    }

    @Override
    public List<MetricOutputDto> getOffersMetrics() {
        List<Offer> offers = offerRepository.findAll();

        Map<LocalDate, Long> createdCountByDate = new TreeMap<>();
        Map<LocalDate, Long> acceptedCountByDate = new TreeMap<>();
        Map<LocalDate, Long> rejectedCountByDate = new TreeMap<>();
        Map<LocalDate, Long> pendingCountByDate = new TreeMap<>();

        for (Offer offer : offers) {
            if (offer.getPublishedAt() == null)
                continue;
            LocalDate date = offer.getPublishedAt().toLocalDate();

            createdCountByDate.put(date, createdCountByDate.getOrDefault(date, 0L) + 1);

            switch (offer.getStatus()) {
                case ACCEPTED:
                    acceptedCountByDate.put(date, acceptedCountByDate.getOrDefault(date, 0L) + 1);
                    break;
                case REJECTED:
                    rejectedCountByDate.put(date, rejectedCountByDate.getOrDefault(date, 0L) + 1);
                    break;
                case PENDING:
                    pendingCountByDate.put(date, pendingCountByDate.getOrDefault(date, 0L) + 1);
                    break;
            }
        }

        return List.of(
                buildMetric("Ofertas creadas", createdCountByDate),
                buildMetric("Ofertas aceptadas", acceptedCountByDate),
                buildMetric("Ofertas rechazadas", rejectedCountByDate),
                buildMetric("Ofertas en progreso", pendingCountByDate));
    }

    private MetricOutputDto buildMetric(String title, Map<LocalDate, Long> counts) {
        List<MetricDataOutputDto> dataPoints = counts.entrySet().stream()
                .map(entry -> {
                    MetricDataOutputDto point = new MetricDataOutputDto();
                    point.setX(entry.getKey().toString()); // "yyyy-MM-dd"
                    point.setY(entry.getValue().intValue());
                    return point;
                })
                .collect(Collectors.toList());

        MetricSerieOutputDto serie = new MetricSerieOutputDto();
        serie.setDataKey("y");
        serie.setLabel(title);

        MetricOutputDto dto = new MetricOutputDto();
        dto.setTitle(title);
        dto.setData(dataPoints);
        dto.setSerie(List.of(serie));

        return dto;
    }
}
