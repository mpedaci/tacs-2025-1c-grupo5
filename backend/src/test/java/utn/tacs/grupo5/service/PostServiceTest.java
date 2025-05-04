package utn.tacs.grupo5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.mapper.PostMapper;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.service.impl.PostService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    PostMapper postMapper;

    @InjectMocks
    PostService postService;

    // Save
    @Test
    public void save_shouldSavePost_whenValidInput() {
        PostInputDto requestDto = new PostInputDto();
        requestDto.setUserId(1L);
        requestDto.setConservationStatus(ConservationStatus.GOOD);

        User user = new User();
        user.setId(1L);
        Post createdPost = new Post();
        createdPost.setId(1L);
        createdPost.setUser(user);

        when(postMapper.toEntity(requestDto)).thenReturn(createdPost);
        when(postRepository.save(createdPost)).thenReturn(createdPost);

        Post result = postService.save(requestDto);

        verify(postRepository).save(createdPost);
        assertEquals(createdPost, result);
        assertEquals(Post.Status.PUBLISHED, result.getStatus());
        assertNull(result.getFinishedAt());
        assertEquals(requestDto.getUserId(), result.getUser().getId());

    }

    @Test
    public void update_shouldUpdatePost_whenPostExists() {
        PostInputDto requestDto = new PostInputDto();
        requestDto.setUserId(1L);
        requestDto.setConservationStatus(ConservationStatus.PERFECT);

        Post existingPost = new Post();
        existingPost.setId(1L);
        existingPost.setEstimatedValue(BigDecimal.ONE);

        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setEstimatedValue(BigDecimal.TEN);

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(existingPost));
        when(postMapper.toEntity(requestDto)).thenReturn(updatedPost);
        when(postRepository.save(updatedPost)).thenReturn(updatedPost);

        Post result = postService.update(1L, requestDto);

        verify(postRepository).findById(1L);
        verify(postRepository).save(updatedPost);

        assertEquals(existingPost.getId(), result.getId());
        assertNotEquals(existingPost.getEstimatedValue(), result.getEstimatedValue());
    }

    @Test
    public void delete_shouldDeletePost_whenPostExists() {
        postService.delete(1L);
        verify(postRepository).deleteById(1L);
    }

    @Test
    public void get_shouldReturnPost_whenPostExists() {
        postService.get(1L);
        verify(postRepository).findById(1L);
    }

    @Test
    public void getAll_shouldReturnAllPosts() {
        postService.getAll();
        verify(postRepository).findAll();
    }

    @Test
    public void updateStatus_shouldUpdateStatus_whenPostExists() {
        Post existingPost = new Post();
        existingPost.setId(1L);
        existingPost.setStatus(Post.Status.PUBLISHED);

        when(postRepository.findById(1L)).thenReturn(Optional.of(existingPost));

        postService.updateStatus(1L, Post.Status.FINISHED);

        verify(postRepository).findById(1L);
        verify(postRepository).save(existingPost);

        assertEquals(Post.Status.FINISHED, existingPost.getStatus());
        assertNotNull(existingPost.getFinishedAt());
    }

    @Test
    public void updateStatus_shouldThrowNotFoundException_whenPostDoesNotExist() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            postService.updateStatus(1L, Post.Status.CANCELLED);
        } catch (NotFoundException e) {
            assertEquals("Post not found", e.getMessage());
        }

        verify(postRepository).findById(1L);
        verify(postRepository, never()).save(any());
    }
}
