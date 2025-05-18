package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.entity.post.Post.Status;
import utn.tacs.grupo5.mapper.PostMapper;
import utn.tacs.grupo5.repository.impl.MongoPostRepository;
import utn.tacs.grupo5.service.IPostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService implements IPostService {
    private final MongoPostRepository postRepository;
    private final CardService cardService;
    private final UserService userService;
    private final PostMapper postMapper;

    public PostService(MongoPostRepository postRepository, CardService cardService, UserService userService,
                       PostMapper postMapper) {
        this.postRepository = postRepository;
        this.cardService = cardService;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @Override
    public Optional<Post> get(UUID id) {
        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(this::updatePostInfo);
        return post;
    }

    @Override
    public Post save(PostInputDto postInputDto) {
        Post post = postMapper.toEntity(postInputDto);
        LocalDateTime now = LocalDateTime.now();
        post.setPublishedAt(now);
        post.setUpdatedAt(now);
        post.setFinishedAt(null);
        post.setStatus(Post.Status.PUBLISHED);
        return postRepository.save(post);
    }

    @Override
    public Post update(UUID id, PostInputDto postInputDto) {
        Post existingPost = get(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        Post post = postMapper.toEntity(postInputDto);
        post.setId(existingPost.getId());
        post.setId(id);
        post.setPublishedAt(existingPost.getPublishedAt());
        post.setUpdatedAt(LocalDateTime.now());

        updateFinishedAt(post, existingPost.getStatus());

        return postRepository.save(post);
    }

    @Override
    public void delete(UUID id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public void updateStatus(UUID postId, Status newStatus) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        Status previousStatus = post.getStatus();

        LocalDateTime now = LocalDateTime.now();
        post.setStatus(newStatus);
        post.setUpdatedAt(now);

        updateFinishedAt(post, previousStatus);

        postRepository.save(post);
    }

    void updateFinishedAt(Post post, Status previousStatus) {
        if (post.getStatus() == null) {
            post.setStatus(previousStatus);
        } else if (Post.Status.CANCELLED.equals(post.getStatus()) ||
                Post.Status.FINISHED.equals(post.getStatus())) {
            post.setFinishedAt(LocalDateTime.now());
        } else {
            post.setFinishedAt(null);
        }
    }

    @Override
    public List<Post> getAllWithFilters(String cardName, String gameName, String cardStatus) {
        List<Post> posts = new ArrayList<>(postRepository.findAll());
        posts.forEach(this::updatePostInfo);

        Optional.ofNullable(cardName)
                .filter(name -> !name.isEmpty())
                .ifPresent(name -> posts
                        .removeIf(post -> !post.getCard().getName().toLowerCase().contains(name.toLowerCase())));

        Optional.ofNullable(gameName)
                .filter(name -> !name.isEmpty())
                .ifPresent(name -> posts.removeIf(
                        post -> !post.getCard().getGame().getTitle().toLowerCase().contains(name.toLowerCase())));

        Optional.ofNullable(cardStatus)
                .map(ConservationStatus::fromString)
                .ifPresent(status -> posts.removeIf(post -> !post.getConservationStatus().equals(status)));

        return posts;
    }

    private void updatePostInfo(Post p) {
        p.setUser(userService.get(p.getUserId()).orElseThrow(() -> {
            delete(p.getId());
            return new NotFoundException("User post not found");
        }));

        p.setCard(cardService.get(p.getCardId()).orElseThrow(() -> {
            delete(p.getId());
            return new NotFoundException("Card post not found");
        }));

        List<Card> wantedCards = new ArrayList<>();
        List<UUID> cardsToDelete = new ArrayList<>();
        p.getWantedCardsIds().forEach(wantedCard -> cardService.get(wantedCard)
                .ifPresentOrElse(wantedCards::add, () -> cardsToDelete.add(wantedCard)));
        if(!cardsToDelete.isEmpty()) {
            p.getWantedCardsIds().removeAll(cardsToDelete);
            postRepository.save(p);
        }
        p.setWantedCards(wantedCards);
    }
}
