package utn.tacs.grupo5.controller;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import utn.tacs.grupo5.TestSecurityConfig;
import utn.tacs.grupo5.controller.exceptions.ConflictException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.service.IUserService;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    IUserService userService;

    @Test
    void save_shouldReturnOK_whenValidInput() throws Exception {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setFirstName("John Doe");
        userInputDto.setEmail("john.doe@example.com");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User saved successfully"));
    }

    @Test
    void save_shouldReturnConflict_whenValidUserExists() throws Exception {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setFirstName("John Doe");
        userInputDto.setEmail("john.doe@example.com");

        when(userService.save(userInputDto)).thenThrow(new ConflictException("User already exists"));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void get_shouldReturnUser_whenUserExists() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John Doe");
        user.setEmail("john.doe@example.com");

        when(userService.get(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John Doe"));
    }

    @Test
    void get_shouldReturnNotFound_whenUserDoesntExists() throws Exception {
        Long userId = 999L;

        when(userService.get(userId)).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_shouldReturnOK_whenValidInput() throws Exception {
        Long userId = 1L;
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setFirstName("Updated Name");
        userInputDto.setEmail("updated.email@example.com");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User updated successfully"));
    }

    @Test
    void update_shouldReturnNotFound_whenUserDoesntExists() throws Exception {
        Long userId = 999L;
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setFirstName("Updated Name");
        userInputDto.setEmail("updated.email@example.com");

        when(userService.update(userId, userInputDto)).thenThrow(new NotFoundException("User not found"));

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }

}
