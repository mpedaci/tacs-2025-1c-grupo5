package utn.tacs.grupo5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.service.impl.PostService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    // Save
    @Test
    public void savePostOk() {
        PostInputDto requestDtO = new PostInputDto();
        requestDtO.setUserId(1L);
        requestDtO.setConservationStatus("GOOD");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        postService.save(requestDtO);

        verify(postRepository).save(any(Post.class));
    }

    @Test
    public void savePostUserNotFound() {
        PostInputDto requestDtO = new PostInputDto();
        requestDtO.setUserId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postService.save(requestDtO));
    }

    // Update
    @Test
    public void updatePostOk() {
        PostInputDto requestDtO = new PostInputDto();
        requestDtO.setUserId(1L);
        requestDtO.setConservationStatus("PERFECT");

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(new Post()));
        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        postService.update(1L, requestDtO);

        verify(postRepository).findById(1L);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    public void updatePostStatusCancelledOk() {
        PostInputDto requestDtO = new PostInputDto();
        requestDtO.setUserId(1L);
        requestDtO.setPostStatus("CANCELLED");

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(new Post()));
        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        postService.update(1L, requestDtO);

        verify(postRepository).findById(1L);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    public void updatePostStatusFinishedOk() {
        PostInputDto requestDtO = new PostInputDto();
        requestDtO.setUserId(1L);
        requestDtO.setPostStatus("FINISHED");

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(new Post()));
        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        Post response = postService.update(1L, requestDtO);

        verify(postRepository).findById(1L);
        verify(postRepository).save(any(Post.class));
        assertNotNull(response.getFinishDate());
    }

    // Delete
    @Test
    public void deletePostOk() {
        postService.delete(1L);
        verify(postRepository).deleteById(1L);
    }

    // Getters
    @Test
    public void getPostByIdOk() {
        postService.get(1L);
        verify(postRepository).findById(1L);
    }

    @Test
    public void getPostsOk() {
        postService.getAll();
        verify(postRepository).findAll();
    }
}
