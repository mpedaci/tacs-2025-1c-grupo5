package utn.tacs.grupo5.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.impl.InMemoryPostRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class InMemoryPostRepositoryTest {
    private final InMemoryPostRepository inMemoryPostRepository = new InMemoryPostRepository();

    @BeforeEach
    public void init() {
        for (int i = 0; i < 3; i++) {
            inMemoryPostRepository.save(new Post());
        }
    }

    @Test
    public void save_shouldSavePost_whenPostDoesNotExist() {
        Post post = new Post();
        inMemoryPostRepository.save(post);
        assertEquals(4, inMemoryPostRepository.getPosts().size());
        assertEquals(4, post.getId());
    }

    @Test
    public void save_shouldUpdatePost_whenPostExists() {
        Post post = inMemoryPostRepository.findById(1L).orElseThrow();
        post.setConservationStatus(ConservationStatus.GOOD);
        inMemoryPostRepository.save(post);

        assertEquals(3, inMemoryPostRepository.getPosts().size());
        assertEquals(1, post.getId());
        assertEquals(ConservationStatus.GOOD, post.getConservationStatus());
    }

    @Test
    public void deleteById_shouldRemovePost_whenPostExists() {
        inMemoryPostRepository.deleteById(2L);
        assertEquals(2, inMemoryPostRepository.getPosts().size());
        assertFalse(inMemoryPostRepository.getPosts().stream().anyMatch(post -> post.getId().equals(2L)));
    }

    @Test
    public void findById_shouldReturnPost_whenPostExists() {
        Post post = inMemoryPostRepository.findById(1L).orElseThrow();
        assertEquals(1, post.getId());
    }

    @Test
    public void findAll_shouldReturnAllPosts() {
        List<Post> posts = inMemoryPostRepository.findAll();
        assertEquals(3, posts.size());
    }
}
