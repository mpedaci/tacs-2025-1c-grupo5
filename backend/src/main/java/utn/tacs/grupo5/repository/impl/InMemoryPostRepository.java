package utn.tacs.grupo5.repository.impl;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.entity.post.Post.Status;
import utn.tacs.grupo5.repository.PostRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Getter
public class InMemoryPostRepository implements PostRepository {
    private final List<Post> posts = Collections.synchronizedList(new ArrayList<>());;

    @Override
    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(UUID.randomUUID());
        } else {
            deleteById(post.getId());
        }

        posts.add(post);

        return post;
    }

    @Override
    public Optional<Post> findById(UUID id) {
        synchronized (posts) {
            return posts.stream()
                    .filter(post -> post.getId().equals(id))
                    .findFirst();
        }
    }

    @Override
    public List<Post> findAll() {
        synchronized (posts) {
            return new ArrayList<>(posts);
        }
    }

    @Override
    public void deleteById(UUID id) {
        synchronized (posts) {
            posts.removeIf(post -> post.getId().equals(id));
        }
    }

    @Override
    public Long getCountByStatus(Status status) {
        synchronized (posts) {
            return posts.stream()
                    .filter(post -> status != null && status.equals(post.getStatus()))
                    .count();
        }
    }

    @Override
    public Long getCount() {
        synchronized (posts) {
            return Long.valueOf(posts.size());
        }
    }
}
