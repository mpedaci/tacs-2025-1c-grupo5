package utn.tacs.grupo5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.entity.post.PostStatus;
import utn.tacs.grupo5.repository.impl.InMemoryPostRepository;
import utn.tacs.grupo5.repository.impl.InMemoryUserRepository;
import utn.tacs.grupo5.service.IPostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {
    @Autowired
    private InMemoryPostRepository inMemoryPostRepository;
    @Autowired
    private InMemoryUserRepository inMemoryUserRepository;

    @Override
    public Optional<Post> get(Long id) {
        return inMemoryPostRepository.findById(id);
    }

    @Override
    public Post save(PostInputDto postInputDto) {
        Post post = new Post();
        post.setId(null);
        post.setUser(inMemoryUserRepository.findById(postInputDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found")));
        post.setImages(postInputDto.getImages());

        post.setPublishDate(LocalDateTime.now());
        post.setFinishDate(null);
        post.setConservationStatus(ConservationStatus.valueOf(postInputDto.getConservationStatus()));
        post.setEstimatedValue(postInputDto.getEstimatedValue());

        post.setPostStatus(PostStatus.PUBLISHED);

        return inMemoryPostRepository.save(post);
    }

    @Override
    public Post update(Long id, PostInputDto postInputDto) {
        Post post = get(id).orElseThrow(() -> new NotFoundException("Post not found"));

        post.setUser(inMemoryUserRepository.findById(postInputDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found")));
        post.setImages(postInputDto.getImages());

        post.setPublishDate(LocalDateTime.now());
        post.setFinishDate(null);
        post.setConservationStatus(ConservationStatus.valueOf(postInputDto.getConservationStatus()));
        post.setEstimatedValue(postInputDto.getEstimatedValue());

        post.setPostStatus(PostStatus.valueOf(postInputDto.getPostStatus()));

        inMemoryPostRepository.save(post);

        return post;
    }

    @Override
    public void delete(Long id) {
        inMemoryPostRepository.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
        return inMemoryPostRepository.findAll();
    }
}
