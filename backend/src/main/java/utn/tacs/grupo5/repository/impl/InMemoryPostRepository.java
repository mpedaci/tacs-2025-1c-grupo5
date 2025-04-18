package utn.tacs.grupo5.repository.impl;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Getter
public class InMemoryPostRepository implements PostRepository {
    private final List<Post> posts = new ArrayList<>();

    @Override
    public Post save(Post post) {
        if(post.getId() == null) {
            post.setId((long) (posts.size() + 1));
        } else {
            deleteById(post.getId());
        }

        posts.add(post);

        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return posts.stream().filter(post -> post.getId().equals(id)).findFirst();
    }

    @Override
    public List<Post> findAll() {
        return posts;
    }

    @Override
    public void deleteById(Long id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
}
