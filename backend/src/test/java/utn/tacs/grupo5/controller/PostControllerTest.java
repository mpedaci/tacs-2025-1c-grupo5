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
                postOutputDto.setId(1L);

                Post post = new Post();
                post.setId(1L);

                when(postService.save(postInputDto)).thenReturn(post);
                when(postMapper.toDto(post)).thenReturn(postOutputDto);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/posts")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(postInputDto)))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
        }

        // Update
        @Test
        void update_shouldReturnOK_whenValidInput() throws Exception {
                Long postId = 1L;
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
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId));
        }

        @Test
        void delete_shouldReturnOK_whenPostExists() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/posts/" + 1L)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void get_shouldReturnPost_whenPostExists() throws Exception {
                Long postId = 1L;

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
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId));
        }

        @Test
        void get_shouldReturnNotFound_whenPostDoesNotExist() throws Exception {
                Long postId = 1L;

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
                Post post1 = new Post();
                post1.setId(1L);
                Post post2 = new Post();
                post2.setId(2L);

                PostOutputDto postOutputDto1 = new PostOutputDto();
                postOutputDto1.setId(1L);
                PostOutputDto postOutputDto2 = new PostOutputDto();
                postOutputDto2.setId(2L);

                when(postService.getAllWithFilters(null, null, null)).thenReturn(List.of(post1, post2));
                when(postMapper.toDto(post1)).thenReturn(postOutputDto1);
                when(postMapper.toDto(post2)).thenReturn(postOutputDto2);

                mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
        }

}
