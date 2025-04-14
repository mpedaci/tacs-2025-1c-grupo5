package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.entity.post.PostStatus;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.IPostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Post> get(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post save(PostInputDto postInputDto) {
        Post post = new Post();
        post.setId(null);
        post.setUser(userRepository.findById(postInputDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found")));
        post.setImages(postInputDto.getImages());

        post.setPublishDate(LocalDateTime.now());
        post.setFinishDate(null);
        post.setConservationStatus(ConservationStatus.valueOf(postInputDto.getConservationStatus()));
        post.setEstimatedValue(postInputDto.getEstimatedValue());

        post.setPostStatus(PostStatus.PUBLISHED);

        return postRepository.save(post);
    }

    @Override
    public Post update(Long id, PostInputDto postInputDto) {
        Post post = get(id).orElseThrow(() -> new NotFoundException("Post not found"));

        if (postInputDto.getPostStatus() != null) {
            post.setPostStatus(PostStatus.valueOf(postInputDto.getPostStatus()));
            if (PostStatus.FINISHED.equals(post.getPostStatus())) {
                post.setFinishDate(LocalDateTime.now());
            }
        } else {
            post.setImages(postInputDto.getImages());
            post.setConservationStatus(ConservationStatus.valueOf(postInputDto.getConservationStatus()));
            post.setEstimatedValue(postInputDto.getEstimatedValue());
        }

        postRepository.save(post);
        return post;
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }
}
