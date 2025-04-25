package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.entity.post.Post.Status;
import utn.tacs.grupo5.mapper.PostMapper;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.service.IPostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {

    private PostRepository postRepository;
    private PostMapper postMapper;

    public PostService(
            PostRepository postRepository,
            PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public Optional<Post> get(Long id) {
        return postRepository.findById(id);
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
    public Post update(Long id, PostInputDto postInputDto) {
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
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public void updateStatus(Long postId, Status newStatus) {
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

        Optional.ofNullable(cardName)
                .filter(name -> !name.isEmpty())
                .ifPresent(name -> posts
                        .removeIf(post -> !post.getCard().getName().toLowerCase().contains(name.toLowerCase())));

        Optional.ofNullable(gameName)
                .filter(name -> !name.isEmpty())
                .ifPresent(name -> posts.removeIf(
                        post -> !post.getCard().getGame().getTitle().toLowerCase().contains(name.toLowerCase())));

        Optional.ofNullable(cardStatus)
                .map(status -> ConservationStatus.fromString(status))
                .ifPresent(status -> posts.removeIf(post -> !post.getConservationStatus().equals(status)));

        return posts;
    }
}
