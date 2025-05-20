package utn.tacs.grupo5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.mapper.PostMapper;
import utn.tacs.grupo5.repository.PostRepository;
import utn.tacs.grupo5.service.impl.CardService;
import utn.tacs.grupo5.service.impl.PostService;
import utn.tacs.grupo5.service.impl.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    PostRepository postRepository;

    @Mock
    PostMapper postMapper;

    @Mock
    CardService cardService;

    @Mock
    UserService userService;

    @InjectMocks
    PostService postService;

    // Save
    @Test
    public void save_shouldSavePost_whenValidInput() {
        PostInputDto requestDto = new PostInputDto();
        requestDto.setUserId(UUID.randomUUID());
        requestDto.setConservationStatus(ConservationStatus.GOOD);

        User user = new User();
        user.setId(requestDto.getUserId());
        Post createdPost = new Post();
        createdPost.setId(UUID.randomUUID());
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
        requestDto.setUserId(UUID.randomUUID());
        requestDto.setConservationStatus(ConservationStatus.PERFECT);

        Post existingPost = new Post();
        existingPost.setId(UUID.randomUUID());
        existingPost.setEstimatedValue(BigDecimal.ONE);
        existingPost.setUserId(UUID.randomUUID());
        existingPost.setCardId(UUID.randomUUID());
        existingPost.setWantedCardsIds(new ArrayList<>());

        Post updatedPost = new Post();
        updatedPost.setId(existingPost.getId());
        updatedPost.setEstimatedValue(BigDecimal.TEN);

        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.of(existingPost));
        when(postMapper.toEntity(requestDto)).thenReturn(updatedPost);
        when(postRepository.save(updatedPost)).thenReturn(updatedPost);
        when(cardService.get(any(UUID.class))).thenReturn(Optional.of(new Card()));
        when(userService.get(any(UUID.class))).thenReturn(Optional.of(new User()));

        Post result = postService.update(existingPost.getId(), requestDto);

        verify(postRepository).findById(existingPost.getId());
        verify(postRepository).save(updatedPost);

        assertEquals(existingPost.getId(), result.getId());
        assertNotEquals(existingPost.getEstimatedValue(), result.getEstimatedValue());
    }

    @Test
    public void delete_shouldDeletePost_whenPostExists() {
        UUID postId = UUID.randomUUID();
        postService.delete(postId);
        verify(postRepository).deleteById(postId);
    }

    @Test
    public void get_shouldReturnPost_whenPostExists() {
        UUID postId = UUID.randomUUID();
        postService.get(postId);
        verify(postRepository).findById(postId);
    }

    @Test
    public void getAll_shouldReturnAllPosts() {
        postService.getAll();
        verify(postRepository).findAll();
    }

    @Test
    public void updateStatus_shouldUpdateStatus_whenPostExists() {
        UUID postId = UUID.randomUUID();
        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setStatus(Post.Status.PUBLISHED);

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        postService.updateStatus(postId, Post.Status.FINISHED);

        verify(postRepository).findById(postId);
        verify(postRepository).save(existingPost);

        assertEquals(Post.Status.FINISHED, existingPost.getStatus());
        assertNotNull(existingPost.getFinishedAt());
    }

    @Test
    public void updateStatus_shouldThrowNotFoundException_whenPostDoesNotExist() {
        UUID postId = UUID.randomUUID();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        try {
            postService.updateStatus(postId, Post.Status.CANCELLED);
        } catch (NotFoundException e) {
            assertEquals("Post not found", e.getMessage());
        }

        verify(postRepository).findById(postId);
        verify(postRepository, never()).save(any());
    }
}
