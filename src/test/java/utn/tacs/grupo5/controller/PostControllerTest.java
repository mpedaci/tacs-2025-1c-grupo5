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
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.service.impl.PostService;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PostController.class)
@Import(TestSecurityConfig.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    //Save
    @Test
    void saveShouldReturnOK() throws Exception {
        PostInputDto postInputDto = new PostInputDto();

        when(postService.save(postInputDto)).thenReturn(new Post());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Update
    @Test
    void updateShouldReturnOK() throws Exception {
        Long postId = 1L;
        PostInputDto postInputDto = new PostInputDto();

        when(postService.update(postId, postInputDto)).thenReturn(new Post());

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/posts/" + postId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateStatusShouldReturnOK() throws Exception {
        Long postId = 1L;
        PostInputDto postInputDto = new PostInputDto();

        when(postService.update(postId, postInputDto)).thenReturn(new Post());

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/posts/" + postId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Delete
    @Test
    void deleteShouldReturnOK() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/posts/" + 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Getters
    @Test
    void getShouldReturnPostWhenPostExists() throws Exception {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);

        when(postService.get(postId)).thenReturn(Optional.of(post));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/" + postId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId));
    }

    @Test
    void getShouldReturnPosts() throws Exception {
        when(postService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
