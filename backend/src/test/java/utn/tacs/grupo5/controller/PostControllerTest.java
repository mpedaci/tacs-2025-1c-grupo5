package utn.tacs.grupo5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import utn.tacs.grupo5.TestSecurityConfig;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.dto.post.PostOutputDto;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.mapper.PostMapper;
import utn.tacs.grupo5.service.impl.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PostController.class)
@Import(TestSecurityConfig.class)
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PostMapper postMapper;

    @MockitoBean
    PostService postService;

    // Save
    @Test
    void save_shouldReturnOK_whenValidInput() throws Exception {
        PostInputDto postInputDto = new PostInputDto();

        PostOutputDto postOutputDto = new PostOutputDto();
        UUID postId = UUID.randomUUID();
        postOutputDto.setId(postId);

        Post post = new Post();
        post.setId(postId);

        when(postService.save(postInputDto)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId.toString()));
    }

    // Update
    @Test
    void update_shouldReturnOK_whenValidInput() throws Exception {
        UUID postId = UUID.randomUUID();
        PostInputDto postInputDto = new PostInputDto();

        PostOutputDto postOutputDto = new PostOutputDto();
        postOutputDto.setId(postId);

        Post post = new Post();
        post.setId(postId);

        when(postService.update(postId, postInputDto)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId.toString()));
    }

    @Test
    void delete_shouldReturnOK_whenPostExists() throws Exception {
        UUID postId = UUID.randomUUID();
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void get_shouldReturnPost_whenPostExists() throws Exception {
        UUID postId = UUID.randomUUID();

        Post post = new Post();
        post.setId(postId);

        PostOutputDto postOutputDto = new PostOutputDto();
        postOutputDto.setId(postId);

        when(postService.get(postId)).thenReturn(Optional.of(post));
        when(postMapper.toDto(post)).thenReturn(postOutputDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/" + postId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId.toString()));
    }

    @Test
    void get_shouldReturnNotFound_whenPostDoesNotExist() throws Exception {
        UUID postId = UUID.randomUUID();

        when(postService.get(postId)).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/" + postId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoPostsExist() throws Exception {
        when(postService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    void getAll_shouldReturnListOfPosts_whenPostsExistAndNoFilters() throws Exception {
        UUID postId1 = UUID.randomUUID();
        UUID postId2 = UUID.randomUUID();

        Post post1 = new Post();
        post1.setId(postId1);
        Post post2 = new Post();
        post2.setId(postId2);

        PostOutputDto postOutputDto1 = new PostOutputDto();
        postOutputDto1.setId(postId1);
        PostOutputDto postOutputDto2 = new PostOutputDto();
        postOutputDto2.setId(postId2);

        when(postService.getAllWithFilters(null, null, null)).thenReturn(List.of(post1, post2));
        when(postMapper.toDto(post1)).thenReturn(postOutputDto1);
        when(postMapper.toDto(post2)).thenReturn(postOutputDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(postId1.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(postId2.toString()));
    }

}
